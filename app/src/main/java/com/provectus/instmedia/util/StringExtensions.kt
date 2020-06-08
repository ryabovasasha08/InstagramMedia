package com.provectus.instmedia.util

import android.annotation.SuppressLint
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

@SuppressLint("SimpleDateFormat")
fun String.toCorrectDateFormat(): String {
    val inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ")
    val outputFormat = DateTimeFormatter.ofPattern("dd MMM yyyy")
    return LocalDate.parse(this, inputFormat).format(outputFormat)
}

fun String.toDateTime(): LocalDateTime {
    val inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ")
    return LocalDateTime.parse(this, inputFormat)
}