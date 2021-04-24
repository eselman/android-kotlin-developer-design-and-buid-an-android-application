package com.eselman.android.books.database.utils

import com.eselman.android.books.database.BooksDatabase
import com.eselman.android.books.model.Book
import com.eselman.android.books.model.mappers.asDatabaseModel

class BooksSaver(val database: BooksDatabase) {
    fun insertBooks(books: List<Book>) {
        books.forEach { book ->
            database.bookDao.insertBook(book.asDatabaseModel())
            book.volumeInfo?.let {
                database.volumeInfoDao.insertFullVolumeInfo(it, book.id)
            }
            book.saleInfo?.let {
                database.saleInfoDao.insertFullSaleInfo(it, book.id)
            }

            book.accessInfo?.let {
                database.accessInfoDao.insertFullAccessInfo(it, book.id)
            }

            book.searchInfo?.let {
                database.searchInfoDao.insertSearchInfo(it.asDatabaseModel(book.id))
            }
        }
    }
}
