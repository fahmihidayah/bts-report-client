package com.polytech.btsreport.presentation.report

sealed class ReportState {
    object Idle : ReportState()
    object Loading : ReportState()
    data class Success(val message: String) : ReportState()
    data class Error(val message: String) : ReportState()
}
