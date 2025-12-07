package com.polytech.btsreport.data.api

import com.polytech.btsreport.data.dto.request.LoginForm
import com.polytech.btsreport.data.dto.response.LoginResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

    @POST("technicians/login")
    suspend fun login(
        @Body loginForm: LoginForm
    ) : Response<LoginResponse>

    @GET("/visitations")
    suspend fun getVisitations()

    @POST("/visitations/update")
    @Multipart
    suspend fun updateVisitation(
        @Part("image") file: MultipartBody.Part,
        @Part("description") description: RequestBody
    )
}