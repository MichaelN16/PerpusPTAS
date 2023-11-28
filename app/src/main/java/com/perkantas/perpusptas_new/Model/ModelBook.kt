package com.perkantas.perpusptas_new.Model

class ModelBook {
    //variables
    var bookId:Int = 0
    lateinit var bookCode:String
    lateinit var bookTitle:String
    lateinit var author:String
    var categoryId:Int = 0
    lateinit var category:String
    lateinit var publisher:String
    var bookCover:Int = 0
    lateinit var bookDesc:String
    var stock:Int = 0

    //progress bar to load book cover
    var isLoading: Boolean = false
}