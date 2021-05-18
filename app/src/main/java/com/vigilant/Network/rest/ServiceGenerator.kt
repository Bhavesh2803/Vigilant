package com.vigilant.Network.rest

import com.vigilant.Network.basic.Api
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
                requestBuilder.addHeader("Authorization", "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjYwOWI3MjFjODY5MTcxMDFlYzJhNjBlZCIsImlhdCI6MTYyMTAwMTI0OH0.KQQTY67hA6DM75GbsWmYq6l2P_F29slNCcFjrad012brVrgpwKXeuF5_xHVGsWZv4twLJ-Y_uRt4kVJkoWqzyCLRApyDPhp-2Cb0Fg2d0HVrmb-I5XkKCEGxkqOmJYqgsON7gs7RLnvafPgpTb8RviVpCx0iDDGsf13Qt3MkFbc")
                }

                val request: Request = requestBuilder.build()
                return chain.proceed(request)




                return chain.proceed(original)
            }
        }).build()

        return httpClient.build()
    }


}