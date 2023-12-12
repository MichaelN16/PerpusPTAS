package com.perkantas.perpusptas_new.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.perkantas.perpusptas_new.Adapter.AdapterBook
import com.perkantas.perpusptas_new.Constants
import com.perkantas.perpusptas_new.Model.BookResponse
import com.perkantas.perpusptas_new.R
import com.perkantas.perpusptas_new.databinding.ActivityBookDetailBinding

class BookDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBookDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //handle click rent
        binding.rentBookBtn.setOnClickListener {
            startActivity(Intent(this, ProfileEditActivity::class.java))
        }

        //handle back button
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

        // Retrieve the data from the intent
        val bookData: BookResponse.DataBook? =
            intent.getSerializableExtra("dataBook") as BookResponse.DataBook?


        if (bookData != null) {
            // Set the book details to the views
            binding.titleTv.text = bookData.bookTitle
            binding.bookIndTv.text = bookData.bookCode
            binding.categoryTv.text = bookData.category
            binding.authorTv.text = bookData.author
            binding.publisherTv.text = bookData.publisher
            binding.stockTv.text = bookData.stock.toString()
            binding.descriptionTv.text = bookData.bookDesc

            // Use Glide to load the book cover asynchronously
            Glide.with(this)
                .load(Constants.BASE_URL + "/" + bookData.bookCover)
                .error(R.drawable.ic_error_gray) // Placeholder for error case
                .into(binding.coverBookIv)

        } else {
            // Handle the case where no book data is available
            Log.e("BookDetailActivity", "No book data available.")
            finish() // You might want to finish the activity or handle this case differently
        }

    }
}