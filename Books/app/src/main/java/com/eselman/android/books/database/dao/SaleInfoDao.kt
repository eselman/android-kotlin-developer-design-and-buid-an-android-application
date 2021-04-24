package com.eselman.android.books.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.eselman.android.books.database.entities.PriceEntity
import com.eselman.android.books.database.entities.SaleInfoEntity
import com.eselman.android.books.model.SaleInfo
import com.eselman.android.books.model.mappers.PriceType
import com.eselman.android.books.model.mappers.asDatabaseModel
import com.eselman.android.books.model.mappers.priceAsDatabaseModel

@Dao
abstract class SaleInfoDao {
    fun insertFullSaleInfo(saleInfo: SaleInfo, bookId: String) {
        insertSaleInfo(saleInfo.asDatabaseModel(bookId))
        val saleInfoEntity = getSaleInfoByBookId(bookId)

        val listPrice = saleInfo.priceAsDatabaseModel(saleInfoEntity.saleInfoId, PriceType.LIST)
        insertPrice(listPrice)

        val retailPrice = saleInfo.priceAsDatabaseModel(saleInfoEntity.saleInfoId, PriceType.RETAIL)
        insertPrice(retailPrice)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertSaleInfo(saleInfo: SaleInfoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertPrice(price: PriceEntity)

    @Query("SELECT * FROM sale_info WHERE bookId = :bookId")
    abstract fun getSaleInfoByBookId(bookId: String): SaleInfoEntity

    @Query("SELECT * FROM price WHERE saleInfoId = :saleInfoId and priceType=:priceType")
    abstract fun getPriceByType(saleInfoId: Long, priceType: String): PriceEntity

    @Query("DELETE FROM sale_info")
    abstract fun deleteSaleInfo()

    @Query("DELETE FROM price")
    abstract fun deletePrice()
}
