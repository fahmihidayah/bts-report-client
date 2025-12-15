package com.polytech.btsreport.presentation.login

import com.polytech.btsreport.data.dto.response.LoginResponse

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val message: String, val data: LoginResponse) : LoginState()
    data class Error(val message: String) : LoginState()
}
