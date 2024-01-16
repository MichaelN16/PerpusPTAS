package com.perkantas.perpusptas_new.Activity

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import com.perkantas.perpusptas_new.Auth.SessionManager
import com.perkantas.perpusptas_new.Model.MyProfileResponse
import com.perkantas.perpusptas_new.Model.MyProfileRequest
import com.perkantas.perpusptas_new.R
import com.perkantas.perpusptas_new.Retrofit.ApiClient
import com.perkantas.perpusptas_new.databinding.ActivityProfileEditBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class ProfileEditActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {
    private lateinit var binding: ActivityProfileEditBinding
    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient

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
            validateData()
        }

        binding.birthDatePickerBtn.setOnClickListener {
            showDatePickerDialog()
        }

        // Retrieve data from Intent
        val intent = intent
        val name = intent.getStringExtra("NAME") ?: ""
        val address = intent.getStringExtra("ADDRESS") ?: ""
        val birthPlace = intent.getStringExtra("BIRTH_PLACE") ?: ""
        val component = intent.getStringExtra("COMPONENT") ?: ""

        // Populate the UI elements using View Binding
        binding.nameEt.setText(name)
        binding.addressEt.setText(address)
        binding.birthPlaceEt.setText(birthPlace)
        binding.componentAc.setText(component)
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                binding.birthEt.setText(selectedDate)
            }, year, month, day
        )
        datePickerDialog.show()
    }

    // Implement onDateSet method
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
    }

    private fun loadArrayComponent() {
        val komponen = resources.getStringArray(R.array.komponen)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, komponen)
        binding.componentAc.setAdapter(arrayAdapter)
    }

    private var name = ""
    private var address = ""
    private var birthPlace = ""
    private lateinit var birthDate : Date
    private var phoneNumber : Long = 0
    private var component = ""

    private fun validateData() {
        //Input, gather all user information data
        name = binding.nameEt.text.toString().trim()
        address = binding.addressEt.text.toString().trim()
        birthPlace = binding.birthPlaceEt.text.toString().trim()
        component = binding.componentAc.text.toString().trim()
        phoneNumber = binding.numberEt.text.toString().trim().toLong()
        val phoneText = binding.numberEt.text.toString().trim()

        ///Validate Data
        if (name.isEmpty()) {
            Toast.makeText(this, "Masukkan nama Anda", Toast.LENGTH_SHORT).show()
        } else if (address.isEmpty()) {
            Toast.makeText(this, "Masukkan alamat Anda", Toast.LENGTH_SHORT).show()
        } else if (birthPlace.isEmpty()) {
            Toast.makeText(this, "Masukkan tempat lahir Anda", Toast.LENGTH_SHORT).show()
        } else if (component.isEmpty()) {
            Toast.makeText(this, "Pilih komponen", Toast.LENGTH_SHORT).show()
        } else if (phoneText.isEmpty()) {
            Toast.makeText(this, "Masukkan nomor telepon", Toast.LENGTH_SHORT).show()
        } else {
            try {
                phoneNumber = phoneText.toLong()
                updateProfile()
            } catch (e: NumberFormatException) {
                Toast.makeText(this, "Format nomor telepon tidak valid", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateProfile() {
        // Retrieve the entered data
        name = binding.nameEt.text.toString().trim()
        address = binding.addressEt.text.toString().trim()
        birthPlace = binding.birthPlaceEt.text.toString().trim()
        component = binding.componentAc.text.toString().trim()

        // Birth date validator
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        try {
            birthDate = dateFormat.parse(binding.birthEt.text.toString().trim()) as Date

            // Convert the date to 'yyyy-MM-dd' format for Laravel
            val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val formattedDate = outputFormat.format(birthDate)

            // Phone number validator
            phoneNumber = binding.numberEt.text.toString().trim().toLong()

            // Create the MyProfileRequest object
            val updateRequest = MyProfileRequest(name, birthPlace, formattedDate, phoneNumber, address, component)

            // Retrieve the authentication token
            val token = "Bearer ${sessionManager.fetchAuthToken()}"
            Log.d("Response : ", sessionManager.fetchAuthToken()!!)

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