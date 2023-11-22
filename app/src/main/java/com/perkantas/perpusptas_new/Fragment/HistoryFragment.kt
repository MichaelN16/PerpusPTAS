package com.perkantas.perpusptas_new.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.perkantas.perpusptas_new.R
import com.perkantas.perpusptas_new.SessionManager
import com.perkantas.perpusptas_new.databinding.FragmentHistoryBinding
import com.perkantas.perpusptas_new.databinding.FragmentLibraryBinding

class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHistoryBinding.inflate(layoutInflater)

        return binding.root
    }

}