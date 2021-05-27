package com.vigilant.CreateReport

import android.Manifest
import android.R.attr
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.*
import android.provider.MediaStore
import android.view.View
import android.widget.RadioGroup
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.mssinfotech.mycity.Utility.CommonFunction
import com.mssinfotech.mycity.Utility.Constants.IncidentImageArray
import com.mssinfotech.mycity.Utility.Constants.IncidentVideoArray
import com.mssinfotech.mycity.Utility.Constants.TouristImageArray
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import com.vigilant.Network.basic.BaseActivity
import com.vigilant.Network.rest.ServiceGenerator
import com.vigilant.R
import com.vigilant.UploadFile.UploadFileResponse
import com.vigilant.Utility.URIPathHelper
import com.vigilant.Utility.VigilantApplication.context
import com.vigilant.databinding.ActivityCreateReportBinding
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*


class CreateReportActivity : BaseActivity() {

    private lateinit var binding: ActivityCreateReportBinding
    var alert_mode = ""
    private var sdImageMainDirectory: File? = null
    private var outputFileUri: Uri? = null
    private val REQUEST_CAMERA = 0
    private val SELECT_FILE = 1
    private var destination: Uri? = null
    lateinit var photo: Bitmap
    var selectedImagePath = ""
    var finalpath: File? = null
    private val SELECT_VIDEO = 3
    var camera_type = ""

    private val ct: Context = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateReportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val PhotoAdapter = PhotoAdapter(this, IncidentImageArray)
        binding.recylUploadImage.setLayoutManager(LinearLayoutManager(ct, LinearLayoutManager.HORIZONTAL, false))
        binding.recylUploadImage.setAdapter(PhotoAdapter)

        val Adapter = UploadVideoAdapter(this, IncidentVideoArray)
        binding.recylUploadVideo.setLayoutManager(LinearLayoutManager(ct, LinearLayoutManager.HORIZONTAL, false))
        binding.recylUploadVideo.setAdapter(Adapter)


        val TouristPhotoAdapter = TouristPhotoAdapter(this, TouristImageArray)
        binding.recylImageTourist.setLayoutManager(LinearLayoutManager(ct, LinearLayoutManager.HORIZONTAL, false))
        binding.recylImageTourist.setAdapter(TouristPhotoAdapter)

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
        binding.ivImageUpload.setOnClickListener {
            camera_type = "camera"
            binding.rlImagePickLayout.visibility = View.VISIBLE
        }
        binding.ivImageTourist.setOnClickListener {
            camera_type = "camera_tourist"
            binding.rlImagePickLayout.visibility = View.VISIBLE
        }
        binding.ivVideo.setOnClickListener {
            camera_type = "video"
            binding.rlImagePickLayout.visibility = View.VISIBLE
        }
        binding.camera.setOnClickListener {
            click_pic()
            binding.rlImagePickLayout.setVisibility(View.GONE)
        }
        binding.gallery.setOnClickListener {
            galleryIntent()
            binding.rlImagePickLayout.setVisibility(View.GONE)
        }
        binding.crossImg.setOnClickListener { binding.rlImagePickLayout.setVisibility(View.GONE) }

