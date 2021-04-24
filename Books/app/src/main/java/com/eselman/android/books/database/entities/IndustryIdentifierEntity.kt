package com.eselman.android.books.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "industry_identifier")
data class IndustryIdentifierEntity(
    @PrimaryKey(autoGenerate = true)
    val primaryIdentifierId: Long = 0,
    val type: String?,
    val identifier: String?,
    val volumeInfoId: Long
)
