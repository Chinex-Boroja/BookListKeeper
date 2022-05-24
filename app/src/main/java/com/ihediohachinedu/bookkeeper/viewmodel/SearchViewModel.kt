package com.ihediohachinedu.bookkeeper.viewmodel

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.ihediohachinedu.bookkeeper.db.Book
import com.ihediohachinedu.bookkeeper.db.BookDao
import com.ihediohachinedu.bookkeeper.db.BookDb
import com.ihediohachinedu.bookkeeper.repository.BookRepo

class SearchViewModel(application: Application): AndroidViewModel(application){

    private val bookRepository = BookRepo(application)

//    private val bookDao: BookDao
//
//    init {
//        val bookDB = BookDb.getDatabase(application)
//        bookDao = bookDB!!.bookDao()
//    }

    fun getBooksByBookOrAuthor(searchQuery: String): LiveData<List<Book>> {
        return bookRepository.getBooksByBookOrAuthor(searchQuery)
//        return bookDao.getBooksByBookOrAuthor(searchQuery)
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
//
//        private class UpdateAsyncTask(private val bookDao: BookDao) : AsyncTask<Book, Void, Void>() {
//
//            override fun doInBackground(vararg books: Book): Void? {
//                bookDao.update(books[0])
//                return null
//            }
//        }
//
//        private class DeleteAsyncTask(private val bookDao: BookDao) : AsyncTask<Book, Void, Void>() {
//
//            override fun doInBackground(vararg books: Book): Void? {
//                bookDao.delete(books[0])
//                return null
//            }
//        }
//    }

}