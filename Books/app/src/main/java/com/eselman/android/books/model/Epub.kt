package com.eselman.android.books.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Epub(
    val downloadLink: String?,
    val acsTokenLink: String?,
    val isAvailable: Boolean?
) : Parcelable
