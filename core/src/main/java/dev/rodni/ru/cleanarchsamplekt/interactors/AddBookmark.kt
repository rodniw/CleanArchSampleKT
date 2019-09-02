package dev.rodni.ru.cleanarchsamplekt.interactors

import dev.rodni.ru.cleanarchsamplekt.data.BookmarkRepository
import dev.rodni.ru.cleanarchsamplekt.domain.Bookmark
import dev.rodni.ru.cleanarchsamplekt.domain.Document

class AddBookmark(private val bookmarkRepository: BookmarkRepository) {
    suspend operator fun invoke(document: Document, bookmark: Bookmark) =
        bookmarkRepository.addBookmark(document, bookmark)
}