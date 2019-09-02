package dev.rodni.ru.cleanarchsamplekt.framework

import android.app.Application
import androidx.lifecycle.AndroidViewModel

open class ReaderAppViewModel(application: Application, protected val interactors: Interactors) :
    AndroidViewModel(application) {

  protected val appApplication: ReaderAppApplication = getApplication()

}
