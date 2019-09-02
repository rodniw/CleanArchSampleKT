package dev.rodni.ru.cleanarchsamplekt.interactors

import dev.rodni.ru.cleanarchsamplekt.data.DocumentRepository

class GetDocuments(private val documentRepository: DocumentRepository) {
    suspend operator fun invoke() = documentRepository.getDocuments()
}