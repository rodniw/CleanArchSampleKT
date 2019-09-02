package dev.rodni.ru.cleanarchsamplekt.data

import dev.rodni.ru.cleanarchsamplekt.domain.Document

//created this interface to provide the Document data access from the Framework layer.
interface DocumentDataSource {

    suspend fun add(document: Document)

    suspend fun readAll(): List<Document>

    suspend fun remove(document: Document)
}