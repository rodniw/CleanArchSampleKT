package dev.rodni.ru.cleanarchsamplekt.framework

import android.content.Context
import dev.rodni.ru.cleanarchsamplekt.data.DocumentDataSource
import dev.rodni.ru.cleanarchsamplekt.domain.Document
import dev.rodni.ru.cleanarchsamplekt.framework.db.DocumentEntity
import dev.rodni.ru.cleanarchsamplekt.framework.db.ReaderAppDatabase

class RoomDocumentDataSource(val context: Context) : DocumentDataSource {

    private val documentDao = ReaderAppDatabase.getInstance(context).documentDao()

    override suspend fun add(document: Document) {
        val details = FileUtil.getDocumentDetails(context, document.url)
        documentDao.addDocument(
            DocumentEntity(document.url, details.name, details.size, details.thumbnail)
        )
    }

    override suspend fun readAll(): List<Document> = documentDao.getDocuments().map {
        Document(
            it.uri,
            it.title,
            it.size,
            it.thumbnailUri
        )
    }

    override suspend fun remove(document: Document) = documentDao.removeDocument(
        DocumentEntity(document.url, document.name, document.size, document.thumbnail)
    )
}