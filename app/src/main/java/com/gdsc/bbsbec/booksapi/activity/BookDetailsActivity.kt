/*
 * Copyright (C) 2022 Google Developer Student Clubs BBSBEC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gdsc.bbsbec.booksapi.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.gdsc.bbsbec.booksapi.R
import com.gdsc.bbsbec.booksapi.databinding.ActivityBookDetailsBinding
import com.gdsc.bbsbec.booksapi.model.BookSearchResultData
import com.gdsc.bbsbec.booksapi.viewmodel.BookDataViewModel

class BookDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookDetailsBinding
    private lateinit var mBookDataViewModel: BookDataViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bookThumbnail: String = intent.getStringExtra("bookThumbnail").toString()
        val bookName: String = intent.getStringExtra("bookName").toString()
        val bookPublisher: String = intent.getStringExtra("bookPublisher").toString()
        val bookDescription: String = intent.getStringExtra("bookDescription").toString()
        val previewLink: String = intent.getStringExtra("previewLink").toString()

        binding.bookName.text = bookName
        binding.publisherNameTextView.text = bookPublisher
        binding.bookDescription.text = bookDescription
        binding.bookThumbnail.load(bookThumbnail)

        mBookDataViewModel = ViewModelProvider(this).get(BookDataViewModel::class.java)

        binding.previewButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(previewLink)
            startActivity(intent)
        }

        binding.wishlistImageButton.setOnClickListener {
            if (!binding.wishlistImageButton.equals(R.drawable.ic_filled_star)) {
                binding.wishlistImageButton.setImageResource(R.drawable.ic_filled_star)

                val book = BookSearchResultData(
                    0,
                    bookThumbnail,
                    bookName,
                    bookPublisher,
                    bookDescription,
                    previewLink,
                    bookThumbnail
                )
                mBookDataViewModel.addBook(book)
                Toast.makeText(applicationContext, "Book added to wishlist", Toast.LENGTH_LONG)
                    .show()
            } else {
                Toast.makeText(applicationContext, "Book removed from wishlist", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }
}