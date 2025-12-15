package com.polytech.btsreport.presentation.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.polytech.btsreport.databinding.LoginActivityBinding
import com.polytech.btsreport.presentation.home.HomeActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: LoginActivityBinding

    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Login"

        binding.loginViewModel = loginViewModel
        binding.lifecycleOwner = this

        observeLoginState()
    }

    private fun observeLoginState() {
        loginViewModel.loginState.observe(this) { state ->
            when (state) {
                is LoginState.Idle -> {
                    hideLoading()
                }
                is LoginState.Loading -> {
                    showLoading()
                }
                is LoginState.Success -> {
                    hideLoading()
                    Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
                    navigateToHome()
                }
                is LoginState.Error -> {
                    hideLoading()
                    Toast.makeText(this, state.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun showLoading() {
        binding.loginButton.isEnabled = false
        binding.loginButton.text = "Logging in..."
    }

    private fun hideLoading() {
        binding.loginButton.isEnabled = true
        binding.loginButton.text = "Login"
    }

    private fun navigateToHome() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }
}