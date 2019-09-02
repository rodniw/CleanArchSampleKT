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

  val bookmarks = MediatorLiveData<List<Bookmark>>().apply {
    // TODO add sources
  }

  val currentPage = MediatorLiveData<PdfRenderer.Page>()

  val hasPreviousPage: LiveData<Boolean> = Transformations.map(currentPage) {
    it.index > 0
  }

  val hasNextPage: LiveData<Boolean> = Transformations.map(currentPage) {
    renderer.value?.let { renderer -> it.index < renderer.pageCount - 1 }
  }

  val isBookmarked = MediatorLiveData<Boolean>().apply {
    addSource(document) { value = isCurrentPageBookmarked() }
    addSource(currentPage) { value = isCurrentPageBookmarked() }
    addSource(bookmarks) { value = isCurrentPageBookmarked() }
  }

  val isInLibrary: MediatorLiveData<Boolean> = MediatorLiveData<Boolean>().apply {
    addSource(document) { value = isInLibrary(it) }
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

  private fun isCurrentPageBookmarked() =
      bookmarks.value?.any { it.page == currentPage.value?.index } == true

  // TODO check if document is in library
  private fun isInLibrary(document: Document) = false

  fun loadArguments(arguments: Bundle?) {
    if (arguments == null) {
      return
    }

    // TODO load document from arguments and initialize
  }

  fun openDocument(uri: Uri) {
    // TODO open document
  }

  fun openBookmark(bookmark: Bookmark) {
    openPage(bookmark.page)
  }

  private fun openPage(page: Int) = renderer.value?.let {
    currentPage.value = it.openPage(page)
  }

  fun nextPage() = currentPage.value?.let { openPage(it.index.plus(1)) }

  fun previousPage() = currentPage.value?.let { openPage(it.index.minus(1)) }

  fun toggleBookmark() {
    // TODO toggle bookmark on the current page
  }

  fun toggleInLibrary() {
    // TODO toggle if open document is in library
  }
}
