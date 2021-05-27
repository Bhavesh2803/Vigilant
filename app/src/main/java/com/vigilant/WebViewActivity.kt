package com.vigilant

import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.webkit.WebView
import android.webkit.WebViewClient
import com.vigilant.Network.basic.BaseActivity
import com.vigilant.databinding.ActivityWebViewBinding


class WebViewActivity : BaseActivity() {


    private lateinit var binding: ActivityWebViewBinding
    var is_loading = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.webView.webViewClient = webViewClient
        val title = intent.getStringExtra("title")
        val url = intent.getStringExtra("url")
        showLoader()

        handle_dialog.postDelayed(checkLoader, 1000)
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

    var handle_dialog = Handler()
    var checkLoader: Runnable = object : Runnable {
        override fun run() {
            try {
                if (is_loading) {
                    if (binding.webView.getProgress() == 100) {
                        hideLoader()
                        is_loading = false
                    }
                    handle_dialog.postDelayed(this, 1000)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun kill() {}
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