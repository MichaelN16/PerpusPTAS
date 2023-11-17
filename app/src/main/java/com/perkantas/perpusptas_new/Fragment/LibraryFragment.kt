package com.perkantas.perpusptas_new.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.perkantas.perpusptas_new.LandingActivity
import com.perkantas.perpusptas_new.R
import com.perkantas.perpusptas_new.SessionManager
import com.perkantas.perpusptas_new.databinding.FragmentLibraryBinding

class LibraryFragment : Fragment(){
    private lateinit var binding: FragmentLibraryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLibraryBinding.inflate(layoutInflater)

        return binding.root
    }
}