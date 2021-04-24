package com.eselman.android.books.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ImageLinks(
    val thumbnail: String?,
    val small: String?,
    val medium: String?,
    val large: String?,
    val smallThumbnail: String?,
    val extraLarge: String?
) : Parcelable
