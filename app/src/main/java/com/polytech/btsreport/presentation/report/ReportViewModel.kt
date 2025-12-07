package com.polytech.btsreport.presentation.report

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polytech.btsreport.data.dto.response.Visitation
import com.polytech.btsreport.data.repository.ReportRepository
import com.polytech.btsreport.utils.toFile
import com.polytech.btsreport.utils.toMultipartBodyPart
import com.polytech.btsreport.utils.toRequestBody
import kotlinx.coroutines.launch
import java.io.File

class ReportViewModel : ViewModel() {
    private val reportRepository = ReportRepository()

    private val _reportForm = MutableLiveData(ReportForm())
    val reportForm: LiveData<ReportForm> = _reportForm

    private val _reportState = MutableLiveData<ReportState>(ReportState.Idle)
    val reportState: LiveData<ReportState> = _reportState

    fun updateImage(image: Bitmap) {
        _reportForm.value = _reportForm.value?.copy(image = image)
    }

    fun updateDescription(description: String) {
        _reportForm.value = _reportForm.value?.copy(description = description)
    }

    fun submitReport(visitation: Visitation, cacheDir: File) {
        val form = _reportForm.value ?: return

        if (form.image == null) {
            _reportState.value = ReportState.Error("Please capture an image")
            return
        }

        if (form.description.isBlank()) {
            _reportState.value = ReportState.Error("Please enter a description")
            return
        }

        val visitationId = visitation.id ?: run {
            _reportState.value = ReportState.Error("Invalid visitation ID")
            return
        }

        val newState = determineNewState(visitation.visitStatus)

        viewModelScope.launch {
            _reportState.value = ReportState.Loading

            try {
                val imageFile = File(cacheDir, "report_image_${System.currentTimeMillis()}.jpg")
                form.image.toFile(imageFile)

                val imagePart = imageFile.toMultipartBodyPart("image")
                val descriptionBody = form.description.toRequestBody()

                val result = reportRepository.updateVisitation(
                    id = visitationId,
                    state = newState,
                    file = imagePart,
                    description = descriptionBody
                )

                result.fold(
                    onSuccess = {
                        _reportState.value = ReportState.Success("Report submitted successfully")
                        imageFile.delete()
                    },
                    onFailure = { exception ->
                        _reportState.value = ReportState.Error(exception.message ?: "Failed to submit report")
                        imageFile.delete()
                    }
                )
            } catch (e: Exception) {
                _reportState.value = ReportState.Error(e.message ?: "An error occurred")
            }
        }
    }

    private fun determineNewState(currentState: String?): String {
        return when (currentState?.lowercase()) {
            "new" -> "in_progress"
            "in_progress" -> "complete"
            else -> "in_progress"
        }
    }

    fun resetState() {
        _reportState.value = ReportState.Idle
    }
}
