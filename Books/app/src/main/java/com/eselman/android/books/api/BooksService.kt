package com.eselman.android.books.api

import com.eselman.android.books.model.BooksResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface BooksService {
    @GET("volumes")
    suspend fun getVolumes(
        @Query("q") filter: String,
        @Query("startIndex") startIndex: Int,
        @Query("maxResults") maxResults: Int,
        @Query("orderBy") orderBy: String
    ): BooksResponse
}
