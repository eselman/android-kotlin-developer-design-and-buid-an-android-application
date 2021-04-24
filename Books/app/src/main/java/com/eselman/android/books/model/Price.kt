package com.eselman.android.books.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Price(val amount: Double?, val currencyCode: String?) : Parcelable
