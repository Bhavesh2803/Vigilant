package com.vigilant

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.vigilant.databinding.ActivitySocialPageBinding

class SocialPageActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySocialPageBinding
    var ct: Context = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySocialPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val SocialPageAdapter  = SocialPageAdapter(this)
        binding.recyl.setLayoutManager(LinearLayoutManager(ct))
        binding.recyl.setAdapter(SocialPageAdapter)

        binding.ivBack.setOnClickListener {
            finish()
        }
    }
}