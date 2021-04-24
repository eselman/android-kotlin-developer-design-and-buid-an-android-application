package com.eselman.android.books.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PDF(
    val downloadLink: String?,
    val acsTokenLink: String?,
    val isAvailable: Boolean?
) : Parcelable
