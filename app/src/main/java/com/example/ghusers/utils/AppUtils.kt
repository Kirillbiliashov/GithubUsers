package com.example.ghusers.utils

import java.text.SimpleDateFormat
import java.util.Date

object AppUtils {

    fun Date.toFormattedString(): String {
        val dateFormat = SimpleDateFormat("dd.MM.yy")
        return dateFormat.format(this)
    }
}