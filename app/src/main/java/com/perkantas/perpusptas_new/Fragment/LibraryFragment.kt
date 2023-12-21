package com.perkantas.perpusptas_new.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.perkantas.perpusptas_new.Adapter.LibraryPagerAdapter
import com.perkantas.perpusptas_new.Auth.SessionManager
import com.perkantas.perpusptas_new.Model.CategoryResponse
import com.perkantas.perpusptas_new.Retrofit.ApiClient
import com.perkantas.perpusptas_new.databinding.FragmentLibraryBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LibraryFragment : Fragment() {
    private lateinit var binding: FragmentLibraryBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient

    private val categoryList: ArrayList<CategoryResponse.DataCat> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLibraryBinding.inflate(inflater, container, false)
        val view = binding.root

        sessionManager = SessionManager(requireContext())
        apiClient = ApiClient()

        viewPager = binding.viewPager
        tabLayout = binding.tabLayout

        loadCategories()

        return view
    }

    private fun loadCategories() {
        apiClient.getApiService(requireContext()).getCategories().enqueue(object : Callback<CategoryResponse>{
            override fun onResponse(call: Call<CategoryResponse>, response: Response<CategoryResponse>) {
                if (response.isSuccessful){
                    val loadedCategoryList = ArrayList(response.body()?.dataCat ?: emptyList())
                    setUpViewPager(loadedCategoryList)
                    }
                }


            override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {

            }
        })
    }

    private fun setUpViewPager(categoryList: ArrayList<CategoryResponse.DataCat>) {
        //set up viewpager and tab layout
        val adapter = LibraryPagerAdapter(requireActivity(), categoryList)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            // Customize tab labels based on your category data
            tab.text = categoryList[position].category
        }.attach()

    }
}
