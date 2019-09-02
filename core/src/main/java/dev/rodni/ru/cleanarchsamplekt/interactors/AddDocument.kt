package dev.rodni.ru.cleanarchsamplekt.interactors

import dev.rodni.ru.cleanarchsamplekt.data.DocumentRepository
import dev.rodni.ru.cleanarchsamplekt.domain.Document

class AddDocument(private val documentRepository: DocumentRepository) {
    suspend operator fun invoke(document: Document) = documentRepository.addDocument(document)
}