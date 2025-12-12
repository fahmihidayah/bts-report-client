package com.polytech.btsreport.presentation.login

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.polytech.btsreport.data.dto.LoginForm
import com.polytech.btsreport.databinding.LoginActivityBinding

class LoginActivity : AppCompatActivity() {
    var loginForm : LoginForm = LoginForm(
        email = "",
        password = ""
    )
    private lateinit var binding: LoginActivityBinding
    private val loginViewModel : LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Login"

        binding.form = loginForm

    }
}