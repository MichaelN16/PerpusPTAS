package com.perkantas.perpusptas_new.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.perkantas.perpusptas_new.Activity.FilterBook
import com.perkantas.perpusptas_new.Activity.VerificationActivity
import com.perkantas.perpusptas_new.Auth.SessionManager
import com.perkantas.perpusptas_new.Constants
import com.perkantas.perpusptas_new.Model.BookResponse
import com.perkantas.perpusptas_new.R
import com.perkantas.perpusptas_new.databinding.RowBookListBinding

class AdapterBook(var data: ArrayList<BookResponse.DataBook>, private var filterList: ArrayList<BookResponse.DataBook>, val listener: OnAdapterListener): RecyclerView.Adapter<AdapterBook.Holder>(), Filterable {

    private lateinit var context: Context
    private lateinit var sessionManager: SessionManager
    private var filter: FilterBook? = null

    class Holder(val binding: RowBookListBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = RowBookListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        sessionManager = SessionManager(context)

        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val currentBook = data[position]
        holder.binding.titleTv.text = currentBook.bookTitle
        holder.binding.descriptionTv.text = currentBook.bookDesc
        holder.binding.categoryTv.text = currentBook.category
        holder.binding.codeBookTv.text = currentBook.bookCode
        holder.binding.stockCountTv.text = currentBook.stock.toString()

        // Assuming bookCover is a URL, not an image resource
        // Use Glide to load the book cover asynchronously
        Glide.with(holder.itemView.context)
            .load(Constants.BASE_URL + "/" + currentBook.bookCover)
            .centerCrop()
            .error(R.drawable.ic_error_gray) // Placeholder for error case
            .into(holder.binding.coverBook)

        //click item in recycler, move to detail book activity
        holder.itemView.setOnClickListener {
            if (sessionManager.isLoggedIn()){
                listener.onClick(data)
            } else{
                //go to verification class
                val intent = Intent(context, VerificationActivity::class.java)
                context.startActivity(intent)
            }
        }
        // Set the visibility of the ProgressBar based on the loading status
        if (currentBook.isLoading) {
            holder.binding.progressBar.visibility = View.VISIBLE
        } else {
            holder.binding.progressBar.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getFilter(): Filter {
        if (filter == null){
            filter = FilterBook(filterList, this)
        }
        return filter as FilterBook
    }

    interface OnAdapterListener{
        fun onClick(data: ArrayList<BookResponse.DataBook>)
    }
}

