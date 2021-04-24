package com.eselman.android.books.database.utils

import com.eselman.android.books.database.BooksDatabase
import com.eselman.android.books.database.entities.asDomainModel
import com.eselman.android.books.database.relations.asDomainModel
import com.eselman.android.books.model.Book
import com.eselman.android.books.model.mappers.PriceType

class BooksRetriever(val database: BooksDatabase) {
    fun getBooks(): List<Book> {
        val books = database.bookDao.getBooks().map {
            it.asDomainModel()
        }

        books.forEach { book ->
            val volumeInfo = database.volumeInfoDao.getVolumeInfoByBookId(book.id).asDomainModel()
            with(volumeInfo) {
                authors = database.volumeInfoDao.getVolumeInfoAuthors(volumeInfoId).asDomainModel()
                categories = database.volumeInfoDao.getVolumeInfoCategories(volumeInfoId).asDomainModel()
                dimensions = database.volumeInfoDao.getDimensions(volumeInfoId)?.asDomainModel()
                imageLinks = database.volumeInfoDao.getImageLinks(volumeInfoId)?.asDomainModel()
                industryIdentifiers = database.volumeInfoDao.getVolumeInfoIndustryIdentifiers(volumeInfoId)
                    .asDomainModel()
            }
            book.volumeInfo = volumeInfo

            val saleInfo = database.saleInfoDao.getSaleInfoByBookId(book.id).asDomainModel()
            with(saleInfo) {
                listPrice = database.saleInfoDao.getPriceByType(saleInfoId, PriceType.LIST.name).asDomainModel()
                retailPrice = database.saleInfoDao.getPriceByType(saleInfoId, PriceType.RETAIL.name).asDomainModel()
            }
            book.saleInfo = saleInfo

            val accessInfo = database.accessInfoDao.getAccessInfoByBookId(book.id).asDomainModel()
            with(accessInfo) {
                epub = database.accessInfoDao.getEpub(accessInfoId).asDomainModel()
                pdf = database.accessInfoDao.getPDF(accessInfoId).asDomainModel()
                downloadAccess = database.accessInfoDao.getDownloadAccess(accessInfoId)?.asDomainModel()
            }
            book.accessInfo = accessInfo

            book.searchInfo = database.searchInfoDao.getSearchInfoByBookId(book.id)?.asDomainModel()
        }

        return books
    }
}
