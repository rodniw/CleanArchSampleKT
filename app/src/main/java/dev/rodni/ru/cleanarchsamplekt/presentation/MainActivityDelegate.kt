package dev.rodni.ru.cleanarchsamplekt.presentation

import dev.rodni.ru.cleanarchsamplekt.Document

interface MainActivityDelegate {

  fun openDocument(document: Document)
}