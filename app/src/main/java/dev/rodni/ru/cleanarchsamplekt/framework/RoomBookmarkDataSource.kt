package dev.rodni.ru.cleanarchsamplekt.framework

import android.content.Context
import dev.rodni.ru.cleanarchsamplekt.data.BookmarkDataSource
import dev.rodni.ru.cleanarchsamplekt.domain.Bookmark
import dev.rodni.ru.cleanarchsamplekt.domain.Document
import dev.rodni.ru.cleanarchsamplekt.framework.db.BookmarkEntity
import dev.rodni.ru.cleanarchsamplekt.framework.db.ReaderAppDatabase

class RoomBookmarkDataSource(context: Context) : BookmarkDataSource {

    //use ReaderAppDatabase to get an instance of BookmarkDao and store it in local field
    private val bookmarkDao = ReaderAppDatabase.getInstance(context).bookmarkDao()

    //call add, read and remove methods from Room implementation.
    override suspend fun add(document: Document, bookmark: Bookmark) =
        bookmarkDao.addBookmark(BookmarkEntity(
            documentUri = document.url,
            page = bookmark.page
        ))

    override suspend fun read(document: Document): List<Bookmark> = bookmarkDao
        .getBookmarks(document.url).map { Bookmark(it.id, it.page) }

    override suspend fun remove(document: Document, bookmark: Bookmark) =
        bookmarkDao.removeBookmark(
            BookmarkEntity(id = bookmark.id, documentUri = document.url, page = bookmark.page)
        )
}