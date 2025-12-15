package com.polytech.btsreport.presentation.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.polytech.btsreport.data.dto.request.LoginForm
import com.polytech.btsreport.data.repository.LoginRepository
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    var loginForm: LoginForm = LoginForm()

    private val repository = LoginRepository()

    private val _loginState = MutableLiveData<LoginState>(LoginState.Idle)
    val loginState: LiveData<LoginState> = _loginState

    fun login() = viewModelScope.launch {
        Log.d("LoginViewModel", Gson().toJson(loginForm))

        if (!validateInput()) {
            return@launch
        }

        _loginState.value = LoginState.Loading

        val result = repository.login(loginForm)

        result.onSuccess { loginResponse ->
            _loginState.value = LoginState.Success(
                message = loginResponse.message ?: "Login successful!",
                data = loginResponse
            )
        }.onFailure { exception ->
            _loginState.value = LoginState.Error(exception.message ?: "Unknown error")
        }
    }

    private fun validateInput(): Boolean {
        return when {
            loginForm.email.isBlank() -> {
                _loginState.value = LoginState.Error("Email cannot be empty")
                false
            }
            loginForm.password.isBlank() -> {
                _loginState.value = LoginState.Error("Password cannot be empty")
                false
            }
            !android.util.Patterns.EMAIL_ADDRESS.matcher(loginForm.email).matches() -> {
                _loginState.value = LoginState.Error("Invalid email format")
                false
            }
            else -> true
        }
    }
}