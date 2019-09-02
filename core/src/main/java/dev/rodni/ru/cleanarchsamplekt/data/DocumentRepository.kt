package dev.rodni.ru.cleanarchsamplekt.data

import dev.rodni.ru.cleanarchsamplekt.domain.Document

class DocumentRepository(
    private val documentDataSource: DocumentDataSource,
    private val openDocumentDataSource: OpenDocumentDataSource
) {
    /* for document data source*/
    suspend fun addDocument(document: Document) = documentDataSource.add(document)

    suspend fun getDocuments() = documentDataSource.readAll() //return List<Document>

    suspend fun removeDocument(document: Document) = documentDataSource.remove(document)

    /* for open document data source*/
    fun setOpenDocument(document: Document) = openDocumentDataSource.setOpenDocument(document)

    fun getOpenDocument() = openDocumentDataSource.getOpenDocument() //return Document
}