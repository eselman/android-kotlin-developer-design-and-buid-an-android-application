package com.eselman.android.books.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.eselman.android.books.R
import com.eselman.android.books.common.SearchBy
import com.eselman.android.books.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = FragmentSearchBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner

        binding.searchBtn.setOnClickListener {
            if (binding.searchQuery.text.isNotEmpty()) {
                when (binding.searchByRadioGroup.checkedRadioButtonId) {
                    R.id.author -> {
                        navigateToBooksList(binding.searchQuery.text.toString(), SearchBy.AUTHOR.name)
                    }
                    R.id.title -> {
                        navigateToBooksList(binding.searchQuery.text.toString(), SearchBy.TITLE.name)
                    }
                }
            } else {
                binding.searchQuery.error = getString(R.string.please_enter_query)
            }
        }

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.books_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.searchBookStores -> {
                val action = SearchFragmentDirections.actionSearchFragmentToBooksLocationFragment()
                findNavController().navigate(action)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun navigateToBooksList(query: String, searchBy: String) {
        val action = SearchFragmentDirections.actionSearchFragmentToBooksListFragment(query, searchBy)
        findNavController().navigate(action)
    }
}
