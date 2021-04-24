package com.eselman.android.books.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Book(
    val id: String,
    val kind: String,
    val etag: String,
    val selfLink: String,
    var volumeInfo: VolumeInfo? = null,
    var saleInfo: SaleInfo? = null,
    var accessInfo: AccessInfo? = null,
    var searchInfo: SearchInfo? = null
) : Parcelable
