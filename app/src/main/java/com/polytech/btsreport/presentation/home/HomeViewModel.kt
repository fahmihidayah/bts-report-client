package com.polytech.btsreport.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polytech.btsreport.data.dto.response.Visitation
import com.polytech.btsreport.data.repository.HomeRepository
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val repository = HomeRepository()

    private val _visitationsState = MutableLiveData<VisitationsState>(VisitationsState.Idle)
    val visitationsState: LiveData<VisitationsState> = _visitationsState

    private val _visitations = MutableLiveData<List<Visitation>>()
    val visitations: LiveData<List<Visitation>> = _visitations

    fun loadVisitations() = viewModelScope.launch {
        _visitationsState.value = VisitationsState.Loading

        val result = repository.getVisitations()

        result.onSuccess { visitationsList ->
            _visitations.value = visitationsList
            _visitationsState.value = VisitationsState.Success(
                message = "Loaded ${visitationsList.size} visitations",
                data = visitationsList
            )
        }.onFailure { exception ->
            _visitationsState.value = VisitationsState.Error(exception.message ?: "Unknown error")
        }
    }
}