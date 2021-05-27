package com.vigilant.Network.basic

import com.vigilant.Home.Model.WantedListDTO
import com.vigilant.Login.Model.LoginDTO
import com.vigilant.Model.GetAllReportesDTO
import com.vigilant.Model.GetNewsListDTO.NewsListDTO
import com.vigilant.Requests.LoginRequest
import com.vigilant.Requests.SignUpRequest
import com.vigilant.Requests.SocialSignUpRequest
import com.vigilant.SocialSingUpDTO
import com.vigilant.UploadFile.UploadFileResponse
import com.vigilant.VideoList.Model.GetAllVideosDTO
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface Api {

    @POST("users/login")
    fun login(@Body request: LoginRequest): Call<LoginDTO>

    @GET("serviceProviders/{id}")
    fun callSendEmailApi(@Path("id") provider_id: String?): Call<Void?>

    @POST("users")
    fun signUp(@Body request: SignUpRequest): Call<Void>

    @POST("users/social")
    fun socialsignUp(@Body request: SocialSignUpRequest): Call<SocialSingUpDTO>

    @GET("reports")
    fun getAllReport(): Call<GetAllReportesDTO>

    @GET("wanted-list")
    fun getAllWantedReport(): Call<WantedListDTO>

    @GET("news")
    fun getAllNews(): Call<NewsListDTO>

    @GET("videos")
    fun getAllVideos(): Call<GetAllVideosDTO>

    @Multipart
    @POST("files")
    fun uploadFile(@Part file: MultipartBody.Part): Call<UploadFileResponse>
}