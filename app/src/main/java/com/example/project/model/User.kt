package com.example.project.model

class User{
    var id : Int = 0
    var phone : String = ""
    var password : String = ""

    constructor (phone: String, password: String) {
        this.phone = phone
        this.password = password
    }
    constructor () {}
}