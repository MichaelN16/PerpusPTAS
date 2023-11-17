package com.perkantas.perpusptas_new.Activity

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.perkantas.perpusptas_new.Fragment.AccountFragment
import com.perkantas.perpusptas_new.Fragment.NotificationFragment
import com.perkantas.perpusptas_new.Fragment.LibraryFragment
import com.perkantas.perpusptas_new.Fragment.HistoryFragment
import com.perkantas.perpusptas_new.R
import com.perkantas.perpusptas_new.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val fragmentLibrary: Fragment = LibraryFragment()
    private val fragmentHistory: Fragment = HistoryFragment()
    private val fragmentNotification: Fragment = NotificationFragment()
    private val fragmentAccount: Fragment = AccountFragment()
    val fm: FragmentManager = supportFragmentManager
    var active: Fragment = fragmentLibrary

    private lateinit var menu: Menu
    private lateinit var menuItem: MenuItem
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpBottomNav()
    }

    private fun setUpBottomNav() {
        fm.beginTransaction().add(R.id.container, fragmentLibrary).show(fragmentLibrary).commit()
        fm.beginTransaction().add(R.id.container, fragmentHistory).hide(fragmentHistory).commit()
        fm.beginTransaction().add(R.id.container, fragmentNotification).hide(fragmentNotification).commit()
        fm.beginTransaction().add(R.id.container, fragmentAccount).hide(fragmentAccount).commit()

        bottomNavigationView = findViewById(R.id.nav_view)
        menu = bottomNavigationView.menu
        menuItem = menu.getItem(0)
        menuItem.isChecked = true

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->

            when(item.itemId){
                R.id.navigation_library->{
                    Log.d("Response","Perpustakaan")
                    callFragment(0, fragmentLibrary)
                }
                R.id.navigation_history->{
                    Log.d("Response","Riwayat")
                    callFragment(1, fragmentHistory)
                }
                R.id.navigation_notifications->{
                    Log.d("Response","Notifikasi")
                    callFragment(2, fragmentNotification)
                }
                R.id.navigation_account->{
                    Log.d("Response","Akun")
                    callFragment(3, fragmentAccount)
                }
            }
            false
        }
    }

    private fun callFragment(int: Int, fragment: Fragment){
        menuItem = menu.getItem(int)
        menuItem.isChecked = true
        fm.beginTransaction().hide(active).show(fragment).commit()
        active = fragment
    }
}