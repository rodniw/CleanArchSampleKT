package dev.rodni.ru.cleanarchsamplekt.interactors

import dev.rodni.ru.cleanarchsamplekt.data.DocumentRepository
import dev.rodni.ru.cleanarchsamplekt.domain.Document

class SetOpenDocument(private val documentRepository: DocumentRepository) {
    operator fun invoke(document: Document) = documentRepository.setOpenDocument(document)
}