package com.vigilant.Network.basic

import android.content.Context
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import com.vigilant.Network.rest.APIErrors
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException


abstract class APICallback<T>(context: Context, showProgress: Boolean) : Callback<T> {

    private var mContext:Context? = null
    private var progressDialog: ProgressDialog? = null
    init {
        mContext = context
        if (showProgress)
            progressDialog = ProgressDialog(mContext!!)
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        try {
            if (null != progressDialog) progressDialog!!.dismiss()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        t.apply { onFailed(Throwable("Please check your internet connection and try again.")) }
    }

    override fun onResponse(call: Call<T>, response: Response<T>) {
        try {
            if (null != progressDialog) progressDialog!!.dismiss()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        when {
            response.isSuccessful -> {
                onSuccess(response)
            }
            response.code() == 401 -> {
               /* mContext?.let {
                    PreferenceHelper.clearPrefs(it)
                    it.launchActivity<LoginActivity> {
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    }
                }*/
            }

            response.code() == 503 -> {
                onFailed(Throwable("Slow Internet, Please try after sometime."))
            }

            response.code() == 500 -> {
                onFailed(Throwable("Slow Internet, Please try after sometime."))
            }
            response.code() == 404 -> {
                onFailed(Throwable("Item not found."))
            }
            else -> {
                response.errorBody()?.let {
                    when (it) {
                        is SocketTimeoutException -> {
                            // "Connection Timeout";
                            onFailed(Throwable("Slow Internet, Please try after sometime."))
                        }
                        is IOException -> {
                            // "Timeout";
                            onFailed(Throwable("Slow Internet, Please try after sometime."))
                        }
                        else -> {
                            try {
                                val gson = Gson()
                                val type = object : TypeToken<APIErrors>() {}.type
                                val errorResponse: APIErrors? = gson.fromJson(it.charStream(), type)
                                errorResponse?.let { error ->
                                    onFailed(Throwable(error.message))
                                }
                            } catch (exception: IllegalStateException) {
                                onFailed(Throwable("Slow Internet, Please try after sometime."))
                            } catch (exception: JsonSyntaxException) {
                                onFailed(Throwable("Slow Internet, Please try after sometime."))
                            }
                        }
                    }
                } ?: run {
                    onFailed(Throwable("Default " + response.code() + " " + response.message()))
                }
            }
        }
    }

    abstract fun onSuccess(response: Response<T>)
    abstract fun onFailed(throwable: Throwable)
}