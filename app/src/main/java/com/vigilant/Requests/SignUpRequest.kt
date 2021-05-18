package com.vigilant.Requests

import com.google.gson.annotations.SerializedName

data class SignUpRequest(

		@field:SerializedName("name")
		val name: String? = null,
		@field:SerializedName("mobile")
		val mobile: String? = null,
		@field:SerializedName("email")
		val email: String? = null,
		@field:SerializedName("address")
		val address: String? = null,
		@field:SerializedName("password")
		val password: String? = null,
		@field:SerializedName("confirmPassword")
		val confirmPassword: String? = null	,
		@field:SerializedName("userType")
		val userType: String? = null
)