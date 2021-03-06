package com.vigilant

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import com.jakewharton.rxbinding2.widget.RxTextView
import com.mssinfotech.mycity.Utility.CommonFunction
import com.mssinfotech.mycity.Utility.CommonFunction.showInfoToast
import com.mssinfotech.mycity.Utility.Constants
import com.vigilant.Home.DashboardActivity
import com.vigilant.Network.basic.APICallback
import com.vigilant.Network.rest.ServiceGenerator
import com.vigilant.Requests.SignUpRequest
import com.vigilant.Utility.Util
import com.vigilant.databinding.ActivitySignupBinding
import io.reactivex.Observable
import io.reactivex.functions.Function6
import io.reactivex.observers.DisposableObserver
import retrofit2.Response

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    var user_type = "citizen"
    var allField = false
    var ct = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickableTextView(binding.tvAlreadyAccount)
        initObservable()

        binding.tvSignup.setOnClickListener {
            if (allField) {
                callSignupApi()
            } else {
                showInfoToast(ct, "Please fill all field")
            }

        }

        binding.tvAgent.setOnClickListener {
            binding.tvAgent.setTextColor(resources.getColor(R.color.theme_color))
            binding.tvCitizen.setTextColor(resources.getColor(R.color.gray_text_color))
            user_type = "Agent"
        }
        binding.tvCitizen.setOnClickListener {
            binding.tvCitizen.setTextColor(resources.getColor(R.color.theme_color))
            binding.tvAgent.setTextColor(resources.getColor(R.color.gray_text_color))
            user_type = "User"
        }
    }

    private fun setupClickableTextView(termsTextView: TextView) {
        val termsOfServicesClick = object : ClickableSpan() {
            override fun onClick(p0: View) {
                finish()
            }
        }

        Util.makeLinks(
                termsTextView,
                arrayOf("Sign In"),
                arrayOf(termsOfServicesClick)
        )
    }

    private fun callSignupApi() {

        val full_name = binding.edtFullName.text.toString()
        val contact_number = binding.edtContactNumber.text.toString()
        val email = binding.edtEmailId.text.toString()
        val address = binding.edtAddress.text.toString()
        val password = binding.edtPassword.text.toString()
        val confirm_password = binding.edtConfirmPassword.text.toString()

        val api = ServiceGenerator.getClient(false)
        val loginCall = api.signUp(SignUpRequest(full_name, contact_number, email, address, password, confirm_password, user_type))

        loginCall.enqueue(object : APICallback<Void>(this, true) {

            override fun onSuccess(response: Response<Void>) {
                CommonFunction.showSuccessToast(ct, "SignUp")
                startActivity(Intent(this@SignupActivity, DashboardActivity::class.java))
            }

            override fun onFailed(throwable: Throwable) {
                throwable.message?.let { CommonFunction.showErrorToast(ct, it) }
            }
        })

    }

    private fun initObservable() {


        var mNameObservable: Observable<CharSequence>? = null
        var mContactNumberObservable: Observable<CharSequence>? = null
        var mEmailIdObservable: Observable<CharSequence>? = null
        var mAddressObservable: Observable<CharSequence>? = null
        var mPasswordObservable: Observable<CharSequence>? = null
        var mConfirmPasswordObservable: Observable<CharSequence>? = null
        val observable: Observable<Boolean>?

        mNameObservable = RxTextView.textChanges(binding.edtFullName)
        mContactNumberObservable = RxTextView.textChanges(binding.edtContactNumber)
        mEmailIdObservable = RxTextView.textChanges(binding.edtEmailId)
        mAddressObservable = RxTextView.textChanges(binding.edtAddress)
        mPasswordObservable = RxTextView.textChanges(binding.edtPassword)
        mConfirmPasswordObservable = RxTextView.textChanges(binding.edtConfirmPassword)



        observable = Observable.combineLatest(
                mNameObservable,
                mContactNumberObservable,
                mEmailIdObservable,
                mAddressObservable,
                mPasswordObservable,
                mConfirmPasswordObservable,
                Function6(fun(a: CharSequence, b: CharSequence, c: CharSequence, d: CharSequence, e: CharSequence, f: CharSequence): Boolean {
                    return a.isNotEmpty() && b.isNotEmpty() && c.isNotEmpty() && d.isNotEmpty() && e.isNotEmpty() && f.isNotEmpty()
                            && b.length == 10 && e.length > 5 && f.length > 5

                })
        )

        observable.subscribe(object : DisposableObserver<Boolean?>() {
            override fun onNext(aBoolean: Boolean) {
                if (aBoolean) {
                    // bottomSheet.btnOtpSubmit.updateButtonState(1)

                    binding.tvSignup.setBackgroundColor(Color.parseColor(Constants.THEME_COLOR));
                    allField = true
                } else {
                    // bottomSheet.btnOtpSubmit.updateButtonState(2)

                    binding.tvSignup.setBackgroundColor(Color.GRAY);
                }
            }

            override fun onError(e: Throwable) {}
            override fun onComplete() {}
        })
    }
}