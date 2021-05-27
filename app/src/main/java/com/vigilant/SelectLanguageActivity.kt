package com.vigilant

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mssinfotech.mycity.Utility.AppSession
import com.mssinfotech.mycity.Utility.Constants
import com.vigilant.Login.LoginActivity
import com.vigilant.databinding.ActivitySelectLanguageBinding
import java.util.*


class SelectLanguageActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySelectLanguageBinding
var language = "";
    val ct :Context = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectLanguageBinding.inflate(layoutInflater)
        setContentView(binding.root)


AppSession.save(ct,Constants.LANGUAGE,"english")
        setOnClick()



    }
    private fun setOnClick() {

        binding.tvNext.setOnClickListener{
           // setApplicationLanguage("bn")
            AppSession.save(ct,Constants.LANGUAGE,language)
            startActivity(Intent(this@SelectLanguageActivity, LoginActivity::class.java))

        }

        binding.rlEnglish.setOnClickListener{
            selectedLanguage(0)
            language = "english"
        }
        binding.rlPashto.setOnClickListener{
            selectedLanguage(1)
            language = "pashto"
        }
        binding.rlDari.setOnClickListener{
            selectedLanguage(2)
            language = "dari"
        }
    }

    fun setApplicationLanguage(language: String) {


        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        getApplicationContext().getResources().updateConfiguration(config, null)

        //store current language in prefrence

        //store current language in prefrence
        //prefData.setCurrentLanguage(language)

    }

    fun selectedLanguage(lng:Int){
        binding.rlEnglish.setBackgroundResource(R.drawable.grey_round_button_bg)
        binding.tvEnglish.setTextColor(resources.getColor(R.color.black))
        binding.rlPashto.setBackgroundResource(R.drawable.grey_round_button_bg)
        binding.tvPashto.setTextColor(resources.getColor(R.color.black))
        binding.rlDari.setBackgroundResource(R.drawable.grey_round_button_bg)
        binding.tvDari.setTextColor(resources.getColor(R.color.black))
        if(lng == 0){
            binding.rlEnglish.setBackgroundResource(R.drawable.theme_round_button_bg)
            binding.tvEnglish.setTextColor(resources.getColor(R.color.white))
        }
        else if(lng == 1){
            binding.rlPashto.setBackgroundResource(R.drawable.theme_round_button_bg)
            binding.tvPashto.setTextColor(resources.getColor(R.color.white))
        }
        else if(lng == 2){
            binding.rlDari.setBackgroundResource(R.drawable.theme_round_button_bg)
            binding.tvDari.setTextColor(resources.getColor(R.color.white))
        }
    }
}