package com.eselman.android.books.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.eselman.android.books.database.entities.BookEntity

@Dao
interface BookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBook(book: BookEntity)

    @Query("DELETE FROM book")
    fun deleteBooks()

    @Query("SELECT * FROM book")
    fun getBooks(): List<BookEntity>
}
