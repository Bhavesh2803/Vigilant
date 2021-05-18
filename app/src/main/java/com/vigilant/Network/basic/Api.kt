package com.vigilant.Network.basic

import com.vigilant.Model.GetAllReportesDTO
import com.vigilant.Model.GetNewsListDTO.NewsListDTO
import com.vigilant.Requests.LoginRequest
import com.vigilant.Requests.SignUpRequest
import com.vigilant.Requests.SocialSignUpRequest
import com.vigilant.SocialSingUpDTO
import com.vigilant.VideoList.Model.GetAllVideosDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Api {

    @POST("users/login")
    fun login(@Body request: LoginRequest): Call<Void>

    @POST("users")
    fun signUp(@Body request: SignUpRequest): Call<Void>

    @POST("users/social")
    fun socialsignUp(@Body request: SocialSignUpRequest): Call<SocialSingUpDTO>

    @GET("reports")
    fun getAllReport(): Call<GetAllReportesDTO>

    @GET("news")
    fun getAllNews(): Call<NewsListDTO>

    @GET("videos")
    fun getAllVideos(): Call<GetAllVideosDTO>
}