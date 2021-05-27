package com.vigilant.Login

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.text.style.ClickableSpan
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.TextView
import com.jakewharton.rxbinding2.widget.RxTextView
import com.mssinfotech.mycity.Utility.AppSession
import com.mssinfotech.mycity.Utility.CommonFunction
import com.mssinfotech.mycity.Utility.CommonFunction.showErrorToast
import com.mssinfotech.mycity.Utility.CommonFunction.showInfoToast
import com.mssinfotech.mycity.Utility.CommonFunction.showSuccessToast

import com.mssinfotech.mycity.Utility.Constants
import com.vigilant.Home.DashboardActivity
import com.vigilant.FaceBookLoginActivity
import com.vigilant.GoogleLoginActivity
import com.vigilant.Login.Model.LoginDTO
import com.vigilant.Network.basic.APICallback
import com.vigilant.Network.basic.BaseActivity
import com.vigilant.Network.rest.ServiceGenerator
import com.vigilant.Requests.LoginRequest
import com.vigilant.SignupActivity
import com.vigilant.Utility.Util
import com.vigilant.databinding.ActivityLoginBinding
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.observers.DisposableObserver
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*


class LoginActivity : BaseActivity() {

    private lateinit var binding: ActivityLoginBinding


    var ct = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickableTextView(binding.tvAlreadyAccount)
        initObservable()

        binding.llGoogle.setOnClickListener {
            startActivity(Intent(this@LoginActivity, GoogleLoginActivity::class.java))
        }
        binding.llFaceBook.setOnClickListener {
            startActivity(Intent(this@LoginActivity, FaceBookLoginActivity::class.java))
        }
        binding.tvCancel.setOnClickListener {
            binding.rlSendEmail.visibility = View.GONE
        }
        binding.tvForgotPassword.setOnClickListener {
            binding.rlSendEmail.visibility = View.VISIBLE
        }
        binding.tvSkip.setOnClickListener {
            AppSession.save(ct,Constants.ACCESS_TOKEN,"eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjYwYWM4ZDU5YTA5YmE0MDAxNTNhNDVhYiIsImlhdCI6MTYyMTkyMTEzNn0.YfRMs0lgiGpn_PEF7bHaVZ_dCcA-sDpgg2XVx7LdWenRuCybCHnulqUJF9y4QoP85KiibGLXn2nZ-ueHdgVPDgjvtue7LvFhU5a_2OzmgxwJLx3T8v1XiDUqSR7L3iI12RaJgAVrYWDxH3u1IDSqMuA1X1TmgXZZfMs1UsHKgGo")
            startActivity(Intent(this@LoginActivity, DashboardActivity::class.java))
        }
        binding.tvSend.setOnClickListener {
            val email = binding.edtSentEmail.text.toString()
            if( email.isNotEmpty()){
                callForgotPasswordApi(email)
            }

        }
        binding.tvLogin.setOnClickListener {

            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()

            if (!email.equals("", ignoreCase = true)) {
                if (CommonFunction.isValidEmail(email)) {
                    if (!password.equals("", ignoreCase = true)) {
                        if (password.length >= 6) {
                            callLoginApi()
                        } else {
                            showInfoToast(ct, "Please enter at least 6 digits password")
                        }
                    } else {
                        showInfoToast(ct, "Please enter your password")
                    }
                } else {
                    showInfoToast(ct, "Please enter valid email")
                }

            } else {
                showInfoToast(ct, "Please enter your email")
            }


        }

        printHashKey()
    }

    private fun callLoginApi() {
        showLoader()
        val email = binding.edtEmail.text.toString()
        val password = binding.edtPassword.text.toString()
        val language = AppSession.getValue(ct,Constants.LANGUAGE)
        val api = ServiceGenerator.getClient(false)
        val loginCall = api.login(LoginRequest(email, password,language))

        loginCall.enqueue(object : APICallback<LoginDTO>(this, true) {

            override fun onSuccess(response: Response<LoginDTO>) {
                hideLoader()
                showSuccessToast(ct, "Login Success!")
                AppSession.save(ct,Constants.ACCESS_TOKEN,response.body()?.token)
                startActivity(Intent(this@LoginActivity, DashboardActivity::class.java))
                finishAffinity()
            }

            override fun onFailed(throwable: Throwable) {
                hideLoader()
                throwable.message?.let { showErrorToast(ct, it) }
            }
        })

    }

    private fun callForgotPasswordApi(email:String) {
        showLoader()


        val api = ServiceGenerator.getClient(false)
        val sendEmail = api.callSendEmailApi(email)

        sendEmail.enqueue(object : Callback<Void?> {
            override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                showSuccessToast(ct,response.message())
                hideLoader()
            }

            override fun onFailure(call: Call<Void?>, t: Throwable) {
                t.message?.let { showErrorToast(ct, it) }
                hideLoader()
            }
        })

    }

    private fun setupClickableTextView(termsTextView: TextView) {
        val termsOfServicesClick = object : ClickableSpan() {
            override fun onClick(p0: View) {
                startActivity(Intent(this@LoginActivity, SignupActivity::class.java))
            }

        }

        Util.makeLinks(
                termsTextView,
                arrayOf("Sign up"),
                arrayOf(termsOfServicesClick)
        )
    }

    private fun initObservable() {


        val mEmailObservable: Observable<CharSequence>?
        val mPasswordObservable: Observable<CharSequence>?
        val observable: Observable<Boolean>?
        mEmailObservable = RxTextView.textChanges(binding.edtEmail)
        mPasswordObservable = RxTextView.textChanges(binding.edtPassword)
        observable = Observable.combineLatest(mEmailObservable, mPasswordObservable,
                BiFunction(fun(o: CharSequence, c: CharSequence): Boolean {

                    return o.isNotEmpty() && CommonFunction.isValidEmail(o) && c.isNotEmpty() && c.length > 5
                })
        )
        observable.subscribe(object : DisposableObserver<Boolean?>() {
            override fun onNext(aBoolean: Boolean) {
                if (aBoolean) {
                    // bottomSheet.btnOtpSubmit.updateButtonState(1)

                    binding.tvLogin.setBackgroundColor(Color.parseColor(Constants.THEME_COLOR));


                } else {
                    // bottomSheet.btnOtpSubmit.updateButtonState(2)

                    binding.tvLogin.setBackgroundColor(Color.GRAY);
                }
            }

            override fun onError(e: Throwable) {}
            override fun onComplete() {}
        })

    }

    private fun printHashKey() {
        try {
            val info = packageManager.getPackageInfo(
                    "com.vigilant",
                    PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.e("keyhash:", Base64.encodeToString(md.digest(), Base64.DEFAULT))
                Log.d("keyhash:", Base64.encodeToString(md.digest(), Base64.DEFAULT))
                Log.v("keyhash:", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            }
        } catch (e: PackageManager.NameNotFoundException) {
        } catch (e: NoSuchAlgorithmException) {
        }
    }
}