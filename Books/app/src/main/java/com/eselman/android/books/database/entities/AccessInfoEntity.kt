package com.eselman.android.books.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.eselman.android.books.model.AccessInfo

@Entity(tableName = "access_info")
data class AccessInfoEntity(
    @PrimaryKey(autoGenerate = true)
    val accessInfoId: Long = 0,
    val bookId: String,
    val country: String?,
    val viewability: String?,
    val accessViewStatus: String?,
    val embeddable: Boolean?,
    val publicDomain: Boolean?,
    val textToSpeechPermission: String?,
    val webReaderLink: String?
)

fun AccessInfoEntity.asDomainModel(): AccessInfo {
    return AccessInfo(
        accessInfoId = accessInfoId,
        country = country,
        viewability = viewability,
        accessViewStatus = accessViewStatus,
        embeddable = embeddable,
        publicDomain = publicDomain,
        textToSpeechPermission = textToSpeechPermission,
        webReaderLink = webReaderLink
    )
}
