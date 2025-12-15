package com.polytech.btsreport.data.repository

import android.util.Log
import com.polytech.btsreport.data.api.ApiFactory
import com.polytech.btsreport.data.api.ApiService
import com.polytech.btsreport.data.dto.response.Visitation

class HomeRepository {
    private val apiService: ApiService = ApiFactory.createApiService()

    suspend fun getVisitations(): Result<List<Visitation>> {
        return try {
            val response = apiService.getVisitations()

            if (response.isSuccessful) {
                val visitationsResponse = response.body()
                if (visitationsResponse != null && visitationsResponse.data != null) {
                    Result.success(visitationsResponse.data)
                } else {
                    Result.failure(Exception("Invalid response from server"))
                }
            } else {
                val errorMessage = when (response.code()) {
                    401 -> "Unauthorized. Please login again"
                    404 -> "No visitations found"
                    500 -> "Server error. Please try again later"
                    else -> "Failed to fetch visitations: ${response.message()}"
                }
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Log.e("HomeRepository", "Error fetching visitations", e)
            Result.failure(Exception("Network error: ${e.message}"))
        }
    }
}