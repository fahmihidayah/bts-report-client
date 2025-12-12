package com.polytech.btsreport.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.polytech.btsreport.databinding.TestAcitivityBinding

class TestActivity : AppCompatActivity() {

    lateinit var binding : TestAcitivityBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TestAcitivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}