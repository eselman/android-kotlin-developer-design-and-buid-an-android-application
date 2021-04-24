package com.eselman.android.books.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eselman.android.books.R
import com.eselman.android.books.common.SearchBy
import com.eselman.android.books.databinding.FragmentBooksListBinding
import kotlinx.android.synthetic.main.fragment_books_list.*

class BooksListFragment : Fragment() {
    private val viewModel: BooksListViewModel by activityViewModels()

    private var viewModelAdapter: BooksListAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = FragmentBooksListBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        viewModelAdapter = BooksListAdapter(BooksListAdapter.BookClick {
            val action = BooksListFragmentDirections.actionBooksListFragmentToBookDetailsFragment(it)
            findNavController().navigate(action)
        })

         binding.root.findViewById<RecyclerView>(R.id.books_recycler).apply {
             layoutManager = LinearLayoutManager(context)
             adapter = viewModelAdapter
         }

        val query = BooksListFragmentArgs.fromBundle(requireArguments()).query
        val searchBy = BooksListFragmentArgs.fromBundle(requireArguments()).searchBy

        when (searchBy) {
            SearchBy.AUTHOR.name -> viewModel.getBooksByAuthor(query)
            SearchBy.TITLE.name -> viewModel.getBooksByTitle(query)
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = true
            when (searchBy) {
                SearchBy.AUTHOR.name -> viewModel.refreshBooksByAuthor(query)
                SearchBy.TITLE.name -> viewModel.refreshBooksByTitle(query)
            }
        }

        observeViewModel()

        return binding.root
    }

    private fun observeViewModel() {
        viewModel.booksListByAuthor.observe(viewLifecycleOwner, { booksList ->
             booksList?.apply {
               viewModelAdapter?.booksList = this
                 pullMessage.isVisible = true
                 status_loading_wheel.isVisible = false
                 noDataTextView.isVisible = false
                 if (swipeRefreshLayout.isRefreshing) {
                     swipeRefreshLayout.isRefreshing = false
                 }
             }
        })

        viewModel.booksListByTitle.observe(viewLifecycleOwner, { booksList ->
            booksList?.apply {
                viewModelAdapter?.booksList = this
                pullMessage.isVisible = true
                status_loading_wheel.isVisible = false
                noDataTextView.isVisible = false
                if (swipeRefreshLayout.isRefreshing) {
                    swipeRefreshLayout.isRefreshing = false
                }
            }
        })

        viewModel.showLoading.observe(viewLifecycleOwner, { showLoading ->
            books_recycler.isVisible = !showLoading
            status_loading_wheel.isVisible = showLoading
            noDataTextView.isVisible = !showLoading
            pullMessage.isVisible = !showLoading
        })

        viewModel.showNoData.observe(viewLifecycleOwner, { showNoData ->
            books_recycler.isVisible = !showNoData
            noDataTextView.isVisible = showNoData
            pullMessage.isVisible = !showNoData
        })
    }
}
