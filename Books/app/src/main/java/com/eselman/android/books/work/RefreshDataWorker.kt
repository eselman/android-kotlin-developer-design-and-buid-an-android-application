package com.eselman.android.books.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.eselman.android.books.common.SearchBy
import com.eselman.android.books.database.getDatabase
import com.eselman.android.books.preferences.PreferencesHelper
import com.eselman.android.books.repository.BooksRepository
import retrofit2.HttpException

class RefreshDataWorker(appContext: Context, params: WorkerParameters) : CoroutineWorker(appContext, params) {
    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        val database = getDatabase(applicationContext)
        val repository = BooksRepository(database)
        return try {
            val lastSearchByAuthor = PreferencesHelper.getLastSearchProperty(applicationContext, SearchBy.AUTHOR)
            val lastSearchByTitle = PreferencesHelper.getLastSearchProperty(applicationContext, SearchBy.TITLE)
            repository.cleanAll()
            repository.refreshBooks(lastSearchByAuthor, lastSearchByTitle)
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
}
