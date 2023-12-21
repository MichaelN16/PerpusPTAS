package com.perkantas.perpusptas_new.Activity

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
        /*loadArrayGender()*/
        //handle click, go back
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

        //handle update profile
        binding.updateBtn.setOnClickListener {
            //updateProfile()
            finish()
        }

        binding.birthEt.setOnClickListener {
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
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }

    // Implement onDateSet method
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        // Display the selected date in the birthEt EditText
        val selectedDate = "$dayOfMonth/${month + 1}/$year"
        binding.birthEt.setText(selectedDate)
    }

    /*private fun loadArrayGender() {
        val gender = resources.getStringArray(R.array.gender)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, gender)
        binding.genderAc.setAdapter(arrayAdapter)
    }*/

    private fun loadArrayComponent() {
        val komponen = resources.getStringArray(R.array.komponen)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, komponen)
        binding.componentAc.setAdapter(arrayAdapter)
    }

    /*private fun updateProfile() {
        //Retrieve the entered data
        val name = binding.nameEt.text.toString().trim()
        val address = binding.addressEt.text.toString().trim()
        val birthPlace = binding.birthPlaceEt.text.toString().trim()
        val birthDate = binding.birthEt.text.toString().trim()
        val phoneNumber = binding.numberEt.text.toString().trim()
        val component = binding.componentAc.text.toString().trim()

        val updateProfileRequest = MyProfileRequest(name, birthPlace, birthDate, phoneNumber, address, component)

        apiClient.getApiService(this).updateUserProfile(updateProfileRequest)
            .enqueue(object : Callback<MyProfileResponse> {
                override fun onFailure(call: Call<MyProfileResponse>, t: Throwable) {
                    Toast.makeText(this@ProfileEditActivity,"Gagal mendaftarkan karena ${t.message} ", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<MyProfileResponse>, response: Response<MyProfileResponse>) {
                    val updateResponse = response.body()

                    if (updateResponse?.statusCode==true){

                        Toast.makeText(this@ProfileEditActivity, "Berhasil membuat akun, silahkan Login", Toast.LENGTH_LONG).show()
                        finish()
                    }
                    else{
                        Toast.makeText(this@ProfileEditActivity,"Gagal memperbarui profil", Toast.LENGTH_SHORT).show()
                    }
                }
            })
    }*/
}