package dev.rodni.ru.cleanarchsamplekt.interactors

import dev.rodni.ru.cleanarchsamplekt.data.BookmarkRepository
import dev.rodni.ru.cleanarchsamplekt.domain.Document

class GetBookmarks(private val bookmarkRepository: BookmarkRepository) {
    suspend operator fun invoke(document: Document) = bookmarkRepository.getBookmarks(document)
}