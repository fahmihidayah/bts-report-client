package com.polytech.btsreport.data.repository

import android.util.Log
import com.polytech.btsreport.data.api.ApiFactory
import com.polytech.btsreport.data.api.ApiService
import com.polytech.btsreport.data.dto.request.LoginForm
import com.polytech.btsreport.data.dto.response.LoginResponse
import com.polytech.btsreport.utils.TokenManager

class LoginRepository {
    private val apiService: ApiService = ApiFactory.createApiService()

    suspend fun login(loginForm: LoginForm): Result<LoginResponse> {
        return try {
            val response = apiService.login(loginForm)

            if (response.isSuccessful) {
                val loginResponse = response.body()
                if (loginResponse != null && loginResponse.token != null) {
                    // Save token and user data to MMKV
                    saveUserData(loginResponse)
                    Result.success(loginResponse)
                } else {
                    Result.failure(Exception("Invalid response from server"))
                }
            } else {
                val errorMessage = when (response.code()) {
                    401 -> "Invalid email or password"
                    404 -> "User not found"
                    500 -> "Server error. Please try again later"
                    else -> "Login failed: ${response.message()}"
                }
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Log.e("LoginRepository", "Login error", e)
            Result.failure(Exception("Network error: ${e.message}"))
        }
    }

    private fun saveUserData(loginResponse: LoginResponse) {
        loginResponse.token?.let { TokenManager.saveToken(it) }
        loginResponse.user?.let { user ->
            user.id?.let { TokenManager.saveUserId(it) }
            user.email?.let { TokenManager.saveUserEmail(it) }
            user.name?.let { TokenManager.saveUserName(it) }
        }
    }

    fun isLoggedIn(): Boolean {
        return TokenManager.isLoggedIn()
    }

    fun logout() {
        TokenManager.clearAll()
    }
}
