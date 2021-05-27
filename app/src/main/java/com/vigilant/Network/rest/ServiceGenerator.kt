package com.vigilant.Network.rest

import com.mssinfotech.mycity.Utility.AppSession
import com.mssinfotech.mycity.Utility.Constants
import com.vigilant.Network.basic.Api
import com.vigilant.Utility.App
import com.vigilant.Utility.VigilantApplication
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

object ServiceGenerator {

    const val API_BASE_URL = "https://vigilant-server.herokuapp.com/api/v1/"
    private var retofit: Retrofit? = null

    fun getClient(header:Boolean): Api {
        return Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .client(getOkHttpClient(600,header))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Api::class.java)
    }


    fun getOkHttpClient(timeout: Long = 90,header:Boolean): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.apply { logging.level = HttpLoggingInterceptor.Level.BODY }

        val httpClient = OkHttpClient.Builder()
                .connectTimeout(timeout, TimeUnit.SECONDS)
                .readTimeout(timeout, TimeUnit.SECONDS)
                .addInterceptor(logging)
        httpClient.addInterceptor(object : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): Response {
                val original: Request = chain.request()

                val requestBuilder: Request.Builder = original.newBuilder()
                        .addHeader("Content-Type", "application/json")
            if(header){

                    //Log.e("Bearer","---: "+ key)
                        val token = AppSession.getValue(VigilantApplication.context,Constants.ACCESS_TOKEN)
                requestBuilder.addHeader("Authorization", "Bearer $token")
                }

                val request: Request = requestBuilder.build()
                return chain.proceed(request)




                return chain.proceed(original)
            }
        }).build()

        return httpClient.build()
    }


}