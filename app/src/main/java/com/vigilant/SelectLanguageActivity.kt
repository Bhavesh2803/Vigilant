package com.vigilant

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vigilant.Login.LoginActivity
import com.vigilant.databinding.ActivitySelectLanguageBinding


class SelectLanguageActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySelectLanguageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectLanguageBinding.inflate(layoutInflater)
        setContentView(binding.root)



        setOnClick()


    }
    private fun setOnClick() {

        binding.tvNext.setOnClickListener{
            startActivity(Intent(this@SelectLanguageActivity, LoginActivity::class.java))
        }


    }
}