package com.tech.model

class User(
    val name: String,
    val email: String,
    val middleName: String,
    val phoneNumber: String,
    val birthday: String
) {
    override fun toString(): String {
        return "User(name='$name', email='$email', middleName='$middleName', phoneNumber='$phoneNumber', birthday='$birthday')"
    }
}