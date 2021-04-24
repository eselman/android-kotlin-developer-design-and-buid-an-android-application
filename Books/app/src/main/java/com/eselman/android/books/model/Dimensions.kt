package com.eselman.android.books.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Dimensions(
    val height:	String?,
    val width: String?,
    val thickness: String?
) : Parcelable
