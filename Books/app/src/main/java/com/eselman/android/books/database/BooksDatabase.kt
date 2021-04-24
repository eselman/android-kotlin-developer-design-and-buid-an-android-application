package com.eselman.android.books.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.eselman.android.books.database.dao.AccessInfoDao
import com.eselman.android.books.database.dao.BookDao
import com.eselman.android.books.database.dao.SaleInfoDao
import com.eselman.android.books.database.dao.SearchInfoDao
import com.eselman.android.books.database.dao.VolumeInfoDao
import com.eselman.android.books.database.entities.AccessInfoEntity
import com.eselman.android.books.database.entities.AuthorEntity
import com.eselman.android.books.database.entities.BookEntity
import com.eselman.android.books.database.entities.CategoryEntity
import com.eselman.android.books.database.entities.DimensionsEntity
import com.eselman.android.books.database.entities.DownloadAccessEntity
import com.eselman.android.books.database.entities.EpubEntity
import com.eselman.android.books.database.entities.ImageLinksEntity
import com.eselman.android.books.database.entities.IndustryIdentifierEntity
import com.eselman.android.books.database.entities.PDFEntity
import com.eselman.android.books.database.entities.PriceEntity
import com.eselman.android.books.database.entities.SaleInfoEntity
import com.eselman.android.books.database.entities.SearchInfoEntity
import com.eselman.android.books.database.entities.VolumeInfoEntity

@Database(entities = [AccessInfoEntity::class, AuthorEntity::class, BookEntity::class, CategoryEntity::class,
    DimensionsEntity::class, DownloadAccessEntity::class, EpubEntity::class, ImageLinksEntity::class,
    IndustryIdentifierEntity::class, PDFEntity::class, PriceEntity::class, SaleInfoEntity::class,
    SearchInfoEntity::class, VolumeInfoEntity::class],
        version = 1,
exportSchema = false)
abstract class BooksDatabase : RoomDatabase() {
    abstract val bookDao: BookDao
    abstract val volumeInfoDao: VolumeInfoDao
    abstract val saleInfoDao: SaleInfoDao
    abstract val accessInfoDao: AccessInfoDao
    abstract val searchInfoDao: SearchInfoDao
}

private lateinit var INSTANCE: BooksDatabase

fun getDatabase(context: Context): BooksDatabase {
    synchronized(BooksDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                BooksDatabase::class.java,
                "books").build()
        }
    }
    return INSTANCE
}
