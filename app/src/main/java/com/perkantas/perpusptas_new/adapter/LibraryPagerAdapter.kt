package com.perkantas.perpusptas_new.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.perkantas.perpusptas_new.fragment.BookFragment
import com.perkantas.perpusptas_new.model.CategoryResponse

class LibraryPagerAdapter(fragmentActivity: FragmentActivity, initialCategoryList: ArrayList<CategoryResponse.DataCat>) : FragmentStateAdapter(fragmentActivity) {
    //holds list of fragment i.e. new instances of same fragment for each category
    private val categoryList: ArrayList<CategoryResponse.DataCat> = initialCategoryList

    override fun getItemCount(): Int { //arraylist count
        // Return the number of categories you want to display
        return categoryList.size  // Adjust this based on your actual number of categories
    }

    override fun createFragment(position: Int): Fragment {
        val currentCategory = categoryList[position]
        // Create and return a new instance of BookFragment for each category
        return if (currentCategory.category == "Semua"){
            BookFragment.newInstance(0,"Semua")
        } else{
            BookFragment.newInstance(currentCategory.categoryId, currentCategory.category)
        }
    }
}
