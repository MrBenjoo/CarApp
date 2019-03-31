package com.example.benjo.swecar.data.network

/*
const are compile time constants. Meaning that their value has to be assigned during compile time,
unlike val, where it can be done at runtime.
This means, that const can never be assigned to a function or any class constructor,
but only to a String or primitive.
 */

object UrlManager {
    const val API_ENDPOINT = "vehicle/regno/{reg}/"
    const val API_TOKEN = "o4SOIaXVfGImZgQj0bq6gozpgmHf3JKJZbwIUOBPWdH9hr1yxRJ9zkIgylSn"
    const val API_BASE_URL = "https://api.biluppgifter.se/api/v1/"
}