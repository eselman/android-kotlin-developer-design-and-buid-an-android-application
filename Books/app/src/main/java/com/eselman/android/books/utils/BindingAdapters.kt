package com.eselman.android.books.utils

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.eselman.android.books.R
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat

object BindingAdapters {

    @BindingAdapter("android:fadeVisible")
    @JvmStatic
    fun setFadeVisible(view: View, visible: Boolean? = true) {
        if (view.tag == null) {
            view.tag = true
            view.visibility = if (visible == true) View.VISIBLE else View.GONE
        } else {
            view.animate().cancel()
            if (visible == true) {
                if (view.visibility == View.GONE) {
                    view.fadeIn()
                }
            } else {
                if (view.visibility == View.VISIBLE) {
                    view.fadeOut()
                }
            }
        }
    }

    @BindingAdapter("bookSmallThumbnailImage")
    @JvmStatic
    fun bindBookSmallThumbnailImage(imageView: ImageView, smallThumbNailUrl: String?) {
        smallThumbNailUrl?.let {
            Picasso.with(imageView.context)
                .load(it)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(imageView)
        }
    }

    @BindingAdapter("authors")
    @JvmStatic
    fun bindBookAuthors(authorTextView: TextView, authors: List<String?>?) {
        var authorsStr = ""
        authors?.let {
            if (it.size == 1 && it[0]?.isNotEmpty() == true) {
                authorsStr = it[0] ?: ""
            } else {
                it.forEach { author ->
                    if (author?.isNotEmpty() == true) {
                        authorsStr += author + "\n"
                    }
                }
            }

            authorTextView.text = authorsStr
        }
    }

    @BindingAdapter("publishedDate")
    @JvmStatic
    fun bindPublishedDate(publishedDateTextView: TextView, publishedDate: String?) {
        publishedDate?.let {
            try {
                var simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
                val date = simpleDateFormat.parse(publishedDate)
                simpleDateFormat = SimpleDateFormat("MMMM yyyy")
                publishedDateTextView.text = simpleDateFormat.format(date)
            } catch (_: Exception) {
                Log.d("BOOKS", "Error parsing published date")
            }
        }
    }
}
