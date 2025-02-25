package com.perkantas.perpusptas_new.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.perkantas.perpusptas_new.auth.SessionManager
import com.perkantas.perpusptas_new.Constants
import com.perkantas.perpusptas_new.model.BookResponse
import com.perkantas.perpusptas_new.model.ProfileCheckResponse
import com.perkantas.perpusptas_new.R
import com.perkantas.perpusptas_new.retrofit.ApiClient
import com.perkantas.perpusptas_new.databinding.ActivityBookDetailBinding
import com.perkantas.perpusptas_new.model.RentRequest
import com.perkantas.perpusptas_new.model.RentResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBookDetailBinding
    private lateinit var apiClient: ApiClient
    private lateinit var sessionManager: SessionManager
    private lateinit var bookData: BookResponse.DataBook

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        apiClient = ApiClient()
        sessionManager = SessionManager(this)

        // Check if the user is logged in
        if (sessionManager.isLoggedIn()) {
            // Fetch the user profile and book data
            userProfileCheck()
        } else {
            // Redirect to the login screen
            startActivity(Intent(this, LoginActivity::class.java))
            finish() // Close this activity to prevent returning to it after login
        }

        //handle back button
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

        // Retrieve the data from the intent
        bookData = intent.getSerializableExtra("dataBook") as BookResponse.DataBook

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
    }

    private fun userProfileCheck() {
        apiClient.getApiService(this).checkUserProfile()
            .enqueue(object : Callback<ProfileCheckResponse> {
                override fun onResponse(call: Call<ProfileCheckResponse>, response: Response<ProfileCheckResponse>) {
                    val results = response.body()
                    if (results?.status == true) {
                        binding.rentBookBtn.setOnClickListener {
                            if(bookData.stock > 0){
                                val bookId = bookData.id
                                showRentDetailDialog(bookId)
                            } else {
                                Toast.makeText(this@BookDetailActivity, "Buku tidak dapat dipinjam karena stok habis!", Toast.LENGTH_SHORT).show()
                            }

                        }
                    } else {
                        binding.rentBookBtn.setOnClickListener {
                            openAlertDialog()
                        }
                    }
                }
                override fun onFailure(call: Call<ProfileCheckResponse>, t: Throwable) {
                    Toast.makeText(this@BookDetailActivity, "Gagal mengambil data karena ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
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

    private fun showRentDetailDialog(bookId: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Konfirmasi Pengajuan Pinjam Buku")
        builder.setMessage("Apakah Anda yakin ingin meminjam buku ini? Pastikan Anda sudah berada di perpustakaan untuk mengambil buku!")
        builder.setPositiveButton("Ya") { dialog, which ->
            sendRentRequest(bookId)
        }
        builder.setNegativeButton("Batalkan") { dialog, which ->
            dialog.dismiss()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun sendRentRequest(bookId: Int) {
        val rentRequest = RentRequest(bookId)
        apiClient.getApiService(this).rentRequest(rentRequest).enqueue(object : Callback<RentResponse>{
            override fun onResponse(call: Call<RentResponse>, response: Response<RentResponse>) {
                if (response.isSuccessful){

                    rentSuccessDialog()
                } else {
                    Toast.makeText(this@BookDetailActivity, "Gagal mengajukan peminjaman karena ${response.body().toString()}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<RentResponse>, t: Throwable) {
                Toast.makeText(this@BookDetailActivity, "Proses pengajuan peminjaman gagal karena ${t.message}", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun rentSuccessDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.rent_success_dialog, null)
        val builder = AlertDialog.Builder(this)
        builder.setView(dialogView)

        val btnConfirm = dialogView.findViewById<Button>(R.id.confirmBtn)

        val dialog: AlertDialog = builder.create()
        dialog.show()

        btnConfirm.setOnClickListener {
            dialog.dismiss()
        }
    }
}