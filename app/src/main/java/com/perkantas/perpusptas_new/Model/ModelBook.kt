package com.perkantas.perpusptas_new.Model

class ModelBook {
    //variables
    var id:Int = 0
    lateinit var bookCode:String
    lateinit var bookTitle:String
    lateinit var author:String
    lateinit var category:String
    lateinit var publisher:String
    var bookCover:Int = 0
    lateinit var bookDesc:String
    var stock:Int = 0
    var isLoading: Boolean = false
}