package com.polytech.btsreport.presentation.report

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.viewModels
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.widget.addTextChangedListener
import com.polytech.btsreport.data.dto.response.Visitation
import com.polytech.btsreport.databinding.ReportActivityBinding
import java.io.File

class ReportActivity : AppCompatActivity() {

    private lateinit var binding: ReportActivityBinding
    private val reportViewModel: ReportViewModel by viewModels()
    private var currentVisitation: Visitation? = null
    lateinit var imageUri: Uri
    lateinit var file : File
    private val cameraLauncher = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { result ->
        if (result) {
            binding.imageViewResult.setImageURI(imageUri)
            reportViewModel.updateImage(file.path)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ReportActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        file = File(this.cacheDir, "temp_image.jpg")
        imageUri = FileProvider.getUriForFile(
            this,
            "com.polytech.btsreport.fileprovider", // Replace with your app's file provider authority
            file
        )

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Report"

        getVisitationDataFromIntent()
        setupListeners()
        observeViewModel()
    }

    private fun getVisitationDataFromIntent() {
        val visitationId = intent.getStringExtra("VISITATION_ID")
        val visitationStatus = intent.getStringExtra("VISITATION_STATUS")
        val siteName = intent.getStringExtra("SITE_NAME")

        currentVisitation = Visitation(
            id = visitationId,
            visitStatus = visitationStatus,
            siteName = siteName
        )
    }

    private fun setupListeners() {
        binding.imageViewResult.setOnClickListener {
            openCamera()
        }

        binding.detailEditText.addTextChangedListener { text ->
            reportViewModel.updateDescription(text.toString())
        }

        binding.buttonSubmit.setOnClickListener {
            currentVisitation?.let { visitation ->
                reportViewModel.submitReport(visitation, cacheDir)
            } ?: run {
                Toast.makeText(this, "Invalid visitation data", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observeViewModel() {
        reportViewModel.reportState.observe(this) { state ->
            when (state) {
                is ReportState.Idle -> {
                    binding.buttonSubmit.isEnabled = true
                }
                is ReportState.Loading -> {
                    binding.buttonSubmit.isEnabled = false
                    Toast.makeText(this, "Submitting report...", Toast.LENGTH_SHORT).show()
                }
                is ReportState.Success -> {
                    binding.buttonSubmit.isEnabled = true
                    Toast.makeText(this, state.message, Toast.LENGTH_LONG).show()
                    finish()
                }
                is ReportState.Error -> {
                    binding.buttonSubmit.isEnabled = true
                    Toast.makeText(this, state.message, Toast.LENGTH_LONG).show()
                    reportViewModel.resetState()
                }
            }
        }
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraLauncher.launch(imageUri)
    }
}