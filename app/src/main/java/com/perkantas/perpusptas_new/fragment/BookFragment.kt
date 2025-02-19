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
import com.perkantas.perpusptas_new.activity.BookDetailActivity
import com.perkantas.perpusptas_new.activity.VerificationActivity
import com.perkantas.perpusptas_new.adapter.AdapterBook
import com.perkantas.perpusptas_new.auth.SessionManager
import com.perkantas.perpusptas_new.model.BookResponse
import com.perkantas.perpusptas_new.retrofit.ApiClient
import com.perkantas.perpusptas_new.databinding.FragmentBookBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookFragment : Fragment() {
    private lateinit var binding:FragmentBookBinding
    private lateinit var adapterBook: AdapterBook
    private lateinit var rvBook: RecyclerView
    private lateinit var apiClient:ApiClient
    private lateinit var sessionManager:SessionManager

    companion object {
        fun newInstance(categoryId: Int, category: String): BookFragment {
            val fragment = BookFragment()
            val args = Bundle()
            args.putInt("categoryId", categoryId)
            args.putString("category", category)
            fragment.arguments = args
            return fragment
        }
    }

    private var categoryId = 0
    private var category = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        categoryId = requireArguments().getInt("categoryId", 0)
        category = requireArguments().getString("category", "")
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

        //progress bar
        val progressBar = binding.progressBar
        progressBar.visibility = View.VISIBLE // Show ProgressBar initially

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

    override fun onResume() {
        loadBooks()
        super.onResume()
    }

    private fun loadBooks() {
        if(category == "Semua"){
            //load all books
            loadAllBook()
        } else {
            //load selected category books
            loadCategorizedBook()
        }
    }

    private var listBook = ArrayList<BookResponse.DataBook>()

    private fun loadAllBook() {
        listBook.clear()
        // Implement loading all books logic
        apiClient.getApiService(requireContext()).getBookIndex().enqueue(object : Callback<BookResponse> {
            override fun onResponse(call: Call<BookResponse>, response: Response<BookResponse>) {
                binding.progressBar.visibility = View.GONE
                val bookResponse = response.body()!!

                if (response.isSuccessful) {
                    listBook = ArrayList(bookResponse.dataBook)
                    showBooks()
                } else {

                }
            }

            override fun onFailure(call: Call<BookResponse>, t: Throwable) {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(requireContext(),"Gagal mengambil data buku karena ${t.message}", Toast.LENGTH_SHORT).show()
            }

        })
    }

    // Implement methods to load books based on category
    private fun loadCategorizedBook() {
        listBook.clear()
        // Implement loading categorized books logic
        apiClient.getApiService(requireContext()).getBookIndex(category).enqueue(object : Callback<BookResponse> {
            override fun onResponse(call: Call<BookResponse>, response: Response<BookResponse>) {
                binding.progressBar.visibility = View.GONE
                val bookResponse = response.body()!!

                if (response.isSuccessful) {
                    listBook = ArrayList(bookResponse.dataBook)
                    showBooks()
                } else {

                }
            }

            override fun onFailure(call: Call<BookResponse>, t: Throwable) {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(requireContext(),"Gagal mengambil data buku karena ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showBooks(){
        // Initialize RecyclerView and Adapter
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rvBook.layoutManager = layoutManager

        val filteredBooks: List<BookResponse.DataBook> = if (category == "Semua") {

            listBook
        } else {
            listBook.filter { it.category == category }
        }

        if(filteredBooks.isEmpty()){
            binding.searchEt.visibility = View.GONE
            binding.booksRv.visibility = View.GONE
            binding.noItemTv.visibility = View.VISIBLE
        } else {
            adapterBook = AdapterBook(filteredBooks as ArrayList<BookResponse.DataBook>,
                filteredBooks, object : AdapterBook.OnAdapterListener {

                    // Implement onClick
                    override fun onClick(data: BookResponse.DataBook) {
                        if (sessionManager.isLoggedIn()) {
                            // Handle the click event here
                            val intent = Intent(requireContext(), BookDetailActivity::class.java)
                            intent.putExtra("dataBook", data)
                            startActivity(intent)
                        } else {
                            // Go to verification class
                            Toast.makeText(requireContext(), "Silahkan masuk untuk menggunakan fitur ini", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(requireContext(), VerificationActivity::class.java))
                        }
                    }
                })
            rvBook.adapter = adapterBook
        }
    }

}