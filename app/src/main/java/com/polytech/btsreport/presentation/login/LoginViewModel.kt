package com.polytech.btsreport.presentation.login

import androidx.lifecycle.ViewModel
import com.polytech.btsreport.data.dto.LoginForm

class LoginViewModel : ViewModel(){
    var loginForm : LoginForm = LoginForm(
        email = "",
        password = ""
    )
}