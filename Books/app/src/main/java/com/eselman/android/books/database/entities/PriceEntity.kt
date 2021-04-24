package com.eselman.android.books.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.eselman.android.books.model.Price

@Entity(tableName = "price")
data class PriceEntity(
    @PrimaryKey(autoGenerate = true)
    val priceId: Long = 0,
    val priceType: String, // listPrice or retailPrice
    val amount: Double?,
    val currencyCode: String?,
    val saleInfoId: Long
)

fun PriceEntity.asDomainModel(): Price {
    return Price(
        amount = amount,
        currencyCode = currencyCode
    )
}
