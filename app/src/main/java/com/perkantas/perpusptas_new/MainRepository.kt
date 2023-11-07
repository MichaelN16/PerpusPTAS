package com.perkantas.perpusptas_new;

import com.perkantas.perpusptas_new.Interface.ApiService

class MainRepository constructor (private val apiService: ApiService){
    fun getAllBooks() = apiService.getAllBooks()
}
