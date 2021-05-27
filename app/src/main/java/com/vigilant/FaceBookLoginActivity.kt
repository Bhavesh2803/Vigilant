package com.vigilant

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.mssinfotech.mycity.Utility.CommonFunction.showErrorToast
import com.mssinfotech.mycity.Utility.CommonFunction.showSuccessToast
import com.vigilant.Home.DashboardActivity
import com.vigilant.Network.basic.APICallback
import com.vigilant.Network.rest.ServiceGenerator
import com.vigilant.Requests.SocialSignUpRequest
import com.vigilant.databinding.ActivityFaceBookLoginBinding
import retrofit2.Response
import java.util.*

class FaceBookLoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFaceBookLoginBinding
    var callbackManager: CallbackManager? = null
    //LoginButton loginButton = null;


    //LoginButton loginButton = null;
    private val email_layout: RelativeLayout? = null
    private val tv_login: TextView? = null
    private val edt_email: EditText? = null
    private val facebook_id = ""
    private val user_name = ""
    private val login_type = ""
    private var email = ""
    private var first_name = ""
    private var full_name = ""
    private var last_name = ""
    private var account_id = ""


    private var image_uri: Uri? = null
    var ct: Context = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFaceBookLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try { // before onCreate in MainActivity

            FacebookSdk.sdkInitialize(applicationContext)

            binding.tvLogin.setOnClickListener {
                callSocialSignUpApi(account_id, full_name, binding.edtEmail.text.toString(), "user", "" + image_uri)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        //  loginButton.setReadPermissions(Arrays.asList(EMAIL));
        // If you are using in a fragment, call loginButton.setFragment(this);

        // Callback registration


        //  loginButton.setReadPermissions(Arrays.asList(EMAIL));
        // If you are using in a fragment, call loginButton.setFragment(this);

        // Callback registration
        val loginButton = LoginButton(this)
        callbackManager = CallbackManager.Factory.create()
        loginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult?> {
            override fun onSuccess(loginResult: LoginResult?) {
                if (loginResult != null) {
                }
            }

            override fun onCancel() {
                // App code
            }

            override fun onError(exception: FacebookException) {
                // App code
                Log.e("nuj",exception.message.toString())
            }
        })


        LoginManager.getInstance().registerCallback(callbackManager,
                object : FacebookCallback<LoginResult?> {
                    override fun onSuccess(loginResult: LoginResult?) {
                        try {
                            if (loginResult != null) {
                                val request = GraphRequest.newMeRequest(
                                        loginResult.accessToken
                                ) { `object`, response ->
                                    Log.v("SignUpPassword", response.toString())
                                    try {
                                        if (`object`.has("email")) email = `object`.getString("email")
                                        if (`object`.has("first_name")) first_name = `object`.getString("first_name")
                                        if (`object`.has("name")) full_name = `object`.getString("name")
                                        if (`object`.has("last_name")) last_name = `object`.getString("last_name")
                                        if (full_name != null && full_name.contains(" ")) {
                                            val full_name_ar = full_name.split(" ".toRegex()).toTypedArray()
                                            first_name = full_name_ar[0]
                                            last_name = full_name_ar[1]
                                        }
                                        full_name = "$first_name $last_name"
                                        if (`object`.has("id")) account_id = `object`.getString("id")
                                        val profile = Profile.getCurrentProfile()
                                        val id = profile.id
                                        val link = profile.linkUri.toString()
                                        Log.i("Link", link)
                                        if (Profile.getCurrentProfile() != null) {
                                            //     Log.i("Login", "ProfilePic" + Profile.getCurrentProfile().getProfilePictureUri(400, 200));
                                            image_uri = Profile.getCurrentProfile().getProfilePictureUri(400, 520)
                                        }
                                    } catch (e: Exception) {
                                        e.printStackTrace()
                                    }
                                    //LoginManager.getInstance().logOut();
                                    if (image_uri != null) //AppSession.save(ct, Constants.PIC1, "" + image_uri);
                                        if (full_name != null && !full_name.equals("", ignoreCase = true)) //AppSession.save(ct, Constants.USER_NAME, full_name);
                                        //AppSession.save(ct, Constants.PIC1, image_uri.toString());
                                         //   CommonFunction.showToast(ct, full_name + " " + account_id)
                                    makeRequestForSignup()
                                    LoginManager.getInstance().logOut()
                                    if (email == null || email.equals("", ignoreCase = true)) {
                                        email_layout!!.visibility = View.VISIBLE
                                    } else {
                                        //finish()
                                        callSocialSignUpApi(account_id, full_name, email, "user", "" + image_uri)
                                    }
                                }
                                val parameters = Bundle()
                                parameters.putString("fields", "id,name,email")
                                request.parameters = parameters
                                request.executeAsync()
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    override fun onCancel() {
                        // App code
                        Log.e("nuj","exception.message.toString()")
                    }

                    override fun onError(exception: FacebookException) {
                        // App code
                        if (exception != null) {
                            finish()
                        }
                    }
                })

        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("id"))

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }

    }

    private fun makeRequestForSignup() {
      /*  val refreshedToken: String = FirebaseInstanceId.getInstance().getToken()
        val signUpParameter = SocialLoginParameter()
        signUpParameter.setName(full_name)
        signUpParameter.setEmail(email)
        signUpParameter.setFcm_token(refreshedToken)
        signUpParameter.setSocial_profile("" + image_uri)
        signUpParameter.setLogin_type("FACEBOOK")
        signUpParameter.setSocial_id("" + account_id)
        signUpParameter.setDevice_type("1")
        SocialLoginManager(this@FaceBookLoginActivity).SocialLogin(signUpParameter)*/
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager!!.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun callSocialSignUpApi(socialId: String, name: String, email: String, userType: String, profilePic: String) {


        val api = ServiceGenerator.getClient(false)
        val socialsignUp = api.socialsignUp(SocialSignUpRequest(socialId, name, email, userType, profilePic))
        socialsignUp.enqueue(object : APICallback<SocialSingUpDTO>(this, true) {

            override fun onSuccess(response: Response<SocialSingUpDTO>) {
              showSuccessToast(ct, response.message())
                startActivity(Intent(this@FaceBookLoginActivity, DashboardActivity::class.java))
                finishAffinity()
            }

            override fun onFailed(throwable: Throwable) {
                throwable.message?.let { showErrorToast(ct, it) }
            }
        })



    }
}