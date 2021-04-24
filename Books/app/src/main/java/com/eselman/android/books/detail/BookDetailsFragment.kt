package com.eselman.android.books.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.eselman.android.books.databinding.FragmentBookDetailsBinding

class BookDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentBookDetailsBinding.inflate(inflater)
        binding.lifecycleOwner = this
        val book = BookDetailsFragmentArgs.fromBundle(requireArguments()).selectedBook
        binding.book = book

        if (book.saleInfo != null && book.saleInfo?.buyLink != null) {
            binding.buyBtn.isVisible = true
            binding.buyBtn.setOnClickListener {
                val openUrlIntent = Intent(Intent.ACTION_VIEW, Uri.parse(book.saleInfo?.buyLink))
                if (openUrlIntent.resolveActivity(requireActivity().packageManager) != null) {
                    startActivity(openUrlIntent)
                }
            }
        } else {
            binding.buyBtn.isVisible = false
        }

        return binding.root
    }
}
