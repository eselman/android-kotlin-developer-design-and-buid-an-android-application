package com.eselman.android.books.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "author")
data class AuthorEntity(
    @PrimaryKey(autoGenerate = true)
    val authorId: Long = 0,
    val author: String?,
    val volumeInfoId: Long
)
