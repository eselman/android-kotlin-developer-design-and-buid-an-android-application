package com.eselman.android.books.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.eselman.android.books.R
import com.eselman.android.books.databinding.BookItemCardBinding
import com.eselman.android.books.model.Book

class BooksListAdapter(private val bookClick: BookClick) : RecyclerView.Adapter<BooksListAdapter.BookViewHolder>() {
    var booksList: List<Book> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class BookClick(val block: (Book) -> Unit) {
        fun onClick(book: Book) = block(book)
    }

    class BookViewHolder(val viewDataBinding: BookItemCardBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.book_item_card
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val withDataBinding: BookItemCardBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            BookViewHolder.LAYOUT,
            parent,
            false)
        return BookViewHolder(withDataBinding)
    }

    override fun getItemCount() = booksList.size

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.book = booksList[position]
            it.bookClick = bookClick
        }
    }
}
