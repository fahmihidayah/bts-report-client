package com.polytech.btsreport.data.dto.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("exp")
	val exp: Int? = null,

	@field:SerializedName("token")
	val token: String? = null,

	@field:SerializedName("user")
	val user: User? = null
)
