package com.polytech.btsreport.data.dto.response

import com.google.gson.annotations.SerializedName

data class User(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("sessions")
	val sessions: List<SessionsItem?>? = null,

	@field:SerializedName("phoneNumber")
	val phoneNumber: String? = null,

	@field:SerializedName("_strategy")
	val strategy: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("collection")
	val collection: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)

data class SessionsItem(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("expiresAt")
	val expiresAt: String? = null
)
