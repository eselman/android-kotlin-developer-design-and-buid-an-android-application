package com.eselman.android.books.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VolumeInfo(
    val volumeInfoId: Long = 0,
    val title: String?,
    val subtitle: String?,
    var authors: MutableList<String?>? = null,
    val publisher: String?,
    val publishedDate: String?,
    val description: String?,
    var industryIdentifiers: List<IndustryIdentifier>? = null,
    val pageCount: Int?,
    var dimensions: Dimensions? = null,
    val printType: String?,
    var categories: MutableList<String?>? = null,
    val averageRating: Double?,
    val ratingsCount: Int?,
    val contentVersion: String?,
    var imageLinks: ImageLinks? = null,
    val language: String?,
    val mainCategory: String?,
    val previewLink: String?,
    val canonicalVolumeLink: String?
) : Parcelable
