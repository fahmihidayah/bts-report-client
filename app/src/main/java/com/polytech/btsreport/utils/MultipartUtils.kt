package com.polytech.btsreport.utils

import android.graphics.Bitmap
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream

fun File.toMultipartBodyPart(partName: String): MultipartBody.Part {
    val requestFile = this.asRequestBody("image/*".toMediaTypeOrNull())
    return MultipartBody.Part.createFormData(partName, this.name, requestFile)
}

fun String.toRequestBody(): RequestBody {
    return this.toRequestBody("text/plain".toMediaTypeOrNull())
}