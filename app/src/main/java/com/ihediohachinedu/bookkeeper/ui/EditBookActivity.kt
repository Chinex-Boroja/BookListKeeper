package com.ihediohachinedu.bookkeeper.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.ihediohachinedu.bookkeeper.databinding.ActivityNewBookBinding

class EditBookActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewBookBinding
    var id: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewBookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Fetch the data and populate the UI
        val bundle: Bundle? = intent.extras
        bundle?.let {
            id = bundle.getString("id")
            val book = bundle.getString("book")
            val author: String? = bundle.getString("author")
            val description: String? = bundle.getString("description")
            val lastUpdated: String? = bundle.getString("lastUpdated")

            //set the data in the edit text
            binding.etAuthorName.setText(author)
            binding.etBookName.setText(book)
            binding.etDescription.setText(description)
            binding.txvLastUpdated.text = lastUpdated

        }

        binding.btnSave.setOnClickListener {
            val updatedAuthor = binding.etAuthorName.text.toString()
            val updatedBook = binding.etBookName.text.toString()
            val updatedDescription = binding.etDescription.text.toString()

            val resultIntent = Intent()
            resultIntent.putExtra(ID, id)
            resultIntent.putExtra(UPDATED_AUTHOR, updatedAuthor)
            resultIntent.putExtra(UPDATED_BOOK, updatedBook)
            resultIntent.putExtra(UPDATED_DESCRIPTION, updatedDescription)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

        binding.btnCancel.setOnClickListener {
            finish()
        }
    }

    companion object {
        const val ID = "book_id"
        const val UPDATED_AUTHOR = "author_name"
        const val UPDATED_BOOK = "book_name"
        const val UPDATED_DESCRIPTION = "description"
    }
}