package ru.scid.supportchat.util

import java.text.SimpleDateFormat
import java.util.Locale

object DateUtil {

    fun formatDate(date: String, sourceFormat: String, newFormat: String): String? {
        try {
            val sdf = SimpleDateFormat(sourceFormat, Locale.getDefault())
            val date = sdf.parse(date)
            return SimpleDateFormat(newFormat, Locale.getDefault()).format(date)
        } catch (e: Exception) {}
        return null
    }

}