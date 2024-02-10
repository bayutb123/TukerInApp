package com.bayutb123.tukerin.data.utils

import android.content.Context
import android.net.Uri
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream


object MediaUtils {
    fun preparePart(uris: List<Uri>, context: Context): Array<MultipartBody.Part> {
        val parts = mutableListOf<MultipartBody.Part>()
        uris.forEachIndexed { index, it ->
            val file = File(getRealPathFromUri(it, context))
            val requestFile = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            val tag = "image[$index]"
            parts.add(MultipartBody.Part.createFormData(tag, file.name, requestFile))
        }
        return parts.toTypedArray()
    }

    private fun getRealPathFromUri(uri: Uri, context: Context): String {
        val contentResolver = context.contentResolver

        // Create a temporary file to store the content
        val tempFile = File(context.cacheDir, "temp_file")

        try {
            // Open an input stream from the content resolver
            contentResolver.openInputStream(uri)?.use { inputStream ->
                // Use a FileOutputStream to write the content to the temporary file
                FileOutputStream(tempFile).use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }

            // Return the absolute path of the temporary file
            return tempFile.absolutePath
        } catch (e: Exception) {
            // Handle exceptions, log errors, or return null
            e.printStackTrace()
            return ""
        }
    }

    fun createPartFromFile(part: MultipartBody.Part) {
        val file = File(part.body.toString())
        val requestFile = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        MultipartBody.Part.createFormData("image", file.name, requestFile)
    }

}
