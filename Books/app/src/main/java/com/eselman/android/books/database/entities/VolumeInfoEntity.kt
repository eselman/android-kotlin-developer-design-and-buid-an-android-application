package com.eselman.android.books.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.eselman.android.books.model.VolumeInfo

@Entity(tableName = "volume_info")
data class VolumeInfoEntity(
    @PrimaryKey(autoGenerate = true)
    val volumeInfoId: Long = 0,
    val bookId: String,
    val title: String?,
    val subtitle: String?,
    val publisher: String?,
    val publishedDate: String?,
    val description: String?,
    val pageCount: Int?,
    val printType: String?,
    val averageRating: Double?,
    val ratingsCount: Int?,
    val contentVersion: String?,
    val language: String?,
    val mainCategory: String?,
    val previewLink: String?,
    val canonicalVolumeLink: String?
)

fun VolumeInfoEntity.asDomainModel(): VolumeInfo {
    return VolumeInfo(
        volumeInfoId = this.volumeInfoId,
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
