package com.perkantas.perpusptas_new.Activity

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.perkantas.perpusptas_new.Auth.SessionManager
import com.perkantas.perpusptas_new.Constants
import com.perkantas.perpusptas_new.Model.BookResponse
import com.perkantas.perpusptas_new.Model.ProfileCheckResponse
import com.perkantas.perpusptas_new.R
import com.perkantas.perpusptas_new.Retrofit.ApiClient
import com.perkantas.perpusptas_new.databinding.ActivityBookDetailBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBookDetailBinding
    private lateinit var apiClient: ApiClient
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        apiClient = ApiClient()
        sessionManager = SessionManager(this)
        userProfileCheck()

        /*//handle click rent
        binding.rentBookBtn.setOnClickListener {
            openAlertDialog()
        }*/

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

            // Set the visibility of the ProgressBar based on the loading status
            if (bookData.isLoading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }

        } else {
            // Handle the case where no book data is available
            Log.e("BookDetailActivity", "No book data available.")
            finish() // You might want to finish the activity or handle this case differently
        }
    }

    private fun openAlertDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder
            .setTitle("Profil Belum Lengkap")
            .setMessage("Anda perlu melengkapi profil untuk meminjam buku!")
            .setPositiveButton("Lengkapi Profil") { dialog, which ->
                startActivity(Intent(this, ProfileEditActivity::class.java))
            }
            .setNegativeButton("Kembali") { dialog, which ->
                dialog.dismiss()
            }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun userProfileCheck() {
        //call api for user is already fill profile or not
        val authToken = "Bearer ${sessionManager.fetchAuthToken()}"
        apiClient.getApiService(this).checkUserProfile(token = authToken).enqueue(object : Callback<ProfileCheckResponse>{
            override fun onResponse(call: Call<ProfileCheckResponse>, response: Response<ProfileCheckResponse>) {
                val results = response.body()!!
                if(results.status){
                    binding.rentBookBtn.setOnClickListener {
                        Toast.makeText(this@BookDetailActivity, "Berhasil!", Toast.LENGTH_SHORT).show()
                        //startActivity(Intent(this@BookDetailActivity, RentActivity::class.java))
                    }
                } else {
                    binding.rentBookBtn.setBackgroundColor(Color.LTGRAY)
                    binding.rentBookBtn.setOnClickListener {
                        openAlertDialog()
                    }
                }
            }

            override fun onFailure(call: Call<ProfileCheckResponse>, t: Throwable) {
                Toast.makeText(this@BookDetailActivity, "Gagal mengambil data", Toast.LENGTH_SHORT).show()
            }

        })
    }
}