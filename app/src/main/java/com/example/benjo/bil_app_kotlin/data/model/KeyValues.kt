package com.example.benjo.bil_app_kotlin.data.model


data class KeyValues(val value_1: String? = "", val value_2: String? = "", val value_3: String? = "") {

    fun toFormattedText() : String? {
        var formattedText : String? = null
        if(value_1 != null) formattedText = value_1
        formattedText = concation(value_2, formattedText)
        formattedText = concation(value_3, formattedText)
        return formattedText
    }

    private fun concation(value : String?, formattedText: String?) : String? {
        var formattedTextCopy = formattedText
        if(!value.isNullOrBlank())
            if(formattedTextCopy != null)
                formattedTextCopy += " / $value"
            else
                formattedTextCopy = value
        return formattedTextCopy
    }

}