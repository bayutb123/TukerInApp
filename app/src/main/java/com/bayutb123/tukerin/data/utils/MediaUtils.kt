package com.bayutb123.tukerin.data.utils

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

object MediaUtils {
    fun preparePart(uris: List<Uri>) : List<MultipartBody.Part> {
        val multipartBodyPartList : MutableList<MultipartBody.Part> = mutableListOf()
        var index = 0
        uris.forEach { uri ->
            val file = uri.path?.let { File(it) }
            val body = file?.asRequestBody("image/png".toMediaTypeOrNull())
            body?.let { multipartBodyPartList.add(MultipartBody.Part.createFormData("image $$index", file.name, it)) }
            index++
        }
        return multipartBodyPartList
    }
}