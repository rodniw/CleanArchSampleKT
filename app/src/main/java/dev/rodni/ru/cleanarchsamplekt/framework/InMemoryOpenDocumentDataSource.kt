package dev.rodni.ru.cleanarchsamplekt.framework

import dev.rodni.ru.cleanarchsamplekt.data.OpenDocumentDataSource
import dev.rodni.ru.cleanarchsamplekt.domain.Document

//implements the interface from the data layer
//currently, the open document is stored in memory, so the implementation is pretty straightforward
class InMemoryOpenDocumentDataSource : OpenDocumentDataSource {

    private var openDocument: Document = Document.EMPTY

    override fun setOpenDocument(document: Document) {
        openDocument = document
    }

    override fun getOpenDocument() = openDocument
}