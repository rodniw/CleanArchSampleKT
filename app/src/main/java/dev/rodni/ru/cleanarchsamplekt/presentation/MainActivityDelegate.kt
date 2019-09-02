package dev.rodni.ru.cleanarchsamplekt.presentation

import dev.rodni.ru.cleanarchsamplekt.domain.Document

interface MainActivityDelegate {

  fun openDocument(document: Document)
}