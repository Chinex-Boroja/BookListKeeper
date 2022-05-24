package com.ihediohachinedu.bookkeeper.ui

import android.app.Activity
import android.app.SearchManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
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
import java.util.*

class MainActivity : AppCompatActivity(), BookKeeperAdapter.OnDeleteClickListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding


    private lateinit var bookViewModel: BookViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        //Create the instance of the book adapter and set up the recyclerview
        val bookListAdapter = BookKeeperAdapter(this, this)
        binding.rcv.adapter = bookListAdapter
        binding.rcv.layoutManager = LinearLayoutManager(this)

        binding.rcv.addItemDecoration(DividerItemDecoration(baseContext, LinearLayoutManager.VERTICAL))

 //        val navController = findNavController(R.id.nav_host_fragment_content_main)
//        appBarConfiguration = AppBarConfiguration(navController.graph)
//        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            val intent = Intent(this, NewBookActivity::class.java)
            startActivityForResult(intent, NEW_BOOK_ACTIVITY_REQUEST_CODE)
        }
        val viewModelFactory = BookViewModelFactory(application)
        bookViewModel = ViewModelProvider(this, viewModelFactory)[(BookViewModel::class.java)]

        //From the MA make a request to the VM to give the LiveData of the list of books
        //this gives the list of books and populate it in the RV which is the UI
        bookViewModel.allBooks.observe(this, Observer { books ->
            books?.let {
                //set the books into the UI, only if it is not null
                bookListAdapter.setBooks(books)
            }
        })
    }


    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == NEW_BOOK_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {

            val id = UUID.randomUUID().toString()
            val authorName = data?.getStringExtra(NewBookActivity.NEW_AUTHOR)
            val bookName = data?.getStringExtra(NewBookActivity.NEW_BOOK)
            val description = data?.getStringExtra(NewBookActivity.NEW_DESCRIPTION)
            val currentTime = Calendar.getInstance().time

            //Creating the  wrapper object
            val book = Book(id, authorName, bookName, description!!, currentTime)
            bookViewModel.insert(book)

            Toast.makeText(applicationContext, R.string.saved, Toast.LENGTH_LONG).show()
        }

        else if (requestCode == UPDATE_BOOK_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val id = data?.getStringExtra(EditBookActivity.ID)
            val authorName = data?.getStringExtra(EditBookActivity.UPDATED_AUTHOR)
            val bookName = data?.getStringExtra(EditBookActivity.UPDATED_BOOK)
            val description = data?.getStringExtra(EditBookActivity.UPDATED_DESCRIPTION)
            val currentTime = Calendar.getInstance().time

            val book = Book(id!!, authorName, bookName, description!!, currentTime)
                bookViewModel.update(book)

            Toast.makeText(applicationContext, R.string.updated, Toast.LENGTH_LONG).show()

        }
        else {
            Toast.makeText(applicationContext, R.string.not_saved, Toast.LENGTH_LONG).show()
        }

    }


//    override fun onSupportNavigateUp(): Boolean {
//        val navController = findNavController(R.id.nav_host_fragment_content_main)
//        return navController.navigateUp(appBarConfiguration)
//                || super.onSupportNavigateUp()
//    }

    override fun onDeleteClickListener(myBook: Book) {
        bookViewModel.delete(myBook)
        Toast.makeText(applicationContext, R.string.deleted, Toast.LENGTH_LONG).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)

        //Get the SearchView and set the searchable configuration
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView

        // Setting the SearchResultActivity to show the result
        val componentName = ComponentName(this, SearchActivity::class.java)
        val searchableInfo = searchManager.getSearchableInfo(componentName)
        searchView.setSearchableInfo(searchableInfo)

        return true
    }
    companion object {
        private const val NEW_BOOK_ACTIVITY_REQUEST_CODE = 1
        const val UPDATE_BOOK_ACTIVITY_REQUEST_CODE = 2
    }
}