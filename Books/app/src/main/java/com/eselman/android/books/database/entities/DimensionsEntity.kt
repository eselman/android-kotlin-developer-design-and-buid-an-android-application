package com.eselman.android.books.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.eselman.android.books.model.Dimensions

@Entity(tableName = "dimensions")
data class DimensionsEntity(
    @PrimaryKey(autoGenerate = true)
    val dimensionsId: Long = 0,
    val height:	String?,
    val width: String?,
    val thickness: String?,
    val volumeInfoId: Long
)

fun DimensionsEntity.asDomainModel(): Dimensions {
    return Dimensions(
        height = height,
        width = width,
        thickness = thickness
    )
}
