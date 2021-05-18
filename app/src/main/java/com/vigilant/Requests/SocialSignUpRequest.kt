package com.vigilant.Requests

import com.google.gson.annotations.SerializedName

data class SocialSignUpRequest(

		@field:SerializedName("socialId")
		val socialId: String? = null,
		@field:SerializedName("name")
		val name: String? = null,
		@field:SerializedName("email")
		val email: String? = null,
		@field:SerializedName("userType")
		val userType: String? = null,
		@field:SerializedName("profilePic")
		val profilePic: String? = null



)