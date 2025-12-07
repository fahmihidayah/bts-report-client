package com.polytech.btsreport.data.dto.response

data class ListVisitationsResponse(
	val code: Int? = null,
	val error: String? = null,
	val message: String? = null,
    val data : List<Visitation>? = null
)

