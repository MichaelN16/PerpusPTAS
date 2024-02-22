package com.perkantas.perpusptas_new.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.perkantas.perpusptas_new.Constants
import com.perkantas.perpusptas_new.R
import com.perkantas.perpusptas_new.databinding.ActivityHistoryDetailBinding
import com.perkantas.perpusptas_new.model.RentHistoryResponse
import com.perkantas.perpusptas_new.util.dateConverter
import com.perkantas.perpusptas_new.util.getStatus

class HistoryDetailActivity : AppCompatActivity() {
    private lateinit var binding:ActivityHistoryDetailBinding
    private lateinit var historyData: RentHistoryResponse.RentData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener {
            onBackPressed()
            finish()
        }

        historyData = intent.getSerializableExtra("dataHistory") as RentHistoryResponse.RentData

        val dateRent = dateConverter(historyData.date_rent, "yyyy-MM-dd", "dd/MM/yyyy")
        val dateDue = dateConverter(historyData.date_due, "yyyy-MM-dd", "dd/MM/yyyy")
        val dateReturn = dateConverter(historyData.date_return, "yyyy-MM-dd", "dd/MM/yyyy")
        val status = getStatus(historyData)
        binding.rentTv.text = dateRent
        binding.dueTv.text = dateDue
        binding.returnTv.text = dateReturn
        binding.titleTv.text = historyData.book.book_title
        binding.statusTv.text = status
        binding.bookIndTv.text = historyData.book.book_code
        binding.categoryTv.text = historyData.book.category
        Glide.with(this)
            .load(Constants.BASE_URL + "/" + historyData.book.book_cover)
            .error(R.drawable.ic_error_gray)
            .into(binding.coverBook)

        if (historyData.book.isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}