package com.ihediohachinedu.bookkeeper.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BookDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBook(book: Book)

    @Query("SELECT * FROM ${Book.TABLE_NAME}")
    fun getAllBooks() : LiveData<List<Book>>
//
//    @get:Query("SELECT * FROM ${Book.TABLE_NAME}")
//    val allBooks: LiveData<List<Book>>

    @Query("SELECT * FROM books WHERE book LIKE :searchString OR author LIKE :searchString")
    fun getBooksByBookOrAuthor(searchString: String) : LiveData<List<Book>>

    @Update
    fun update(book: Book)

    @Delete
    fun delete(book: Book)
}