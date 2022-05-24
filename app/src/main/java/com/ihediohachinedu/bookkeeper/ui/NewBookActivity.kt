package com.ihediohachinedu.bookkeeper.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View

import com.ihediohachinedu.bookkeeper.databinding.ActivityNewBookBinding

class NewBookActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewBookBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewBookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.txvLastUpdated.visibility = View.INVISIBLE

        binding.btnSave.setOnClickListener {
            val resultIntent = Intent()

            if (TextUtils.isEmpty(binding.etAuthorName.text) ||
                    TextUtils.isEmpty(binding.etBookName.text)) {
                setResult(Activity.RESULT_CANCELED, resultIntent)
            } else {
                val author = binding.etAuthorName.text.toString()
                val book = binding.etBookName.text.toString()
                val description = binding.etDescription.text.toString()


                resultIntent.putExtra(NEW_AUTHOR, author)
                resultIntent.putExtra(NEW_BOOK, book)
                resultIntent.putExtra(NEW_DESCRIPTION, description)
                setResult(Activity.RESULT_OK, resultIntent)
            }
            finish()
        }
        binding.btnCancel.setOnClickListener {
            finish()
        }
    }
    companion object {
        const val NEW_AUTHOR = "new_author"
        const val NEW_BOOK = "new_book"
        const val NEW_DESCRIPTION = "new_description"
    }
}