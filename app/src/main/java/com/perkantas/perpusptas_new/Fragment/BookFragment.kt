package com.perkantas.perpusptas_new.Fragment

import android.app.ProgressDialog
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
import com.perkantas.perpusptas_new.Activity.BookDetailActivity
import com.perkantas.perpusptas_new.Adapter.AdapterBook
import com.perkantas.perpusptas_new.Auth.SessionManager
import com.perkantas.perpusptas_new.Model.BookResponse
import com.perkantas.perpusptas_new.Retrofit.ApiClient
import com.perkantas.perpusptas_new.databinding.FragmentBookBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookFragment : Fragment() {
    private lateinit var binding: FragmentBookBinding
    private lateinit var adapterBook: AdapterBook
    private lateinit var rvBook: RecyclerView
    private lateinit var listBook: ArrayList<BookResponse.DataBook>
    private lateinit var progressDialog:ProgressDialog
    private lateinit var apiClient:ApiClient
    private lateinit var sessionManager:SessionManager

    companion object {
        fun newInstance(categoryId: String, category: String): BookFragment {
            val fragment = BookFragment()
            val args = Bundle()
            args.putString("categoryId", categoryId)
            args.putString("category", category)
            fragment.arguments = args
            return fragment
        }
    }

    private var categoryId = ""
    private var category = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = arguments
        if (args != null) {
            categoryId = args.getString("categoryId")!!
            category = args.getString("category")!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBookBinding.inflate(LayoutInflater.from(context), container, false)
        rvBook = binding.booksRv

        //init API
        apiClient = ApiClient()

        //init session manager
        sessionManager = SessionManager(requireContext())

        //progress dialog
        progressDialog = ProgressDialog(requireContext())
        progressDialog.setTitle("Memuat data")
        progressDialog.setCanceledOnTouchOutside(false)

        // Initialize other UI components or variables if needed
        loadBooks()

        // Search functionality
        binding.searchEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Not needed
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                try {
                    adapterBook.filter.filter(s)
                } catch (e: Exception) {
                    Log.d("Response", "Search exception : ${e.message}")
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // Not needed
            }
        })

        return binding.root
    }

    private fun showBooks() {
        // Initialize RecyclerView and Adapter
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rvBook.layoutManager = layoutManager

        listBook = ArrayList()  // Initialize your list of books here

        adapterBook = AdapterBook(listBook, listBook, object : AdapterBook.OnAdapterListener {
            override fun onClick(data: ArrayList<BookResponse.DataBook>) {
                startActivity(Intent(requireContext(), BookDetailActivity::class.java))
            }
        })

        rvBook.adapter = adapterBook
    }

    private fun loadBooks() {
        progressDialog.show()

        if (sessionManager.isLoggedIn()) {
            //if user logged in
            getBookList()
        } else {
            //if not logged in
            getIndexList()
        }
    }

    private fun getBookList() {
        apiClient.getApiService(requireContext()).getBooks().enqueue(object : Callback<BookResponse> {

            override fun onResponse(call: Call<BookResponse>, response: Response<BookResponse>) {
                progressDialog.dismiss()
                val bookResponse = response.body()!!

                if (response.isSuccessful) {
                    listBook = ArrayList(bookResponse.dataBook)
                    showBooks()
                } else {
                    // Handle unsuccessful response
                    Log.e("BookFragment", "BookResponse is null.")
                }
            }

            override fun onFailure(call: Call<BookResponse>, t: Throwable) {
                Toast.makeText(requireContext(),"Gagal mengambil data buku karena ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getIndexList() {
        apiClient.getApiService(requireContext()).getBookIndex().enqueue(object : Callback<BookResponse> {
            // ... other API call handling

            override fun onResponse(call: Call<BookResponse>, response: Response<BookResponse>) {
                progressDialog.dismiss()
                val bookResponse = response.body()!!

                if (response.isSuccessful) {
                    listBook = ArrayList(bookResponse.dataBook)
                    showBooks()
                } else {
                    Log.e("BookFragment", "BookResponse is null.")
                }
            }

            override fun onFailure(call: Call<BookResponse>, t: Throwable) {
                progressDialog.dismiss()
                Toast.makeText(requireContext(),"Gagal mengambil data buku karena ${t.message}", Toast.LENGTH_SHORT).show()
            }

        })
    }

    // Implement methods to load books based on category
    private fun loadAllBook() {
        // Implement loading all books logic
    }

    private fun loadCategorizedBook() {
        // Implement loading categorized books logic
    }
}
