package com.example.benjo.bil_app_kotlin.domain


/*
const are compile time constants. Meaning that their value has to be assigned during compile time,
unlike val, where it can be done at runtime.
This means, that const can never be assigned to a function or any class constructor,
but only to a String or primitive.
 */

object UrlManager {
    const val VEHICLE = "vehicle/regno/{reg}/"
    const val API_TOKEN = "o4SOIaXVfGImZgQj0bq6gozpgmHf3JKJZbwIUOBPWdH9hr1yxRJ9zkIgylSn"
    const val API_HOST = "https://api.biluppgifter.se/api/v1/"
}