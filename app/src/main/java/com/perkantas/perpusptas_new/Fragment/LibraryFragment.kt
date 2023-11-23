package com.perkantas.perpusptas_new.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.perkantas.perpusptas_new.Adapter.AdapterBook
import com.perkantas.perpusptas_new.Model.ModelBook
import com.perkantas.perpusptas_new.R
import com.perkantas.perpusptas_new.databinding.FragmentLibraryBinding

class LibraryFragment : Fragment(){
    private lateinit var binding: FragmentLibraryBinding
    private lateinit var adapter: AdapterBook
    private lateinit var rvBook: RecyclerView
    private lateinit var arrBook: ArrayList<ModelBook>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLibraryBinding.inflate(inflater, container, false)
        val view = binding.root
        rvBook = binding.booksRv

        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rvBook.layoutManager = layoutManager

        arrBook = createBookList()

        adapter = AdapterBook(arrBook)
        rvBook.adapter = adapter

        return view
    }

    private fun createBookList(): ArrayList<ModelBook> {
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
        }
}