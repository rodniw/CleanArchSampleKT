package dev.rodni.ru.cleanarchsamplekt.presentation.library

import android.app.Application
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import dev.rodni.ru.cleanarchsamplekt.Document
import dev.rodni.ru.cleanarchsamplekt.framework.Interactors
import dev.rodni.ru.cleanarchsamplekt.framework.MajesticViewModel

class LibraryViewModel(application: Application, interactors: Interactors)
  : MajesticViewModel(application, interactors) {

  val documents: MutableLiveData<List<Document>> = MutableLiveData()

  fun loadDocuments() {
    // TODO start loading documents
  }

  fun addDocument(uri: Uri) {
    // TODO add a new document
    loadDocuments()
  }

  fun setOpenDocument(document: Document) {
    // TODO set currently open document
  }
}
