package com.example.project.model

class Film{
    var id : Int = 0
    var title : String = ""
    var image : String = ""
    var price : Int = 0

    constructor (title: String, image:String, price: Int) {
        this.title = title
        this.image = image
        this.price = price
    }
    constructor () {}
}