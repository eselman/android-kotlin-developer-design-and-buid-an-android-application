package com.eselman.android.books.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.eselman.android.books.model.SaleInfo

@Entity(tableName = "sale_info")
data class SaleInfoEntity(
    @PrimaryKey(autoGenerate = true)
    val saleInfoId: Long = 0,
    val bookId: String?,
    val country: String?,
    val saleability: String?,
    val isEbook: Boolean?,
    val buyLink: String?
)

fun SaleInfoEntity.asDomainModel(): SaleInfo {
    return SaleInfo(
        saleInfoId = saleInfoId,
        country = country,
        saleability = saleability,
        isEbook = isEbook,
        buyLink = buyLink
    )
}
