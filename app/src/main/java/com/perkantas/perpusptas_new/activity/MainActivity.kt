package com.perkantas.perpusptas_new.activity

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.perkantas.perpusptas_new.R
import com.perkantas.perpusptas_new.auth.SessionManager
import com.perkantas.perpusptas_new.databinding.ActivityMainBinding
import com.perkantas.perpusptas_new.fragment.AccountFragment
import com.perkantas.perpusptas_new.fragment.HistoryFragment
import com.perkantas.perpusptas_new.fragment.LibraryFragment
import com.perkantas.perpusptas_new.fragment.NotificationFragment

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
            openDialogBox()
        }

        //back button
        binding.backBtn.setOnClickListener {
            startActivity(Intent(this, LandingActivity::class.java))
            finish()
        }

        binding.loginBtn.setOnClickListener {
            startActivity(Intent(this, VerificationActivity::class.java))
        }
    }

    private fun openDialogBox() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder
            .setTitle("Keluar dari akun")
            .setMessage("Anda yakin ingin keluar akun?")
            .setPositiveButton("Ya") { dialog, which ->
                sessionManager.deleteAuthToken()
                sessionManager.clearUserData()
                Log.d("Response", "Auth token deleted")
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
            .setNegativeButton("Batalkan") { dialog, which ->
                dialog.dismiss()
            }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun checkUser() {
        if(sessionManager.isLoggedIn()){
            val data = sessionManager.fetchDataLog()
            //set text to user name
            binding.usernameTv.text = data.name

            //show logout button, hide login btn
            binding.logoutBtn.visibility = View.VISIBLE
            binding.loginBtn.visibility = View.GONE
            binding.backBtn.visibility = View.GONE
        } else{
            //set text to "not logged in"
            binding.usernameTv.text = "Belum masuk akun"

            //hide logout btn, show login btn
            binding.logoutBtn.visibility = View.GONE
            binding.loginBtn.visibility = View.VISIBLE
            binding.backBtn.visibility = View.VISIBLE
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
        if (sessionManager.isLoggedIn() || index == 0) {
            // Allow access to the fragment if the user is logged in or if it's the LibraryFragment (index 0)
            menuItem = menu.getItem(index)
            menuItem.isChecked = true
            fm.beginTransaction().hide(active).show(fragment).commit()
            active = fragment

            // Set fragment title to selected fragment
            binding.fragmentsTv.text = title
        } else {
            // Handle the case where the user is not logged in and attempting to access other fragments
            showToast("Silahkan masuk akun untuk menggunakan fitur ini")
            // Redirect to the login screen
            startActivity(Intent(this, VerificationActivity::class.java))
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}