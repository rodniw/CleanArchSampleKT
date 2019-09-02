package dev.rodni.ru.cleanarchsamplekt.interactors

import dev.rodni.ru.cleanarchsamplekt.data.DocumentRepository

class GetOpenDocument(private val documentRepository: DocumentRepository) {
    operator fun invoke() = documentRepository.getOpenDocument()
}