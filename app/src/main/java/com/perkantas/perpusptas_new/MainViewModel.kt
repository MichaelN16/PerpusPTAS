package com.perkantas.perpusptas_new

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perkantas.perpusptas_new.Model.Book
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel constructor(private val repository: MainRepository)  : ViewModel() {

    val bookList = MutableLiveData<List<Book>>()
    val errorMessage = MutableLiveData<String>()

    fun getAllBooks() {

        val response = repository.getAllBooks()
        response.enqueue(object : Callback<List<Book>> {
            override fun onResponse(call: Call<List<Book>>, response: Response<List<Book>>) {
                bookList.postValue(response.body())
            }

            override fun onFailure(call: Call<List<Book>>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }
}