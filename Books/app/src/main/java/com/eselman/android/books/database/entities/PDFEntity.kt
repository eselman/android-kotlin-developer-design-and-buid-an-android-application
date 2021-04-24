package com.eselman.android.books.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.eselman.android.books.model.PDF

@Entity(tableName = "pdf")
data class PDFEntity(
    @PrimaryKey(autoGenerate = true)
    val pdfId: Long = 0,
    val downloadLink: String?,
    val acsTokenLink: String?,
    val isAvailable: Boolean?,
    val accessInfoId: Long
)

fun PDFEntity.asDomainModel(): PDF {
    return PDF(
        downloadLink = downloadLink,
        acsTokenLink = acsTokenLink,
        isAvailable = isAvailable
    )
}
