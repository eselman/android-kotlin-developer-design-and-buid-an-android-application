package com.eselman.android.books.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SaleInfo(
    val saleInfoId: Long = 0,
    val country: String?,
    val saleability: String?,
    val isEbook: Boolean?,
    var listPrice: Price? = null,
    var retailPrice: Price? = null,
    val buyLink: String?
) : Parcelable
