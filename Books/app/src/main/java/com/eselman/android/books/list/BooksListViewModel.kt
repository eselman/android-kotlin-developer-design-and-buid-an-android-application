package com.eselman.android.books.list

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.eselman.android.books.common.SearchBy
import com.eselman.android.books.database.getDatabase
import com.eselman.android.books.model.Book
import com.eselman.android.books.preferences.PreferencesHelper
import com.eselman.android.books.repository.BooksRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class BooksListViewModel(private val app: Application) : AndroidViewModel(app) {
    private val viewModelJob = SupervisorJob()

    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val database = getDatabase(app)

    private val booksRepository = BooksRepository(database)

    val booksListByAuthor = MutableLiveData<List<Book>>()

    val booksListByTitle = MutableLiveData<List<Book>>()

    val showLoading = MutableLiveData<Boolean>()

    val showNoData = MutableLiveData<Boolean>()

    fun getBooksByAuthor(query: String) {
        viewModelScope.launch {
            try {
                showLoading.value = true
                val books = booksRepository.getBooks(query, SearchBy.AUTHOR)
                if (books.isNotEmpty()) {
                    booksListByAuthor.value = books
                    showLoading.value = false
                    showNoData.value = false
                } else {
                    PreferencesHelper.saveLastSearchProperty(app, query, SearchBy.AUTHOR)
                    booksRepository.refreshBooksByAuthor(query)
                    val refreshedBooks = booksRepository.getBooks(query, SearchBy.AUTHOR)
                    if (refreshedBooks.isNotEmpty()) {
                        booksListByAuthor.value = refreshedBooks
                        showLoading.value = false
                        showNoData.value = false
                    } else {
                        showLoading.value = false
                        showNoData.value = true
                    }
                }
            } catch (e: Exception) {
                Log.e("BOOKS", "Error getting books by author ${e.localizedMessage}")
                showLoading.value = false
                showNoData.value = true
            }
        }
    }

    fun getBooksByTitle(query: String) {
        viewModelScope.launch {
            try {
                showLoading.value = true
                val books = booksRepository.getBooks(query, SearchBy.TITLE)
                if (books.isNotEmpty()) {
                    booksListByTitle.value = books
                    showLoading.value = false
                    showNoData.value = false
                } else {
                    PreferencesHelper.saveLastSearchProperty(app, query, SearchBy.TITLE)
                    booksRepository.refreshBooksByTitle(query)
                    val refreshedBooks = booksRepository.getBooks(query, SearchBy.TITLE)
                    if (refreshedBooks.isNotEmpty()) {
                        booksListByTitle.value = refreshedBooks
                        showLoading.value = false
                        showNoData.value = false
                    } else {
                        showLoading.value = false
                        showNoData.value = true
                    }
                }
            } catch (e: Exception) {
                Log.e("BOOKS", "Error getting books by title ${e.localizedMessage}")
                showLoading.value = false
                showNoData.value = true
            }
        }
    }

    fun refreshBooksByAuthor(query: String) {
        viewModelScope.launch {
            try {
                PreferencesHelper.saveLastSearchProperty(app, query, SearchBy.AUTHOR)
                booksRepository.refreshBooksByAuthor(query)
                val refreshedBooks = booksRepository.getBooks(query, SearchBy.AUTHOR)
                if (refreshedBooks.isNotEmpty()) {
                    booksListByAuthor.value = refreshedBooks
                    showNoData.value = false
                } else {
                    showNoData.value = true
                }
            } catch (e: Exception) {
                Log.e("BOOKS", "Error getting books by author ${e.localizedMessage}")
                showLoading.value = false
                showNoData.value = true
            }
        }
    }

    fun refreshBooksByTitle(query: String) {
        viewModelScope.launch {
            try {
                PreferencesHelper.saveLastSearchProperty(app, query, SearchBy.TITLE)
                booksRepository.refreshBooksByTitle(query)
                val refreshedBooks = booksRepository.getBooks(query, SearchBy.TITLE)
                if (refreshedBooks.isNotEmpty()) {
                    booksListByTitle.value = refreshedBooks
                    showNoData.value = false
                } else {
                    showNoData.value = true
                }
            } catch (e: Exception) {
                Log.e("BOOKS", "Error getting books by title ${e.localizedMessage}")
                showLoading.value = false
                showNoData.value = true
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
