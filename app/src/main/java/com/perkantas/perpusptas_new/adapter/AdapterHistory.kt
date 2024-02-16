package com.perkantas.perpusptas_new.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.perkantas.perpusptas_new.Constants
import com.perkantas.perpusptas_new.R
import com.perkantas.perpusptas_new.activity.FilterRent
import com.perkantas.perpusptas_new.databinding.ItemBookHistoryBinding
import com.perkantas.perpusptas_new.model.RentHistoryResponse
import com.perkantas.perpusptas_new.util.dateConverter
import com.perkantas.perpusptas_new.util.dateFormatConverter
import java.time.LocalDate

class AdapterHistory(var data : ArrayList<RentHistoryResponse.RentData>, private var filterList: ArrayList<RentHistoryResponse.RentData> = data): RecyclerView.Adapter<AdapterHistory.Holder>(), Filterable {

    private var filter: FilterRent? = null

    class Holder (val binding: ItemBookHistoryBinding ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemBookHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val rentedBook = data[position]
        val dateRent = dateConverter(rentedBook.date_rent, "yyyy-MM-dd", "dd/MM/yyyy")
        val dateDue = dateConverter(rentedBook.date_due, "yyyy-MM-dd", "dd/MM/yyyy")
        val dateReturn = dateConverter(rentedBook.date_return, "yyyy-MM-dd", "dd/MM/yyyy")
        holder.binding.titleTv.text = rentedBook.book.book_title
        holder.binding.dateRentTv.text = dateRent
        holder.binding.dateDueTv.text = dateDue
        holder.binding.dateReturnTv.text = dateReturn
        holder.binding.codeBookTv.text = rentedBook.book.book_code
        holder.binding.categoryTv.text = rentedBook.book.category//to string

        //glide for coverbook
        Glide.with(holder.itemView.context)
            .load(Constants.BASE_URL + "/" + rentedBook.book.book_cover)
            .centerCrop()
            .error(R.drawable.ic_error_gray)
            .into(holder.binding.coverBook)

        // Mapping status based on conditions
        val status = getStatus(rentedBook)
        holder.binding.statusTv.text = status
        
        if(rentedBook.book.isLoading){
            holder.binding.progressBar.visibility = View.VISIBLE
        } else {
            holder.binding.progressBar.visibility = View.GONE
        }

        /*//on click to show rent detail
        holder.itemView.setOnClickListener{
            listener.onClick(rentedBook)
        }*/
    }

    private fun getStatus(rentedBook: RentHistoryResponse.RentData): CharSequence? {
        val currentDate = LocalDate.now().toString()
        val formattedDate = dateConverter(currentDate, "yyyy-MM-dd", "dd/MM/yyyy")
        return when {
            rentedBook.date_due.isNullOrEmpty() -> "Menunggu"
            rentedBook.date_return.isNullOrEmpty() -> "Meminjam"
            formattedDate >= rentedBook.date_due -> "Terlambat"
            else -> "Selesai"
        }
    }

    override fun getFilter(): Filter {
        if (filter == null){
            filter = FilterRent(data, this)
        }
        return filter as FilterRent
    }

    override fun getItemCount(): Int {
        return data.size
    }
}