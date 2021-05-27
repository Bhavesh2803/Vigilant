package com.vigilant.Requests

import com.google.gson.annotations.SerializedName

data class LoginRequest(

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("password")
	val password: String? = null,

		@field:SerializedName("language")
	val language: String? = null
)