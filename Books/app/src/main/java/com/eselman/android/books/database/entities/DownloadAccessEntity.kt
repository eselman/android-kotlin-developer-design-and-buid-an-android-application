package com.eselman.android.books.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.eselman.android.books.model.DownloadAccess

@Entity(tableName = "download_access")
data class DownloadAccessEntity(
    @PrimaryKey(autoGenerate = true)
    val downloadAccessId: Long = 0,
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
    val signature: String?,
    val accessInfoId: Long
)

fun DownloadAccessEntity.asDomainModel(): DownloadAccess {
    return DownloadAccess(
        kind = kind,
        volumeId = volumeId,
        restricted = restricted,
        deviceAllowed = deviceAllowed,
        justAcquired = justAcquired,
        maxDownloadDevices = maxDownloadDevices,
        downloadsAcquired = downloadsAcquired,
        nonce = nonce,
        source = source,
        reasonCode = reasonCode,
        message = message,
        signature = signature
    )
}
