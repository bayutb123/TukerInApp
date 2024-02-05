package com.bayutb123.tukerin.data.utils

import android.content.Context
import android.net.Uri
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream


object MediaUtils {
    fun preparePart(uris: List<Uri>, context: Context): List<MultipartBody.Part> {
        val multipartBodyPartList: MutableList<MultipartBody.Part> = mutableListOf()

        for (uri in uris) {
            val realPath = getRealPathFromUri(uri, context)

            Timber.tag("RealPath").d(realPath.toString())

            if (realPath != null) {
                val file = File(realPath)
                Timber.tag("File").d(file.toString())
                if (file.exists()) {
                    Timber.tag("File").d(file.name)
                    val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                    val body = MultipartBody.Part.createFormData("image", file.name, requestFile)
                    multipartBodyPartList.add(body)
                } else {
                    // Handle the case where the file doesn't exist
                    // Log an error or take appropriate action
                }
            } else {
                // Handle the case where realPath is null
                // Log an error or take appropriate action
            }
        }
        Timber.tag("MultipartBodyPartList").d(multipartBodyPartList.toString())
        return multipartBodyPartList
    }

    private fun getRealPathFromUri(uri: Uri, context: Context): String? {
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
            return null
        }
    }

}
