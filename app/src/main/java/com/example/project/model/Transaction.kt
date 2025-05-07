package com.example.project.model

class Transaction {
    var id: Int = 0
    var film_id: Int = 0
    var film_title: String = ""
    var film_image: String = ""
    var film_price: Int = 0
    var user_id: Int = 0
    var quantity: Int = 0

    constructor(id: Int, film_id: Int, film_title: String, film_image: String, film_price: Int, user_id: Int, quantity: Int) {
        this.id = id
        this.film_id = film_id
        this.film_title = film_title
        this.film_image = film_image
        this.film_price = film_price
        this.user_id = user_id
        this.quantity = quantity
    }
    constructor () {}
}