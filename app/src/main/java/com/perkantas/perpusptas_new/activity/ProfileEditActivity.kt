package com.perkantas.perpusptas_new.activity

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import com.perkantas.perpusptas_new.auth.SessionManager
import com.perkantas.perpusptas_new.model.MyProfileResponse
import com.perkantas.perpusptas_new.model.MyProfileRequest
import com.perkantas.perpusptas_new.R
import com.perkantas.perpusptas_new.retrofit.ApiClient
import com.perkantas.perpusptas_new.databinding.ActivityProfileEditBinding
import com.perkantas.perpusptas_new.util.DatePickerFragment
import com.perkantas.perpusptas_new.util.dateFormatConverter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class ProfileEditActivity : AppCompatActivity(), DatePickerFragment.OnDateSelectedListener {
    private lateinit var binding: ActivityProfileEditBinding
    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient
    private lateinit var arrayAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)
        apiClient = ApiClient()

        loadArrayComponent()

        //handle click, go back
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

        //handle update profile
        binding.updateBtn.setOnClickListener {
            updateProfile()
        }

        binding.birthDatePickerBtn.setOnClickListener {
            showDatePickerDialog()
        }

        val userData = sessionManager.fetchUserData()!!
        binding.nameEt.setText(userData.name)
        binding.addressEt.setText(userData.address)
        binding.birthPlaceEt.setText(userData.birthPlace)
        binding.birthEt.setText(dateFormatConverter(userData.birthDate,"dd/MM/yyyy"))
        binding.numberEt.setText(userData.phone)
    }

    private fun showDatePickerDialog() {
        val datePickerFragment = DatePickerFragment(this)
        datePickerFragment.show(supportFragmentManager, "datePicker")
    }

    override fun onDateSelected(year: Int, month: Int, dayOfMonth: Int) {
        val selectedDate = "$dayOfMonth/${month + 1}/$year"
        binding.birthEt.setText(selectedDate)
    }

    /*// Implement onDateSet method
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
    }*/

    private fun loadArrayComponent() {
        val komponen = resources.getStringArray(R.array.komponen)
        arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, komponen)
        binding.componentAc.setAdapter(arrayAdapter)
    }

    private var name = ""
    private var address = ""
    private var birthPlace = ""
    private lateinit var birthDate : Date
    private var phoneNumber = ""
    private var component = ""

    private fun updateProfile() {
        // Retrieve the entered data
        name = binding.nameEt.text.toString().trim()
        address = binding.addressEt.text.toString().trim()
        birthPlace = binding.birthPlaceEt.text.toString().trim()
        phoneNumber = binding.numberEt.text.toString().trim()
        component = binding.componentAc.text.toString().trim()

        // Birth date validator
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        try {
            birthDate = dateFormat.parse(binding.birthEt.text.toString().trim()) as Date

            // Convert the date to 'yyyy-MM-dd' format for Laravel
            val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val formattedDate = outputFormat.format(birthDate).toString()

            // Create the MyProfileRequest object
            val updateRequest = MyProfileRequest(name, birthPlace, formattedDate, phoneNumber, address, component)

            // Retrieve the authentication token
            val token = "Bearer ${sessionManager.fetchAuthToken()}"

            // Check if the token is not null before making the API call
            apiClient.getApiService(this).updateUserProfile(token, updateRequest)
                .enqueue(object : Callback<MyProfileResponse> {
                    override fun onFailure(call: Call<MyProfileResponse>, t: Throwable) {
                        Toast.makeText(this@ProfileEditActivity, "Gagal memperbarui profil karena ${t.message}", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<MyProfileResponse>, response: Response<MyProfileResponse>) {
                        val updateResponse = response.body()

                        if (updateResponse?.success == true) {
                            Toast.makeText(this@ProfileEditActivity, "Berhasil memperbarui profil!", Toast.LENGTH_LONG).show()
                            finish()
                        } else {
                            Toast.makeText(this@ProfileEditActivity, "Gagal memperbarui profil ", Toast.LENGTH_SHORT).show()
                        }
                    }
                })
        } catch (e: ParseException) {
            // Handle the parsing exception, e.g., show an error message
            Toast.makeText(this@ProfileEditActivity, "Invalid date format", Toast.LENGTH_SHORT).show()
        } catch (e: NumberFormatException) {
            // Handle the conversion exception, e.g., show an error message
            Toast.makeText(this@ProfileEditActivity, "Invalid phone number", Toast.LENGTH_SHORT).show()
        }
    }
}