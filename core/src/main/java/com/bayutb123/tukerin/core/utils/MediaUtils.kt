package com.bayutb123.tukerin.core.utils

import android.content.Context
import android.net.Uri
import id.zelory.compressor.Compressor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream


object MediaUtils {
    suspend fun preparePart(uris: List<Uri>, context: Context): Array<MultipartBody.Part> {
        val cacheDirectory = File(context.cacheDir, "image_cache")
        cacheDirectory.mkdirs()
        val parts = mutableListOf<MultipartBody.Part>()
        val images = mutableListOf<File>()
        uris.forEachIndexed { _, it ->
            val file = saveImageToCache(it, context, cacheDirectory.path) ?: return arrayOf()
            images.add(file)
        }
        images.forEachIndexed { index, it ->
            val compressedImage = Compressor.compress(context, it)
            val requestFile = compressedImage.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            val body = MultipartBody.Part.createFormData("image[$index]", it.name, requestFile)
            parts.add(body)
        }
        return parts.toTypedArray()
    }

    private fun saveImageToCache(uri: Uri, context: Context, cacheDirectory:String): File? {
        try {
            val inputStream = context.contentResolver.openInputStream(uri)
            val fileName = "${System.currentTimeMillis()}_${uri.lastPathSegment}"
            val file = File(cacheDirectory, fileName)
            FileOutputStream(file).use { outputStream ->
                inputStream?.copyTo(outputStream)
                inputStream?.close()
            }
            return file
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun clearLocalCache(context: Context) {
        val cacheDirectory = File(context.cacheDir, "image_cache")
        val files = cacheDirectory.listFiles()
        files?.forEach { it.delete() }
    }

}