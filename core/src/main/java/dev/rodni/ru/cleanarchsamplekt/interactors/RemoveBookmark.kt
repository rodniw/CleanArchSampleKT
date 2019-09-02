package dev.rodni.ru.cleanarchsamplekt.interactors

import dev.rodni.ru.cleanarchsamplekt.data.BookmarkRepository
import dev.rodni.ru.cleanarchsamplekt.domain.Bookmark
import dev.rodni.ru.cleanarchsamplekt.domain.Document

class RemoveBookmark(private val bookmarkRepository: BookmarkRepository) {
    suspend operator fun invoke(document: Document, bookmark: Bookmark) = bookmarkRepository.removeBookmark(document, bookmark)
}