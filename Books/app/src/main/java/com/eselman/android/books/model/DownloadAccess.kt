package com.eselman.android.books.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DownloadAccess(
    val kind: String?,
    val volumeId: String?,
    val restricted: Boolean?,
    val deviceAllowed: Boolean?,
    val justAcquired: Boolean?,
    val maxDownloadDevices: Int?,
    val downloadsAcquired: Int?,
    val nonce: String?,
    val source: String?,
    val reasonCode: String?,
    val message: String?,
    val signature: String?
) : Parcelable
