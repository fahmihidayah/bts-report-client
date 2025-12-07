package com.polytech.btsreport.presentation.home

import com.polytech.btsreport.data.dto.response.Visitation

sealed class VisitationsState {
    object Idle : VisitationsState()
    object Loading : VisitationsState()
    data class Success(val message: String, val data: List<Visitation>) : VisitationsState()
    data class Error(val message: String) : VisitationsState()
}
