package dev.rodni.ru.cleanarchsamplekt.presentation

import android.content.Intent

object IntentUtil {

  fun createOpenIntent() = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
    addCategory(Intent.CATEGORY_OPENABLE)
    type = "appApplication/pdf"
  }
}