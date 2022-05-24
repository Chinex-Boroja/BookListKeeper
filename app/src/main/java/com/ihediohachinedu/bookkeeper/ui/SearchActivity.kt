package com.ihediohachinedu.bookkeeper.ui

import android.app.Activity
import android.app.SearchManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ihediohachinedu.bookkeeper.R
import com.ihediohachinedu.bookkeeper.adapter.BookKeeperAdapter
import com.ihediohachinedu.bookkeeper.databinding.ActivityMainBinding
import com.ihediohachinedu.bookkeeper.db.Book
import com.ihediohachinedu.bookkeeper.viewmodel.BookViewModel
import com.ihediohachinedu.bookkeeper.viewmodel.BookViewModelFactory
import com.ihediohachinedu.bookkeeper.viewmodel.SearchViewModel
import com.ihediohachinedu.bookkeeper.viewmodel.SearchViewModelFactory
import java.util.*

class SearchActivity : AppCompatActivity(), BookKeeperAdapter.OnDeleteClickListener {

    private lateinit var searchViewModel: SearchViewModel
    private var bookListAdapter: BookKeeperAdapter? = null
    private val TAG = this.javaClass.simpleName

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

       binding.fab.visibility = View.INVISIBLE

        val viewModelFactory = SearchViewModelFactory(application)
        searchViewModel = ViewModelProvider(this, viewModelFactory)[(SearchViewModel::class.java)]

        bookListAdapter = BookKeeperAdapter(this, this)
        binding.rcv.adapter = bookListAdapter
        binding.rcv.layoutManager = LinearLayoutManager(this)

        binding.rcv.addItemDecoration(DividerItemDecoration(baseContext, LinearLayoutManager.VERTICAL))

        handleIntent(intent)

    }
    private fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {

            val searchQuery = intent.getStringExtra(SearchManager.QUERY)

            Log.i(TAG, "Search Query is $searchQuery")

            searchViewModel.getBooksByBookOrAuthor("%$searchQuery%")
                .observe(this, Observer { books ->
                    books?.let { bookListAdapter!!.setBooks(books) }
                })
        }
    }


    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SearchActivity.UPDATE_BOOK_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // Code to edit book
            val bookId = data!!.getStringExtra(EditBookActivity.ID)
            val authorName = data.getStringExtra(EditBookActivity.UPDATED_AUTHOR)
            val bookName = data.getStringExtra(EditBookActivity.UPDATED_BOOK)
            val description = data.getStringExtra(EditBookActivity.UPDATED_DESCRIPTION)
            val currentTime = Calendar.getInstance().time

            val book = Book(bookId!!, authorName, bookName, description!!, currentTime)
            searchViewModel.update(book)

            Toast.makeText(applicationContext, R.string.updated, Toast.LENGTH_LONG).show()

        } else {
            Toast.makeText(applicationContext, R.string.not_saved, Toast.LENGTH_LONG).show()
        }
    }

    override fun onDeleteClickListener(myBook: Book) {
        searchViewModel.delete(myBook)
        Toast.makeText(applicationContext, R.string.deleted, Toast.LENGTH_LONG).show()
    }

    companion object {
        const val UPDATE_BOOK_ACTIVITY_REQUEST_CODE = 2
    }

}