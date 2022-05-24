package com.ihediohachinedu.bookkeeper.viewmodel

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.ihediohachinedu.bookkeeper.db.Book
import com.ihediohachinedu.bookkeeper.db.BookDao
import com.ihediohachinedu.bookkeeper.db.BookDb
import com.ihediohachinedu.bookkeeper.repository.BookRepo

class BookViewModel(application: Application) : AndroidViewModel(application) {

    //Wrapper function to fetch the data which in turn calls the function in the DAO
    val allBooks: LiveData<List<Book>>
    private val bookRepository = BookRepo(application)
//    private val bookDao:  BookDao
    init {
        //fetch db instance for the insert operation &
        //access db using a DAO object
//        val bookDb = BookDb.getDatabase(application)
//        bookDao = bookDb?.bookDao()!!
        //fetching the list of books(LiveData), from the DAO
        allBooks = bookRepository.allBooks
    }

    //Wrapper function for the insert operation
    //acts as the communication layer between the UI and DB
    fun insert(book: Book) {
//        InsertAsyncTask(bookDao).execute(book)
        bookRepository.insert(book)
    }

    fun update(book: Book) {
//        UpdateAsyncTask(bookDao).execute(book)
        bookRepository.update(book)
    }

    fun delete(book: Book) {
//        DeleteAsyncTask(bookDao).execute(book)
        bookRepository.delete(book)
    }

//    companion object {
//        //perform the Async operation in our background thread
//        private class InsertAsyncTask(private val bookDao: BookDao) : AsyncTask<Book, Void, Void>() {
//            @Deprecated("Deprecated in Java")
//            override fun doInBackground(vararg books: Book): Void? {
//                bookDao.insertBook(books[0])
//                return null
//            }
//        }
//
//        private class UpdateAsyncTask(private val bookDao: BookDao) : AsyncTask<Book, Void, Void>() {
//            @Deprecated("Deprecated in Java")
//            override fun doInBackground(vararg books: Book): Void? {
//                bookDao.update(books[0])
//                return null
//            }
//        }
//
//        private class DeleteAsyncTask(private val bookDao: BookDao) : AsyncTask<Book, Void, Void>() {
//            @Deprecated("Deprecated in Java")
//            override fun doInBackground(vararg books: Book): Void? {
//                bookDao.delete(books[0])
//                return null
//            }
//        }
//    }

}