        getData()
    }


    private var fileToUploadPath = ""

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {

            if (requestCode == SELECT_FILE) {
                val handler = Handler()
                handler.postDelayed({

                    try {
                        if (camera_type.contains("camera", true)) {
                            onSelectFromGalleryResult(data)
                        } else if (camera_type.contains("video", true)) {

                            val selectedVideoUri: Uri? = data?.data


                            val uriPathHelper = URIPathHelper()
                            val filePath = selectedVideoUri?.let { uriPathHelper.getPath(this, it) }
                            var f = File(filePath)
                            callUploadFileApi(f)

                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }, 1000)
            } else
                if (requestCode == SELECT_VIDEO) {


                    val Adapter = UploadVideoAdapter(this, IncidentVideoArray)
                    binding.recylUploadVideo.setLayoutManager(LinearLayoutManager(ct, LinearLayoutManager.HORIZONTAL, false))
                    binding.recylUploadVideo.setAdapter(Adapter)
                    val filebodyVideo = File(fileToUploadPath)
                    callUploadFileApi(filebodyVideo)

                } else if (requestCode == REQUEST_CAMERA) {
                    fileToUploadPath = outputFileUri.toString()
                    fileToUploadPath = fileToUploadPath.replace("file://", "")
                    val bmOptions = BitmapFactory.Options()
                    bmOptions.inJustDecodeBounds = false
                    bmOptions.inPurgeable = true
                    try {
                        val cameraBitmap = BitmapFactory.decodeFile(fileToUploadPath)
                        ImageRotation(cameraBitmap, outputFileUri!!, this@CreateReportActivity).execute()
                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                    }
                    if (camera_type.equals("camera", ignoreCase = true)) {
                        val Adapter = PhotoAdapter(this, IncidentImageArray)
                        binding.recylUploadImage.setLayoutManager(LinearLayoutManager(ct, LinearLayoutManager.HORIZONTAL, false))
                        binding.recylUploadImage.setAdapter(Adapter)


                    } else if (camera_type.equals("camera_tourist", ignoreCase = true)) {
                        val Adapter = TouristPhotoAdapter(this, TouristImageArray)
                        binding.recylImageTourist.setLayoutManager(LinearLayoutManager(ct, LinearLayoutManager.HORIZONTAL, false))
                        binding.recylImageTourist.setAdapter(Adapter)

                    }

                }

            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                val result = CropImage.getActivityResult(data)
                if (resultCode == RESULT_OK) {
                    destination = result.uri
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    val error = result.error
                }
                try {
                    if (destination != null) {
                        selectedImagePath = destination!!.path!!
                        val file = File(selectedImagePath)
                        callUploadFileApi(file)
                        /*    photo = MediaStore.Images.Media.getBitmap(this@CreateReportActivity.getContentResolver(), destination)
                            val byteArrayOutputStream = ByteArrayOutputStream()
                            val bitmap1 = Bitmap.createScaledBitmap(photo, 800, 800, true)
                            bitmap1!!.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream)
                            val byteArray = byteArrayOutputStream.toByteArray()
                            val fos = FileOutputStream(selectedImagePath)
                            fos.write(byteArray)
                            fos.close()


                            if (null != bitmap1) {
                                val userImageFile = getUserImageFile(bitmap1)
                                if (null != userImageFile) {
                                    finalpath = userImageFile
                                    callUploadFileApi(finalpath!!)
                                }
                            }
    */
                        // UploadImageManager(this).callImageInsert(requestProfile_picture)

                        //  new Upload_profile_pic().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    } else {

                    }
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun getPath(uri: Uri): String? {
        var cursor: Cursor? = null
        return try {
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            cursor = context.contentResolver.query(uri, proj, null, null, null)
            val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor!!.moveToFirst()
            cursor!!.getString(column_index)
        } finally {
            cursor?.close()
        }
    }

    fun getData() {
        val description = binding.edtDescriptin.text.toString()
        val address = binding.edtAddress.text.toString()
        val passsport_number = binding.edtPassport.text.toString()
        val tazkera_number = binding.edtTazkeraNumber.text.toString()
        val nationality = binding.edtNationality.text.toString()
        val mobile_number = binding.edtMobileNumber.text.toString()

    }


    private fun onSelectFromGalleryResult(data: Intent?) {
        var bm: Bitmap? = null
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(applicationContext.contentResolver, data.data)
                val selectedImageUri = data.data
                if (selectedImageUri != null) {
                    ImageRotation(bm, selectedImageUri, this@CreateReportActivity).execute()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }


    class ImageRotation(var bm1: Bitmap, var imageUri: Uri, var activity: Activity) : AsyncTask<String?, String?, String?>() {
        var b1: Bitmap? = null
        override fun onPreExecute() {
            // showLoader()
        }


        override fun onPostExecute(s: String?) {
            try {
                CropImage.activity(imageUri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(40, 40)
                        .start(activity)
                super.onPostExecute(s)
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
            try {
                CommonFunction.progressDialog?.dismiss()
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }

        override fun doInBackground(vararg p0: String?): String? {
            try {
                b1 = rotateImage(bm1, getCameraPhotoOrientation(getRealPathFromURI(activity, imageUri)!!))
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
            return null
        }

        fun rotateImage(b: Bitmap, angle: Int): Bitmap? {
            val matrix = Matrix()
            matrix.postRotate(angle.toFloat())
            val scaledBitmap = Bitmap.createScaledBitmap(b, b.width, b.height, true)
            return Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.width, scaledBitmap.height, matrix, true)
        }

        private fun getCameraPhotoOrientation(imagePath: String): Int {
            var rotate = 0
            try {
                val imageFile = File(imagePath)
                val exif = ExifInterface(imageFile.absolutePath)
                val orientation = exif.getAttributeInt(
                        ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_UNDEFINED)
                when (orientation) {
                    ExifInterface.ORIENTATION_NORMAL -> {
                        rotate = 0
                        rotate = 90
                    }
                    ExifInterface.ORIENTATION_ROTATE_90 -> rotate = 90
                    ExifInterface.ORIENTATION_ROTATE_270 -> rotate = 270
                    ExifInterface.ORIENTATION_ROTATE_180 -> rotate = 180
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return rotate
        }

        fun getRealPathFromURI(context: Context, contentUri: Uri?): String? {
            var cursor: Cursor? = null
            return try {
                val proj = arrayOf(MediaStore.Images.Media.DATA)
                cursor = context.contentResolver.query(contentUri!!, proj, null, null, null)
                val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                cursor.moveToFirst()
                cursor.getString(column_index)
            } finally {
                cursor?.close()
            }
        }
    }


    private fun galleryIntent() {
        if (camera_type.contains("camera")) {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT //
            startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE)
        } else
            if (camera_type.contains("video")) {
                val intent = Intent()
                intent.type = "video/*"
                intent.action = Intent.ACTION_GET_CONTENT //
                startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE)
            }

    }


    private fun click_pic() {
        val ALL_PERMISSIONS = 101
        val permissions = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)

        if (ActivityCompat.checkSelfPermission(ct, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ct, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ct, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, permissions, ALL_PERMISSIONS)
        } else {
            openCamera()


        }
    }

    private fun openCamera() {
        try {
            if (camera_type.contains("camera")) {
                val root = File(Environment
                        .getExternalStorageDirectory()
                        .toString() + File.separator + "Vigilant" + File.separator)
                root.mkdirs()
                val builder = StrictMode.VmPolicy.Builder()
                StrictMode.setVmPolicy(builder.build())
                sdImageMainDirectory = File(this.getExternalCacheDir(),
                        System.currentTimeMillis().toString() + ".jpg")
                outputFileUri = Uri.fromFile(sdImageMainDirectory)
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri)
                startActivityForResult(cameraIntent, REQUEST_CAMERA)
            } else
                if (camera_type.contains("video")) {
                    val root = File(Environment
                            .getExternalStorageDirectory()
                            .toString() + File.separator + "Vigilant" + File.separator)
                    root.mkdirs()
                    val builder = StrictMode.VmPolicy.Builder()
                    StrictMode.setVmPolicy(builder.build())
                    sdImageMainDirectory = File(this.getExternalCacheDir(),
                            System.currentTimeMillis().toString() + ".mp4")
                    outputFileUri = Uri.fromFile(sdImageMainDirectory)
                    val cameraIntent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri)
                    startActivityForResult(cameraIntent, SELECT_VIDEO)
                }

        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

  /*  private fun getUserImageFile(bitmap: Bitmap): File? {
        return try {
            val f: File = File(this@CreateReportActivity.getCacheDir(), UUID.randomUUID().toString() + "_images.png")
            f.createNewFile()
            val bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 40, bos)
            val bitmapdata = bos.toByteArray()
            val fos = FileOutputStream(f)
            fos.write(bitmapdata)
            fos.flush()
            fos.close()
            f
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            null
        }
    }*/

    lateinit var mFile: RequestBody
    private fun callUploadFileApi(file: File) {

        showLoader()

        if (camera_type.equals("camera", true)) {
            mFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
        } else if (camera_type.equals("video", true)) {
            mFile = RequestBody.create("video/*".toMediaTypeOrNull(), file)
        }
        val fileToUpload: MultipartBody.Part = MultipartBody.Part.createFormData("file", file.name, mFile)
        val api = ServiceGenerator.getClient(true)
        val uploadFile = api.uploadFile(fileToUpload)

        uploadFile.enqueue(object : Callback<UploadFileResponse?> {
            override fun onResponse(call: Call<UploadFileResponse?>, response: Response<UploadFileResponse?>) {
                var a = ""
                if (response.body() != null) {
                    a = response.body()?.data.toString()
                    if (a.equals("null", false)) {
                        if (camera_type.equals("camera", true)) {
                            IncidentImageArray.add(response.body()!!.data)
                        } else if (camera_type.equals("camera_tourist", true)) {
                            TouristImageArray.add(response.body()!!.data)

                        } else if (camera_type.equals("video", true)) {
                            IncidentVideoArray.add(response.body()!!.data)

                        }
                    }
                }
                CommonFunction.showSuccessToast(ct, a)

                hideLoader()
            }

            override fun onFailure(call: Call<UploadFileResponse?>, t: Throwable) {
                CommonFunction.showErrorToast(ct, "Upload error")
                hideLoader()
            }
        })
    }
}