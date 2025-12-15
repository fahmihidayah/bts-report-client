package com.polytech.btsreport.utils

import com.tencent.mmkv.MMKV

object TokenManager {
    private const val KEY_TOKEN = "auth_token"
    private const val KEY_USER_ID = "user_id"
    private const val KEY_USER_EMAIL = "user_email"
    private const val KEY_USER_NAME = "user_name"

    private val mmkv: MMKV by lazy {
        MMKV.defaultMMKV()
    }

    fun saveToken(token: String) {
        mmkv.encode(KEY_TOKEN, token)
    }

    fun getToken(): String? {
        return mmkv.decodeString(KEY_TOKEN)
    }

    fun saveUserId(userId: String) {
        mmkv.encode(KEY_USER_ID, userId)
    }

    fun getUserId(): String? {
        return mmkv.decodeString(KEY_USER_ID)
    }

    fun saveUserEmail(email: String) {
        mmkv.encode(KEY_USER_EMAIL, email)
    }

    fun getUserEmail(): String? {
        return mmkv.decodeString(KEY_USER_EMAIL)
    }

    fun saveUserName(name: String) {
        mmkv.encode(KEY_USER_NAME, name)
    }

    fun getUserName(): String? {
        return mmkv.decodeString(KEY_USER_NAME)
    }

    fun clearAll() {
        mmkv.clearAll()
    }

    fun isLoggedIn(): Boolean {
        return !getToken().isNullOrEmpty()
    }
}
