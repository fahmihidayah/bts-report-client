package com.polytech.btsreport.data.repository

import android.util.Log
import com.polytech.btsreport.data.api.ApiFactory
import com.polytech.btsreport.data.api.ApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody

class ReportRepository {
    private val apiService: ApiService = ApiFactory.createApiService()

    suspend fun updateVisitation(
        id: String,
        state: String,
        file: MultipartBody.Part,
        description: RequestBody
    ): Result<Unit> {
        return try {
            apiService.updateVisitation(id, state, file, description)
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("ReportRepository", "Error updating visitation", e)
            Result.failure(Exception("Failed to update visitation: ${e.message}"))
        }
    }
}
