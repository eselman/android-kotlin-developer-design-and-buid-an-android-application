package com.eselman.android.books.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.eselman.android.books.database.entities.SearchInfoEntity

@Dao
interface SearchInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSearchInfo(searchInfoEntity: SearchInfoEntity)

    @Query("SELECT * FROM search_info WHERE bookId = :bookId")
    fun getSearchInfoByBookId(bookId: String): SearchInfoEntity?

    @Query("DELETE FROM search_info")
    fun deleteSearchInfo()
}
