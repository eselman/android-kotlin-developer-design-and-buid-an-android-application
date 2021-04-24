package com.eselman.android.books.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.eselman.android.books.model.SearchInfo

@Entity(tableName = "search_info")
data class SearchInfoEntity(
    @PrimaryKey(autoGenerate = true)
    val searchInfoId: Long = 0,
    val bookId: String,
    val textSnippet: String?
)

fun SearchInfoEntity.asDomainModel(): SearchInfo {
    return SearchInfo(textSnippet = textSnippet)
}
