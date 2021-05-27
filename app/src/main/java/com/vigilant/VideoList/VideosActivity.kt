package com.vigilant.VideoList

import android.content.Context
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.mssinfotech.mycity.Utility.CommonFunction
import com.mssinfotech.mycity.Utility.CommonFunction.showErrorToast
import com.mssinfotech.mycity.Utility.CommonFunction.showSuccessToast
import com.vigilant.Model.GetNewsListDTO.NewsListDTO
import com.vigilant.Network.basic.APICallback
import com.vigilant.Network.basic.BaseActivity
import com.vigilant.Network.rest.ServiceGenerator
import com.vigilant.NewsAdapter
import com.vigilant.VideoList.Model.GetAllVideosDTO
import com.vigilant.databinding.ActivityVideosBinding
import retrofit2.Response

class VideosActivity : BaseActivity() {

    private lateinit var binding : ActivityVideosBinding
var ct:Context = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideosBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.ivBack.setOnClickListener {
            finish()
        }

        callGetVideoListApi()
    }

    private fun callGetVideoListApi() {

        showLoader()
        val api = ServiceGenerator.getClient(true)
        val getvideosList = api.getAllVideos()
        getvideosList.enqueue(object : APICallback<GetAllVideosDTO>(this, true) {

            override fun onSuccess(response: Response<GetAllVideosDTO>) {
                showSuccessToast(ct,response.message() )
                val videoListAdapter = response.body()?.let { VideoListAdapter(ct, it) }
                binding.recyl.setLayoutManager(LinearLayoutManager(ct))
                binding.recyl.setAdapter(videoListAdapter)
                hideLoader()
            }

            override fun onFailed(throwable: Throwable) {
                throwable.message?.let { showErrorToast(ct, it) }
                hideLoader()
            }
        })

    }
}