package com.eselman.android.books.repository

import com.eselman.android.books.api.RetrofitHelper
import com.eselman.android.books.common.Constants
import com.eselman.android.books.common.SearchBy
import com.eselman.android.books.database.BooksDatabase
import com.eselman.android.books.database.utils.BooksRetriever
import com.eselman.android.books.database.utils.BooksSaver
import com.eselman.android.books.model.Book
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BooksRepository(private val database: BooksDatabase) {
    private val booksSaver = BooksSaver(database)
    private val booksRetriever = BooksRetriever(database)

    suspend fun refreshBooks(lastSearchQueryByAuthor: String, lastSearchQueryByTitle: String) {
        refreshBooksByAuthor(lastSearchQueryByAuthor)
        refreshBooksByTitle(lastSearchQueryByTitle)
    }

    suspend fun refreshBooksByAuthor(lastSearchQueryByAuthor: String) {
        withContext(Dispatchers.IO) {
            if (lastSearchQueryByAuthor.isNotEmpty()) {
                val queryByAuthor = "$lastSearchQueryByAuthor+inauthor:$lastSearchQueryByAuthor"
                val booksResultByAuthor =
                    RetrofitHelper.booksService.getVolumes(queryByAuthor,
                        Constants.DEFAULT_START_INDEX,
                        Constants.DEFAULT_MAX_RESULTS,
                        "relevance")
                cleanAll()
                booksSaver.insertBooks(booksResultByAuthor.items)
            }
        }
    }

    suspend fun refreshBooksByTitle(lastSearchQueryByTitle: String) {
        withContext(Dispatchers.IO) {
            if (lastSearchQueryByTitle.isNotEmpty()) {
                val queryByTitle = "$lastSearchQueryByTitle+intitle:$lastSearchQueryByTitle"
                val booksResultByTitle =
                    RetrofitHelper.booksService.getVolumes(
                        queryByTitle,
                        Constants.DEFAULT_START_INDEX,
                        Constants.DEFAULT_MAX_RESULTS,
                        "relevance"
                    )
                cleanAll()
                booksSaver.insertBooks(booksResultByTitle.items)
            }
        }
    }

    suspend fun getBooks(query: String, searchBy: SearchBy): List<Book> {
        val books = getBooks()
        when (searchBy) {
            SearchBy.TITLE -> return books.filter {
                it.volumeInfo?.title?.contains(query, true) == true
            }

            SearchBy.AUTHOR -> {
                val booksByAuthor = mutableListOf<Book>()
                books.forEach { book ->
                    book.volumeInfo?.authors?.forEach { author ->
                        if (author?.contains(query, true) == true) {
                            booksByAuthor.add(book)
                        }
                    }
                }
                return booksByAuthor
            }
        }
    }

    private suspend fun getBooks() = withContext(Dispatchers.IO) {
        booksRetriever.getBooks()
    }

     suspend fun cleanAll() {
         withContext(Dispatchers.IO) {
             database.volumeInfoDao.deleteAuthors()
             database.volumeInfoDao.deleteCategories()
             database.volumeInfoDao.deleteDimensions()
             database.volumeInfoDao.deleteImageLinks()
             database.volumeInfoDao.deleteIndustryIdentifiers()
             database.volumeInfoDao.deleteVolumeInfo()
             database.searchInfoDao.deleteSearchInfo()
             database.saleInfoDao.deletePrice()
             database.saleInfoDao.deleteSaleInfo()
             database.accessInfoDao.deleteEpub()
             database.accessInfoDao.deletePDF()
             database.accessInfoDao.deleteDownloadAccess()
             database.accessInfoDao.deleteAccessInfo()
             database.bookDao.deleteBooks()
         }
    }
}
