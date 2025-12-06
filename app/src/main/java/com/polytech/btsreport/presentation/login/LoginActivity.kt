package com.polytech.btsreport.presentation.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.polytech.btsreport.databinding.LoginActivityBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: LoginActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Login"

        binding.loginButton.setOnClickListener {
            startActivity(android.content.Intent(this, com.polytech.btsreport.presentation.home.HomeActivity::class.java))
            finish()
        }
    }
}