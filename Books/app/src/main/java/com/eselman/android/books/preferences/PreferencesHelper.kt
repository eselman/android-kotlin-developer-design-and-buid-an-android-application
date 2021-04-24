package com.eselman.android.books.preferences

import android.content.Context
import com.eselman.android.books.common.Constants
import com.eselman.android.books.common.SearchBy

object PreferencesHelper {
    fun getLastSearchProperty(context: Context, searchBy: SearchBy): String {
        val sharedPreferences = context.getSharedPreferences(Constants.PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)
        val key = when (searchBy) {
            SearchBy.AUTHOR -> Constants.LAST_SEARCH_PREFS_KEY_BY_AUTHOR
            SearchBy.TITLE -> Constants.LAST_SEARCH_PREFS_KEY_BY_TITLE
        }
        return sharedPreferences.getString(key, "") ?: ""
    }

    fun saveLastSearchProperty(context: Context, query: String, searchBy: SearchBy) {
        val sharedPreferences = context.getSharedPreferences(Constants.PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)
        val key = when (searchBy) {
            SearchBy.AUTHOR -> Constants.LAST_SEARCH_PREFS_KEY_BY_AUTHOR
            SearchBy.TITLE -> Constants.LAST_SEARCH_PREFS_KEY_BY_TITLE
        }
        with(sharedPreferences.edit()) {
            putString(key, query)
            commit()
        }
    }
}
