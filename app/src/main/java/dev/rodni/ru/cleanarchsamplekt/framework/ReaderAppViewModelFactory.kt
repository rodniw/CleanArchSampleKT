package dev.rodni.ru.cleanarchsamplekt.framework

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

object ReaderAppViewModelFactory : ViewModelProvider.Factory {

  lateinit var application: Application

  lateinit var dependencies: Interactors

  fun inject(application: Application, dependencies: Interactors) {
    ReaderAppViewModelFactory.application = application
    ReaderAppViewModelFactory.dependencies = dependencies
  }

  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    if(ReaderAppViewModel::class.java.isAssignableFrom(modelClass)) {
      return modelClass.getConstructor(Application::class.java, Interactors::class.java)
          .newInstance(
              application,
              dependencies)
    } else {
      throw IllegalStateException("ViewModel must extend ReaderAppViewModel")
    }
  }

}
