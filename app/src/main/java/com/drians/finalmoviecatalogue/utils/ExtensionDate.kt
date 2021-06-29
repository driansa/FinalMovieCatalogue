package com.drians.finalmoviecatalogue.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * input: 2021-05-23
 * output: May 23, 2021
 */
fun String.formatDate(): String {
    val parser = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    val formatter = SimpleDateFormat("MMMM dd, yyyy", Locale.US)

    return try {
        val date = parser.parse(this) ?: return ""
        formatter.format(date)
    } catch (parseException: ParseException) {
        throw IllegalArgumentException("Invalid datetime format")
    }
}

/**
 * input: 2021-05-23
 * output: 2021
 */
fun String.formatDateToYear(): String {
    val parser = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    val formatter = SimpleDateFormat("yyyy", Locale.US)

    return try {
        val date = parser.parse(this) ?: return ""
        formatter.format(date)
    } catch (parseException: ParseException) {
        throw IllegalArgumentException("Invalid datetime format")
    }
}