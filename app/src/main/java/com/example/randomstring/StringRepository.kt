package com.example.randomstring

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.util.Log
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

class StringRepository(private val context: Context) {

    private val CONTENT_URI: Uri = Uri.parse("content://com.iav.contestdataprovider/text")
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }

    fun fetchRandomString(length: Int): RandomString? {
        val resolver: ContentResolver = context.contentResolver

        try {
            val cursor = resolver.query(
                CONTENT_URI,
                null,
                null,
                null,
                null
            )

            cursor?.use {
                if (it.moveToFirst()) {
                    val jsonString = it.getString(it.getColumnIndexOrThrow("data"))
                    val jsonObject = JSONObject(jsonString)
                    val randomTextObject = jsonObject.getJSONObject("randomText")

                    val value: String = randomTextObject.getString("value")
                    val lengthValue: Int = randomTextObject.getInt("length")  // Ensure this is an Int
                    val createdString: String = randomTextObject.getString("created")

                    // Convert the date string to timestamp (milliseconds since epoch)
                    val created: Long = try {
                        dateFormat.parse(createdString)?.time ?: 0L
                    } catch (e: Exception) {
                        Log.e("StringRepository", "Date parsing error: ${e.message}")
                        0L
                    }

                    return RandomString(value, lengthValue, created)
                }
            }
        } catch (e: Exception) {
            Log.e("StringRepository", "Error querying content provider: ${e.message}")
        }

        return null
    }
}
