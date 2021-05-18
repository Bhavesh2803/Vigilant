package com.vigilant.CreateReport

import android.content.Context
import android.os.Bundle
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.vigilant.R
import com.vigilant.VideoAdapter
import com.vigilant.databinding.ActivityCreateReportBinding

class CreateReportActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateReportBinding
    var alert_mode = ""
   private val ct: Context = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateReportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val PhotoAdapter = PhotoAdapter(this)
        binding.recylUploadImage.setLayoutManager(LinearLayoutManager(ct, LinearLayoutManager.HORIZONTAL, false))
        binding.recylUploadImage.setAdapter(PhotoAdapter)

        val Adapter = UploadVideoAdapter(this)
        binding.recylUploadVideo.setLayoutManager(LinearLayoutManager(ct, LinearLayoutManager.HORIZONTAL, false))
        binding.recylUploadVideo.setAdapter(Adapter)


        binding.recylImageTourist.setLayoutManager(LinearLayoutManager(ct, LinearLayoutManager.HORIZONTAL, false))
        binding.recylImageTourist.setAdapter(PhotoAdapter)

        binding.alertTypeRadio.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.radio_high) {
                alert_mode = "High"
            } else if (checkedId == R.id.radio_medium) {
                alert_mode = "Medium"
            } else if (checkedId == R.id.radio_low) {
                alert_mode = "Low"
            }

        })

        binding.ivBack.setOnClickListener {
            finish()
        }

        getData()
    }


    fun getData() {
        val description = binding.edtDescriptin.text.toString()
        val address = binding.edtAddress.text.toString()
        val passsport_number = binding.edtPassport.text.toString()
        val tazkera_number = binding.edtTazkeraNumber.text.toString()
        val nationality = binding.edtNationality.text.toString()
        val mobile_number = binding.edtMobileNumber.text.toString()

    }
}