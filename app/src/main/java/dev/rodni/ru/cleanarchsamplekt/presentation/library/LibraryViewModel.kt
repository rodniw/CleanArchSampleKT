package dev.rodni.ru.cleanarchsamplekt.presentation.library

import android.app.Application
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import dev.rodni.ru.cleanarchsamplekt.domain.Document
import dev.rodni.ru.cleanarchsamplekt.framework.Interactors
import dev.rodni.ru.cleanarchsamplekt.framework.ReaderAppViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LibraryViewModel(application: Application, interactors: Interactors)
  : ReaderAppViewModel(application, interactors) {

  val documents: MutableLiveData<List<Document>> = MutableLiveData()

  //this fetches the list of documents from the library using the GetDocuments interactor
  fun loadDocuments() {
    GlobalScope.launch {
      documents.postValue(interactors.getDocuments())
    }
  }

  //right after adding a new document my user should be able to see this new doc inside doc's list
  //that is why after interactors.addDocument i call loadDocuments method
  //which loading the list of documents againg to update that list
  fun addDocument(uri: Uri) {
    GlobalScope.launch {
      withContext(Dispatchers.IO) {
        interactors.addDocument(Document(uri.toString(), "", 0, ""))
      }
      loadDocuments()
    }
  }

  fun setOpenDocument(document: Document) {
    interactors.setOpenDocument(document)
  }

}
