package com.ihediohachinedu.bookkeeper.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ihediohachinedu.bookkeeper.databinding.ListItemBinding
import com.ihediohachinedu.bookkeeper.db.Book
import com.ihediohachinedu.bookkeeper.ui.EditBookActivity
import com.ihediohachinedu.bookkeeper.ui.MainActivity
import java.text.SimpleDateFormat
import java.util.*

class BookKeeperAdapter(val context: Context, private val onDeleteClickListener: OnDeleteClickListener)
    : RecyclerView.Adapter<BookKeeperAdapter.BookViewHolder>() {

    interface OnDeleteClickListener {
        fun onDeleteClickListener(myBook: Book)
    }

    //Fetch and display the List of books
    private var bookList: List<Book> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(binding)

    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = bookList[position]
//        holder.setData(book.author!!, book.book!!, position)
        holder.setData(book.book!!, book.lastUpdated, position)
        holder.setListeners()
    }

    override fun getItemCount(): Int = bookList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setBooks(books: List<Book>) {
        //Adapter is the getting the list og books from its activity
        bookList = books
        notifyDataSetChanged()
    }

    inner class BookViewHolder(private val binding: ListItemBinding) :RecyclerView.ViewHolder(binding.root) {
        private var pos: Int = 0

        //populating the listItem with the data
        fun setData(book: String, lastUpdated: Date?, position: Int) {
//            binding.tvAuthor.text = author
            binding.tvBook.text = book
            binding.tvLastUpdated.text = getFormattedDate(lastUpdated)
            this.pos = position
        }

        private fun getFormattedDate(lastUpdated: Date?) : String {

            var time = "Last Updated: "
            time += lastUpdated?.let {
                val sdf = SimpleDateFormat("HH:mm d MMM, yyyy", Locale.getDefault())
                sdf.format(lastUpdated)
            } ?: "Not Found"

            return time
        }
        fun setListeners() {
            binding.root.setOnClickListener {
                //initialize the intent that will take us to our EditBookActivity
                // and pass the book Data with it
                val intent = Intent(context, EditBookActivity::class.java)
                intent.putExtra("id", bookList[pos].id)
                intent.putExtra("author", bookList[pos].author)
                intent.putExtra("book", bookList[pos].book)
                intent.putExtra("description", bookList[pos].description)
                intent.putExtra("lastUpdated", getFormattedDate(bookList[pos].lastUpdated))
                //pass it to EditBook
                (context as Activity).startActivityForResult(intent, MainActivity.UPDATE_BOOK_ACTIVITY_REQUEST_CODE)

            }
            binding.deleteItem.setOnClickListener {
                //callback function
                onDeleteClickListener.onDeleteClickListener(bookList[pos])
            }
        }
    }
}