package com.perkantas.perpusptas_new.Activity

import android.widget.Filter
import com.perkantas.perpusptas_new.Adapter.AdapterBook
import com.perkantas.perpusptas_new.Model.BookResponse

class FilterBook : Filter {
    //arraylist in which we want to search
    var filterList: ArrayList<BookResponse.DataBook>
    //adapter in which filter need to be implemented
    var adapterBook: AdapterBook

    //constructor
    constructor(filterList: ArrayList<BookResponse.DataBook>, adapterBook: AdapterBook) {
        this.filterList = filterList
        this.adapterBook = adapterBook
    }

    override fun performFiltering(constraint: CharSequence?): Filter.FilterResults {
        var constraint:CharSequence? = constraint //value to search
        val results = Filter.FilterResults()
        //value to be searched should be not null or not empty
        if (constraint != null && constraint.isNotEmpty()){
            //change to upper case or lower case to avoid sensitive case
            constraint = constraint.toString().lowercase()
            val filteredModels = ArrayList<BookResponse.DataBook>()
            for (i in filterList.indices){
                //validate if match
                if (filterList[i].bookTitle.lowercase().contains(constraint)){
                    //searched value is similiar to value in list, add to filtered list
                    filteredModels.add(filterList[i])
                }
            }
            results.count = filteredModels.size
            results.values = filteredModels
        }
        else {
            //searched value is either null or empty, return all data
            results.count = filterList.size
            results.values = filterList
        }
        return results
    }

    override fun publishResults(constraint: CharSequence, results: Filter.FilterResults) {
        //apply filter change
        adapterBook.data = results.values as ArrayList<BookResponse.DataBook>

        //notify change
        adapterBook.notifyDataSetChanged()

    }
}