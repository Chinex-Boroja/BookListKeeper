package com.ihediohachinedu.bookkeeper.repository

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.ihediohachinedu.bookkeeper.db.Book
import com.ihediohachinedu.bookkeeper.db.BookDao
import com.ihediohachinedu.bookkeeper.db.BookDb

class BookRepo (application: Application){

    val allBooks: LiveData<List<Book>>
    private val bookDao: BookDao

    init {
        val bookDb = BookDb.getDatabase(application)
        bookDao = bookDb!!.bookDao()
        allBooks = bookDao.getAllBooks()
    }

    fun getBooksByBookOrAuthor(searchQuery: String): LiveData<List<Book>> {
        return bookDao.getBooksByBookOrAuthor(searchQuery)
    }

    fun insert(book: Book) {
        InsertAsyncTask(bookDao).execute(book)
    }

    fun update(book: Book) {
        UpdateAsyncTask(bookDao).execute(book)
    }

    fun delete(book: Book) {
        DeleteAsyncTask(bookDao).execute(book)
    }

    companion object {
        private class InsertAsyncTask(private val bookDao: BookDao) : AsyncTask<Book, Void, Void>() {

            @Deprecated("Deprecated in Java")
            override fun doInBackground(vararg books: Book): Void? {
                bookDao.insertBook(books[0])
                return null
            }
        }

        private class UpdateAsyncTask(private val bookDao: BookDao) : AsyncTask<Book, Void, Void>() {

            @Deprecated("Deprecated in Java")
            override fun doInBackground(vararg books: Book): Void? {
                bookDao.update(books[0])
                return null
            }
        }

        private class DeleteAsyncTask(private val bookDao: BookDao) : AsyncTask<Book, Void, Void>() {

            @Deprecated("Deprecated in Java")
            override fun doInBackground(vararg books: Book): Void? {
                bookDao.delete(books[0])
                return null
            }
        }
    }

}