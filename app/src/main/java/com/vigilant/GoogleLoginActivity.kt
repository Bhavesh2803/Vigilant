package com.vigilant

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.mssinfotech.mycity.Utility.CommonFunction.showErrorToast
import com.mssinfotech.mycity.Utility.CommonFunction.showSuccessToast
import com.vigilant.Home.DashboardActivity
import com.vigilant.Network.basic.APICallback
import com.vigilant.Network.rest.ServiceGenerator
import com.vigilant.Requests.SocialSignUpRequest
import retrofit2.Response

class GoogleLoginActivity : AppCompatActivity() {

    var mGoogleSignInClient: GoogleSignInClient? = null
val ct:Context = this
    private val RC_SIGN_IN = 9001
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_login)

        try {

            val gso =
                    GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestIdToken(getResources().getString(R.string.google_client_id))
                            .requestEmail()
                            .build()

            mGoogleSignInClient = GoogleSignIn.getClient(this, gso)



                signIn()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun signIn() {
        val signInIntent = mGoogleSignInClient?.signInIntent
        startActivityForResult(
                signInIntent, RC_SIGN_IN
        )
    }

    private fun signOut() {
        mGoogleSignInClient?.signOut()
                ?.addOnCompleteListener(this) {
                    // Update your UI here
                    finish()
                }
    }

    private fun revokeAccess() {
        mGoogleSignInClient?.revokeAccess()
                ?.addOnCompleteListener(this) {
                    // Update your UI here
                }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {

            val task =
                    GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(
                    ApiException::class.java
            )
            // Signed in successfully
            val googleId = account?.id ?: ""
            Log.i("Google ID",googleId)

            val googleFirstName = account?.givenName ?: ""
            Log.i("Google First Name", googleFirstName)

            val googleLastName = account?.familyName ?: ""
            Log.i("Google Last Name", googleLastName)

            val googleEmail = account?.email ?: ""
            Log.i("Google Email", googleEmail)

            val googleProfilePicURL = account?.photoUrl.toString()
            Log.i("Google Profile Pic URL", googleProfilePicURL)

            val googleIdToken = account?.idToken ?: ""
            Log.i("Google ID Token", googleIdToken)

            callSocialSignUpApi(googleIdToken,googleFirstName+" "+googleLastName,googleEmail,"user",googleProfilePicURL )
            //CommonFunction.showToast(ct,googleFirstName + " "+googleLastName)

        } catch (e: ApiException) {
            // Sign in was unsuccessful
            Log.e(
                    "failed code=", e.statusCode.toString()
            )
        }
     // finish()
    }

    private fun callSocialSignUpApi(socialId:String,name:String,email:String,userType:String,profilePic:String) {


        val api = ServiceGenerator.getClient(false)
        val socialsignUp = api.socialsignUp(SocialSignUpRequest(socialId, name, email, userType, profilePic))
                socialsignUp.enqueue(object : APICallback<SocialSingUpDTO>(this, true) {

            override fun onSuccess(response: Response<SocialSingUpDTO>) {
          showSuccessToast(ct, response.message())
                startActivity(Intent(this@GoogleLoginActivity, DashboardActivity::class.java))
                finishAffinity()
                signOut()
            }

            override fun onFailed(throwable: Throwable) {
                throwable.message?.let { showErrorToast(ct, it) }
                signOut()
            }
        })
                
                

    }
}