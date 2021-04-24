package com.eselman.android.books.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.eselman.android.books.model.Epub

@Entity(tableName = "epub")
data class EpubEntity(
    @PrimaryKey(autoGenerate = true)
    val epubId: Long = 0,
    val downloadLink: String?,
    val acsTokenLink: String?,
    val isAvailable: Boolean?,
    val accessInfoId: Long
)

fun EpubEntity.asDomainModel(): Epub {
    return Epub(
        downloadLink = downloadLink,
        acsTokenLink = acsTokenLink,
        isAvailable = isAvailable
    )
}
