package com.vigilant

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.mssinfotech.mycity.Utility.CommonFunction
import com.mssinfotech.mycity.Utility.CommonFunction.showErrorToast
import com.mssinfotech.mycity.Utility.CommonFunction.showSuccessToast
import com.vigilant.CreateReport.PhotoAdapter
import com.vigilant.Model.GetNewsListDTO.NewsListDTO
import com.vigilant.Network.basic.APICallback
import com.vigilant.Network.basic.BaseActivity
import com.vigilant.Network.rest.ServiceGenerator
import com.vigilant.Requests.SocialSignUpRequest
import com.vigilant.databinding.ActivityNewsListBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsListActivity : BaseActivity() {
    private lateinit var binding: ActivityNewsListBinding

    var ct: Context = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsListBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.ivBack.setOnClickListener {
            finish()
        }
        callGetNewsListApi()
    }

    private fun callGetNewsListApi() {

        showLoader()
        val api = ServiceGenerator.getClient(true)
        val getNewsList = api.getAllNews()
        getNewsList.enqueue(object : APICallback<NewsListDTO>(this, true) {

            override fun onSuccess(response: Response<NewsListDTO>) {
               showSuccessToast(ct, response.message())

                val newsAdapter = response.body()?.let { NewsAdapter(ct, it) }
                binding.recyl.setLayoutManager(LinearLayoutManager(ct))
                binding.recyl.setAdapter(newsAdapter)
                hideLoader()
            }

            override fun onFailed(throwable: Throwable) {
                throwable.message?.let { showErrorToast(ct, it) }
                hideLoader()
            }
        })

    }
}