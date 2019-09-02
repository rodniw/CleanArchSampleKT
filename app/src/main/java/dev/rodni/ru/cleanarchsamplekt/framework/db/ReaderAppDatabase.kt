package dev.rodni.ru.cleanarchsamplekt.framework.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [BookmarkEntity::class, DocumentEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ReaderAppDatabase : RoomDatabase() {

  companion object {

    private const val DATABASE_NAME = "reader.db"

    private var instance: ReaderAppDatabase? = null

    private fun create(context: Context): ReaderAppDatabase =
        Room.databaseBuilder(context, ReaderAppDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()


    fun getInstance(context: Context): ReaderAppDatabase =
        (instance ?: create(context)).also { instance = it }
  }

  abstract fun bookmarkDao(): BookmarkDao

  abstract fun documentDao(): DocumentDao

}