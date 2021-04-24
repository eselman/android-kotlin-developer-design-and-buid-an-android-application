package com.eselman.android.books.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BooksResponse(val kind: String, val totalItems: Int, val items: List<Book>) : Parcelable
