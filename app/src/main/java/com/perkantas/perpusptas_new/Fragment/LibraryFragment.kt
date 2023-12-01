package com.perkantas.perpusptas_new.Fragment

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.perkantas.perpusptas_new.Activity.BookDetailActivity
import com.perkantas.perpusptas_new.Activity.ForgotPasswordActivity
import com.perkantas.perpusptas_new.Adapter.AdapterBook
import com.perkantas.perpusptas_new.Model.BookResponse
import com.perkantas.perpusptas_new.Retrofit.ApiClient
import com.perkantas.perpusptas_new.databinding.FragmentLibraryBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LibraryFragment : Fragment(){
    private lateinit var binding: FragmentLibraryBinding
    private lateinit var adapter: AdapterBook
    private lateinit var rvBook: RecyclerView
    private lateinit var apiClient: ApiClient
    private lateinit var progressDialog: ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLibraryBinding.inflate(inflater, container, false)
        val view = binding.root
        apiClient = ApiClient()
        rvBook = binding.booksRv

        progressDialog = ProgressDialog(requireContext())
        progressDialog.setTitle("Memuat data")
        progressDialog.setCanceledOnTouchOutside(false)

        getBookList()

        return view
    }

    private var listBook:ArrayList<BookResponse.DataBook> = ArrayList()

    private fun getBookList(){
        progressDialog.show()
        apiClient.getApiService(requireContext()).getBooks().enqueue(object : Callback<BookResponse>{
            override fun onResponse(call: Call<BookResponse>, response: Response<BookResponse>) {
                progressDialog.dismiss()
                val bookResponse = response.body()!!

                if (response.isSuccessful) {
                    listBook = ArrayList(bookResponse.dataBook)
                    showBooks()
                } else {
                    // Handle unsuccessful response
                    Log.e("LibraryFragment", "BookResponse is null.")
                }
            }

            override fun onFailure(call: Call<BookResponse>, t: Throwable) {
                progressDialog.dismiss()
                Log.d("Library Response", "${t.message}")
            }
        })
    }

    private fun showBooks() {
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rvBook.layoutManager = layoutManager

        Log.d("LibraryFragment", "Number of books: ${listBook.size}")

        adapter = AdapterBook(listBook, object : AdapterBook.OnAdapterListener{
            override fun onClick(data: ArrayList<BookResponse.DataBook>) {
                startActivity(Intent(requireContext(), BookDetailActivity::class.java))
            }

        })
        rvBook.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    /*private fun createBookList(): ArrayList<ModelBook> {
            val arr = ArrayList<ModelBook>()
            val p1 = ModelBook()
            p1.bookTitle = "SERI PERJALANAN IMAN (PENGKHOTBAH)"
            p1.bookCover = R.drawable.pengkhotbah_cover
            p1.category = "TAFSIRAN/EKSPOSISI"
            p1.bookDesc = "Pengkhotbah adalah salah satu kitab hikmat dalam Perjanjian Lama. Namun, berbeda dengan keempat kitab hikmat lainnya, Pengkhotbah sepertinya lebih banyak membuat kita bertanya-tanya daripada memberikan solusi, dengan lebih banyak kontradiksi daripada penegasan!"
            p1.bookCode = "1006"
            p1.stock = 1

            val p2 = ModelBook()
            p2.bookTitle = "GAYA HIDUP KRISTEN"
            p2.bookCover = R.drawable.ayub_cover
            p2.category = "BAHAN PA"
            p2.bookDesc = "Mengapa hal-hal buruk terjadi pada orang baik? Mengapa ada begitu banyak penderitaan yang seakan tak terjelaskan? Hingga kini, banyak dari kita masih mengajukan pertanyaan-pertanyaan yang mendera Ayub itu. Ayub adalah seorang saleh yang menderita kehilangan besar, mengalami sengsara, dan merasa ditinggalkan—gambaran dari segala sesuatu yang dialami Tuhan Yesus kelak. Telusurilah pertanyaan-pertanyaan sulit tersebut bersama Kitab Ayub, dan temukan bagaimana Anda dapat kembali berharap dan mempercayai Allah Mahakuasa yang menopang kita dalam tangan kasih-Nya."
            p2.bookCode = "2107"
            p2.stock = 2

            val p3 = ModelBook()
            p3.bookTitle = "WHY I BELIEVE"
            p3.bookCover = R.drawable.why_believe
            p3.category = "DOKTRIN"
            p3.bookDesc = "In this powerful declaration of what Christians believe and why, Kennedy explores the foundations of the Christian faith. For new believers and seasoned Christians alike, this book will strengthen their faith by answering that all consuming question, Why?"
            p3.bookCode = "4012"
            p3.stock = 1

            arr.add(p1)
            arr.add(p2)
            arr.add(p3)

            return arr
        }*/
}