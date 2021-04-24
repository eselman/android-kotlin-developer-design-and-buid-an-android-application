package com.eselman.android.books.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AccessInfo(
    val accessInfoId: Long = 0,
    val country: String?,
    val viewability: String?,
    var epub: Epub? = null,
    var pdf: PDF? = null,
    val accessViewStatus: String?,
    val embeddable: Boolean?,
    val publicDomain: Boolean?,
    val textToSpeechPermission: String?,
    val webReaderLink: String?,
    var downloadAccess: DownloadAccess? = null
) : Parcelable
