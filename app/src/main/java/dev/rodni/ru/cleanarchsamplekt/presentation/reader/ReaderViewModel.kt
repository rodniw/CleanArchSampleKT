package dev.rodni.ru.cleanarchsamplekt.presentation.reader

import android.app.Application
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import dev.rodni.ru.cleanarchsamplekt.domain.Bookmark
import dev.rodni.ru.cleanarchsamplekt.domain.Document
import dev.rodni.ru.cleanarchsamplekt.framework.Interactors
import dev.rodni.ru.cleanarchsamplekt.framework.ReaderAppViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException

class ReaderViewModel(application: Application, interactors: Interactors) : ReaderAppViewModel
(application, interactors) {

  companion object {
    private const val DOCUMENT_ARG = "document"

    fun createArguments(document: Document) = bundleOf(
        DOCUMENT_ARG to document
    )
  }

  val document = MutableLiveData<Document>()

  //this will change the value of bookmarks each time my user change the document
  //it will fill with up to date bookmarks, which getting from the interactors, within a coroutine
  val bookmarks = MediatorLiveData<List<Bookmark>>().apply {
    addSource(document) { document ->
      GlobalScope.launch {
        postValue(interactors.getBookmarks(document))
      }
    }
  }

  val currentPage = MediatorLiveData<PdfRenderer.Page>()

  //hasPreviousPage returns true if the index of currentPage is larger than zero
  val hasPreviousPage: LiveData<Boolean> = Transformations.map(currentPage) {
    it.index > 0
  }

  //hasNextPage returns true if the index of currentPage is less than the page count minus one – if the user hasn’t reached the end
  val hasNextPage: LiveData<Boolean> = Transformations.map(currentPage) {
    renderer.value?.let { renderer -> it.index < renderer.pageCount - 1 }
  }

  //isBookmarked relies on isCurrentPageBookmarked()
  //every time document, currentPage or bookmarks change, isBookmarked will receive an update and change, as well
  val isBookmarked = MediatorLiveData<Boolean>().apply {
    addSource(document) { value = isCurrentPageBookmarked() }
    addSource(currentPage) { value = isCurrentPageBookmarked() }
    addSource(bookmarks) { value = isCurrentPageBookmarked() }
  }

  //is in library fun is a suspend fun is a suspend function
  val isInLibrary: MediatorLiveData<Boolean> = MediatorLiveData<Boolean>().apply {
    addSource(document) { document -> GlobalScope.launch { postValue(isInLibrary(document)) } }
  }

  val renderer = MediatorLiveData<PdfRenderer>().apply {
    addSource(document) {
      try {
        val pdfRenderer = getFileDescriptor(Uri.parse(it.url))?.let { it1 -> PdfRenderer(it1) }
        value = pdfRenderer
      } catch (e: IOException) {
        e.printStackTrace()
      }
    }
  }

  private fun getFileDescriptor(uri: Uri) = appApplication.contentResolver.openFileDescriptor(uri, "r")

  //isCurrentPageBookmarked() returns true if a bookmark for the currently shown page exists
  private fun isCurrentPageBookmarked() =
      bookmarks.value?.any { it.page == currentPage.value?.index } == true

  //should return true if the open document is already in the library
  private suspend fun isInLibrary(document: Document) =
    interactors.getDocuments().any { it.url == document.url }

  fun loadArguments(arguments: Bundle?) {
    if (arguments == null) {
      return
    }

    //initializes currentPage to be set to the first page or first bookmarked page if it exists
    currentPage.apply {
      addSource(renderer) { renderer ->
        GlobalScope.launch {
          val document = document.value

          if (document != null) {
            val bookmarks = interactors.getBookmarks(document).lastOrNull()?.page ?: 0
            postValue(renderer.openPage(bookmarks))
          }
        }
      }
    }

    //gets Document passed to ReaderFragment
    val documentFromArguments = arguments.get(DOCUMENT_ARG) as Document? ?: Document.EMPTY

    //gets the last document that was opened from GetOpenDocument
    val lastOpenDocument = interactors.getOpenDocument()

    //sets the value of document to the one passed to ReaderFragment or falls back to lastOpenDocument if nothing was passed
    document.value = when {
      documentFromArguments != Document.EMPTY -> documentFromArguments
      documentFromArguments == Document.EMPTY && lastOpenDocument != Document.EMPTY -> lastOpenDocument
      else -> Document.EMPTY
    }

    //sets the new open document by calling SetOpenDocument
    document.value?.let { interactors.setOpenDocument(it) }

  }

  //this creates a new Document that represents the document that was just open and passes it to SetOpenDocument
  fun openDocument(uri: Uri) {
    document.value = Document(uri.toString(), "", 0, "")
    document.value?.let { interactors.setOpenDocument(it) }
  }


  fun openBookmark(bookmark: Bookmark) {
    openPage(bookmark.page)
  }

  private fun openPage(page: Int) = renderer.value?.let {
    currentPage.value = it.openPage(page)
  }

  fun nextPage() = currentPage.value?.let { openPage(it.index.plus(1)) }

  fun previousPage() = currentPage.value?.let { openPage(it.index.minus(1)) }

  //this function, either delete or add a bookmark, depending on if it’s already in user's database,
  //and then this update the bookmarks, to refresh the UI
  fun toggleBookmark() {
    val currentPage = currentPage.value?.index ?: return
    val document = document.value ?: return
    val bookmark = bookmarks.value?.firstOrNull { it.page == currentPage }

    GlobalScope.launch {
      if (bookmark == null) {
        interactors.addBookmark(document, Bookmark(page = currentPage))
      } else {
        interactors.deleteBookmark(document, bookmark)
      }

      bookmarks.postValue(interactors.getBookmarks(document))
    }
  }

  //the same as toggle bookmark, but with document here
  fun toggleInLibrary() {
    val document = document.value ?: return

    GlobalScope.launch {
      if (isInLibrary.value == true) {
        interactors.removeDocument(document)
      } else {
        interactors.addDocument(document)
      }

      isInLibrary.postValue(isInLibrary(document))
    }
  }

}
