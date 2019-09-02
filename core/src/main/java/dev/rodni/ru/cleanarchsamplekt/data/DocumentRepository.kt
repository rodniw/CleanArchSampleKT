package dev.rodni.ru.cleanarchsamplekt.data

import dev.rodni.ru.cleanarchsamplekt.domain.Document

class DocumentRepository(private val dataSource: DocumentDataSource) {

    suspend fun addDocument(document: Document) = dataSource.add(document)

    suspend fun readAll() = dataSource.readAll() //return List<Document>

    suspend fun removeDocument(document: Document) = dataSource.remove(document)
}