package com.ihediohachinedu.bookkeeper.db

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity (tableName = Book.TABLE_NAME)
data class Book(

    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = ID)
    val id: String = EMPTY_STRING,
    @ColumnInfo(name = AUTHOR)
    val author: String? = null,
    @ColumnInfo(name = BOOK)
    val book: String? = null,

    //Database Migration
    val description: String,

    @ColumnInfo(name = "last_updated")
    val lastUpdated: Date?

) {

    companion object {
        const val TABLE_NAME = "books"
        const val ID = "id"
        const val EMPTY_STRING = ""
        const val AUTHOR = "author"
        const val BOOK = "book"
    }
}
