package com.vigilant

import android.R.attr.name
import android.graphics.Bitmap
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.vigilant.databinding.ActivityWebViewBinding


class WebViewActivity : AppCompatActivity() {


    private lateinit var binding: ActivityWebViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.webView.webViewClient = webViewClient
        val title = intent.getStringExtra("title")
        val url = intent.getStringExtra("url")

        if(title != ""){

            binding.tvHeaderText.text = title
        }
        if (url != "") {
            url?.let { binding.webView.loadUrl(it) }
        }

        binding.ivBack.setOnClickListener{
            finish()
        }
    }

    private val webViewClient = object : WebViewClient() {
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
          //  ProgressUtil.showLoading(this@WebViewActivity)
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
           // ProgressUtil.hideLoading()
        }
    }
}