package com.eselman.android.books.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.eselman.android.books.model.Book

@Entity(tableName = "book")
data class BookEntity constructor(
    @PrimaryKey
    val id: String,
    val kind: String,
    val etag: String,
    val selfLink: String,
)

fun BookEntity.asDomainModel(): Book {
    return Book(
            id = this.id,
            kind = this.kind,
            etag = this.etag,
            selfLink = this.selfLink
        )
}
