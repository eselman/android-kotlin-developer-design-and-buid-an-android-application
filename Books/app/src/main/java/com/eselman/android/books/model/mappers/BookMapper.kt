package com.eselman.android.books.model.mappers

import com.eselman.android.books.database.entities.AuthorEntity
import com.eselman.android.books.database.entities.BookEntity
import com.eselman.android.books.database.entities.IndustryIdentifierEntity
import com.eselman.android.books.database.entities.VolumeInfoEntity
import com.eselman.android.books.database.entities.DimensionsEntity
import com.eselman.android.books.database.entities.CategoryEntity
import com.eselman.android.books.database.entities.ImageLinksEntity
import com.eselman.android.books.database.entities.SaleInfoEntity
import com.eselman.android.books.database.entities.PriceEntity
import com.eselman.android.books.database.entities.AccessInfoEntity
import com.eselman.android.books.database.entities.EpubEntity
import com.eselman.android.books.database.entities.PDFEntity
import com.eselman.android.books.database.entities.DownloadAccessEntity
import com.eselman.android.books.database.entities.SearchInfoEntity
import com.eselman.android.books.model.Book
import com.eselman.android.books.model.VolumeInfo
import com.eselman.android.books.model.AccessInfo
import com.eselman.android.books.model.SaleInfo
import com.eselman.android.books.model.SearchInfo

fun Book.asDatabaseModel(): BookEntity {
    return BookEntity(
        id = this.id,
        kind = this.kind,
        etag = this.etag,
        selfLink = this.selfLink
    )
}

fun VolumeInfo.asDatabaseModel(bookId: String): VolumeInfoEntity {
        return VolumeInfoEntity(
                    bookId = bookId,
                    title = this.title,
                    subtitle = this.subtitle,
                    publisher = this.publisher,
                    publishedDate = this.publishedDate,
                    description = this.description,
                    pageCount = this.pageCount,
                    printType = this.printType,
                    averageRating = this.averageRating,
                    ratingsCount = this.ratingsCount,
                    contentVersion = this.contentVersion,
                    language = this.language,
                    mainCategory = this.mainCategory,
                    previewLink = this.previewLink,
                   canonicalVolumeLink = this.canonicalVolumeLink
                )
        }

fun VolumeInfo.authorsAsDatabaseModel(volumeInfoId: Long): List<AuthorEntity>? {
    return authors?.map {
        AuthorEntity(author = it, volumeInfoId = volumeInfoId)
    }
}

fun VolumeInfo.industryIdentifiersAsDatabaseModel(volumeInfoId: Long): List<IndustryIdentifierEntity>? {
    return industryIdentifiers?.map {
        IndustryIdentifierEntity(type = it.type, identifier = it.identifier, volumeInfoId = volumeInfoId)
    }
}

fun VolumeInfo.dimensionsAsDatabaseModel(volumeInfoId: Long): DimensionsEntity? {
   return dimensions?.let {
       DimensionsEntity(height = it.height, width = it.width, thickness = it.thickness, volumeInfoId = volumeInfoId)
   }
}

fun VolumeInfo.categoriesAsDatabaseModel(volumeInfoId: Long): List<CategoryEntity>? {
    return categories?.map {
        CategoryEntity(category = it, volumeInfoId = volumeInfoId)
    }
}

fun VolumeInfo.imageLinksAsDatabaseModel(volumeInfoId: Long): ImageLinksEntity? {
    return imageLinks?.let {
        ImageLinksEntity(thumbnail = it.thumbnail, small = it.small, medium = it.medium, large = it.large,
        smallThumbnail = it.smallThumbnail, extraLarge = it.extraLarge, volumeInfoId = volumeInfoId)
    }
}

fun SaleInfo.asDatabaseModel(bookId: String): SaleInfoEntity {
    return SaleInfoEntity(
            bookId = bookId,
            country = this.country,
            saleability = this.saleability,
            isEbook = this.isEbook,
            buyLink = this.buyLink
    )
}

fun SaleInfo.priceAsDatabaseModel(saleInfoId: Long, priceType: PriceType): PriceEntity {
   return when (priceType) {
       PriceType.RETAIL -> PriceEntity(
               priceType = priceType.name,
               amount = this.retailPrice?.amount,
               currencyCode = this.retailPrice?.currencyCode,
               saleInfoId = saleInfoId)
       PriceType.LIST -> PriceEntity(
               priceType = priceType.name,
               amount = this.listPrice?.amount,
               currencyCode = this.listPrice?.currencyCode,
               saleInfoId = saleInfoId)
   }
}

fun AccessInfo.asDatabaseModel(bookId: String): AccessInfoEntity {
    return AccessInfoEntity(
            bookId = bookId,
            country = this.country,
            viewability = this.viewability,
            accessViewStatus = this.accessViewStatus,
            embeddable = this.embeddable,
            publicDomain = this.publicDomain,
            textToSpeechPermission = this.textToSpeechPermission,
            webReaderLink = this.webReaderLink
    )
}

fun AccessInfo.epubAsDatabaseModel(accessInfoId: Long): EpubEntity? {
    return epub?.let {
        EpubEntity(downloadLink = it.downloadLink, acsTokenLink = it.acsTokenLink,
                isAvailable = it.isAvailable, accessInfoId = accessInfoId)
    }
}

fun AccessInfo.pdfAsDatabaseModel(accessInfoId: Long): PDFEntity? {
    return pdf?.let {
        PDFEntity(downloadLink = it.downloadLink, acsTokenLink = it.acsTokenLink,
                isAvailable = it.isAvailable, accessInfoId = accessInfoId)
    }
}

fun AccessInfo.downloadAccessAsDatabaseModel(accessInfoId: Long): DownloadAccessEntity? {
    return downloadAccess?.let {
        DownloadAccessEntity(
            kind = it.kind,
            volumeId = it.volumeId,
            restricted = it.restricted,
            deviceAllowed = it.deviceAllowed,
            justAcquired = it.justAcquired,
            maxDownloadDevices = it.maxDownloadDevices,
            downloadsAcquired = it.downloadsAcquired,
            nonce = it.nonce,
            source = it.source,
            reasonCode = it.reasonCode,
            message = it.message,
            signature = it.signature,
            accessInfoId = accessInfoId
        )
    }
}

fun SearchInfo.asDatabaseModel(bookId: String): SearchInfoEntity {
    return SearchInfoEntity(
            bookId = bookId,
            textSnippet = this.textSnippet
    )
}

enum class PriceType {
    RETAIL,
    LIST
}
