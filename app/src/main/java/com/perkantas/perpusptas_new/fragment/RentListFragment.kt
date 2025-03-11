package com.perkantas.perpusptas_new.fragment

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.perkantas.perpusptas_new.activity.HistoryDetailActivity
import com.perkantas.perpusptas_new.adapter.AdapterBook
import com.perkantas.perpusptas_new.adapter.AdapterHistory
import com.perkantas.perpusptas_new.auth.SessionManager
import com.perkantas.perpusptas_new.databinding.FragmentRentListBinding
import com.perkantas.perpusptas_new.model.RentHistoryResponse
import com.perkantas.perpusptas_new.retrofit.ApiClient
import com.perkantas.perpusptas_new.util.dateConverter
import com.perkantas.perpusptas_new.util.getStatus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate

class RentListFragment : Fragment() {
    private lateinit var binding: FragmentRentListBinding
    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient
    private lateinit var rvRent: RecyclerView
    private lateinit var adapterHistory: AdapterHistory

    companion object {
        fun newInstance(key: String, type: String): RentListFragment {
            val fragment = RentListFragment()
            val args = Bundle()
            args.putString("key", key)
            args.putString("type", type)
            fragment.arguments = args
            return fragment
        }
    }

    private var key = ""
    private var type = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        key = requireArguments().getString("key", "")
        type = requireArguments().getString("type", "")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRentListBinding.inflate(LayoutInflater.from(context), container, false)
        rvRent = binding.rentHistoryRv

        sessionManager = SessionManager(requireContext())
        apiClient = ApiClient()

        val progressBar = binding.progressBar
        progressBar.visibility = View.VISIBLE

        fetchRentList()

        binding.searchEt.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                try {
                    adapterHistory.filter.filter(s)
                } catch (e: Exception) {
                    Log.d("Response", "Search exception : ${e.message}")
                }
            }
            override fun afterTextChanged(p0: Editable?) {}
        })

        setSwipeRefresh()

        return binding.root
    }

    private fun setSwipeRefresh() {
        binding.swipeToRefresh.setOnRefreshListener {
            fetchRentList()
            binding.swipeToRefresh.isRefreshing = false
        }
    }

    override fun onResume() {
        fetchRentList()
        super.onResume()
    }

    private var rentList = ArrayList<RentHistoryResponse.RentData>()

    private fun fetchRentList() {
        rentList.clear()
        apiClient.getApiService(requireContext()).getRentList(key).enqueue(object : Callback<RentHistoryResponse>{
            override fun onResponse(call: Call<RentHistoryResponse>, response: Response<RentHistoryResponse>) {
                binding.progressBar.visibility = View.GONE
                val rentHistory = response.body()!!.data

                if (response.isSuccessful){
                    //rentList.addAll(rentHistory)
                    rentList = ArrayList(rentHistory)
                    showRentList()
                }
                else{

                }
            }

            override fun onFailure(call: Call<RentHistoryResponse>, t: Throwable) {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(requireContext(), "Gagal mengambil data karena ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showRentList() {

        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rvRent.layoutManager = layoutManager

        val mappedRentList = rentList.map { Pair(it, getStatus(it)) }

        // Check if the filtered rent list is empty
        if (mappedRentList.isEmpty()) {
            // Hide the RecyclerView
            rvRent.visibility = View.GONE
            binding.searchEt.visibility = View.GONE
            // Show a message indicating no items for this status
            binding.noItemTv.visibility = View.VISIBLE
        } else {
            // Show the RecyclerView if there are items
            rvRent.visibility = View.VISIBLE
            // Hide the message TextView
            binding.noItemTv.visibility = View.GONE

            // Extract the RentHistoryResponse.RentData objects from the pairs
            val rentDataList = mappedRentList.map { it.first }

            // Create an adapter and set it to the RecyclerView
            adapterHistory = AdapterHistory(ArrayList(rentDataList), object : AdapterHistory.OnAdapterListener {
                override fun onClick(data: RentHistoryResponse.RentData) {
                    val intent = Intent(requireContext(), HistoryDetailActivity::class.java)
                    intent.putExtra("dataHistory", data)
                    startActivity(intent)
                }
            }, ArrayList(rentDataList))
            rvRent.adapter = adapterHistory
        }
    }
}