package com.eselman.android.books.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.eselman.android.books.model.ImageLinks

@Entity(tableName = "image_links")
data class ImageLinksEntity(
    @PrimaryKey(autoGenerate = true)
    val imageLinksId: Long = 0,
    val thumbnail: String?,
    val small: String?,
    val medium: String?,
    val large: String?,
    val smallThumbnail: String?,
    val extraLarge: String?,
    val volumeInfoId: Long
)

fun ImageLinksEntity.asDomainModel(): ImageLinks {
    return ImageLinks(
        thumbnail = thumbnail,
        small = small,
        medium = medium,
        large = large,
        smallThumbnail = smallThumbnail,
        extraLarge = extraLarge
    )
}
