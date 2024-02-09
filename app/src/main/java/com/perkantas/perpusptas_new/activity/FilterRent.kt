package com.perkantas.perpusptas_new.activity

import android.widget.Filter
import com.perkantas.perpusptas_new.adapter.AdapterHistory
import com.perkantas.perpusptas_new.model.RentHistoryResponse

class FilterRent(
    var filterList: ArrayList<RentHistoryResponse.RentData>,
    var adapterHistory: AdapterHistory
    ): Filter() {

    override fun performFiltering(constraint: CharSequence?): Filter.FilterResults {
        var constraint:CharSequence? = constraint
        val results = Filter.FilterResults()

        if(constraint != null && constraint.isNotEmpty()) {
            constraint = constraint.toString().lowercase()
            val filteredModels = ArrayList<RentHistoryResponse.RentData>()
            for (i in filterList.indices){
                if(filterList[i].book.book_title.lowercase().contains(constraint)){
                    filteredModels.add(filterList[i])
                }
            }
            results.count = filteredModels.size
            results.values
        }
        else{
            results.count = filterList.size
            results.values = filterList
        }
        return results
    }

    override fun publishResults(constraint: CharSequence?, results: Filter.FilterResults) {
        adapterHistory.data = results.values as ArrayList<RentHistoryResponse.RentData>
        adapterHistory.notifyDataSetChanged()
    }
}