package com.perkantas.perpusptas_new.Activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.perkantas.perpusptas_new.Fragment.AccountFragment
import com.perkantas.perpusptas_new.Fragment.NotificationFragment
import com.perkantas.perpusptas_new.Fragment.LibraryFragment
import com.perkantas.perpusptas_new.Fragment.HistoryFragment
import com.perkantas.perpusptas_new.R
import com.perkantas.perpusptas_new.SessionManager
import com.perkantas.perpusptas_new.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var sessionManager: SessionManager

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

        sessionManager = SessionManager(this)
        //fungsi untuk melihat user login/tidak, dan untuk menampilkan nama
        checkUser()

        //mengaktifkan fungsi bottom nav
        setUpBottomNav()

        //in first launch, set toolbar fragment title to perpustakaan
        binding.fragmentsTv.text = "Perpustakaan"

        //logout button function
        binding.logoutBtn.setOnClickListener {
            sessionManager.deleteAuthToken()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun checkUser() {
        if(sessionManager.isLoggedIn()){
            val data = sessionManager.fetchDataLog()
            binding.usernameTv.text = data.name

            binding.logoutBtn.visibility = View.VISIBLE
        } else{
            binding.usernameTv.text = "Belum masuk akun"
            binding.logoutBtn.visibility = View.GONE
        }
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
                    callFragment(0, fragmentLibrary,"Perpustakaan")
                }
                R.id.navigation_history->{
                    callFragment(1, fragmentHistory, "Riwayat")
                }
                R.id.navigation_notifications->{
                    callFragment(2, fragmentNotification, "Notifikasi")
                }
                R.id.navigation_account->{
                    callFragment(3, fragmentAccount, "Akun")
                }
            }
            false
        }
    }

    private fun callFragment(index: Int, fragment: Fragment, title: String){
        menuItem = menu.getItem(index)
        menuItem.isChecked = true
        fm.beginTransaction().hide(active).show(fragment).commit()
        active = fragment

        //set fragment title to selected fragment
        binding.fragmentsTv.text = title
    }
}