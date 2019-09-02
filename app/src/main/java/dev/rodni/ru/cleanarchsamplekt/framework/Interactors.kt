package dev.rodni.ru.cleanarchsamplekt.framework

import dev.rodni.ru.cleanarchsamplekt.interactors.*

//this data class holds all the interactors from core module
data class Interactors(
    val addBookmark: AddBookmark,
    val getBookmarks: GetBookmarks,
    val deleteBookmark: RemoveBookmark,
    val addDocument: AddDocument,
    val getDocuments: GetDocuments,
    val removeDocument: RemoveDocument,
    val getOpenDocument: GetOpenDocument,
    val setOpenDocument: SetOpenDocument
)