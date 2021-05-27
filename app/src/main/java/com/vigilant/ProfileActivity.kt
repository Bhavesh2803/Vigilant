package com.vigilant

import android.Manifest
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
import android.os.StrictMode.VmPolicy
import android.provider.MediaStore
import android.view.View
import androidx.core.app.ActivityCompat
import com.mssinfotech.mycity.Utility.CommonFunction.progressDialog
import com.mssinfotech.mycity.Utility.CommonFunction.showErrorToast
import com.mssinfotech.mycity.Utility.CommonFunction.showSuccessToast
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE
import com.theartofdev.edmodo.cropper.CropImageView
import com.vigilant.Network.basic.BaseActivity
import com.vigilant.Network.basic.ProgressDialog
import com.vigilant.Network.rest.ServiceGenerator
import com.vigilant.UploadFile.UploadFileResponse
import com.vigilant.databinding.ActivityProfileBinding
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


class ProfileActivity : BaseActivity() {
    private lateinit var binding: ActivityProfileBinding

    var selectedImagePath = ""
    var finalpath: File? = null
    private var outputFileUri: Uri? = null
    private val EXTERNAL_STORAGE_PERMISSION_CONSTANT = 100
    private val CAMERA_PERMISSION_CONSTANT = 102
    private val REQUEST_CAMERA = 0
    private val SELECT_FILE = 1
    private var destination: Uri? = null
    lateinit var photo: Bitmap

    private var sdImageMainDirectory: File? = null


    val ct: Context = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener {
            finish()
        }
        binding.civProfile.setOnClickListener { binding.imagePickLayout.setVisibility(View.VISIBLE) }

        binding.camera.setOnClickListener { click_pic()
            binding.imagePickLayout.setVisibility(View.GONE) }
        binding.gallery.setOnClickListener { galleryIntent()
            binding.imagePickLayout.setVisibility(View.GONE) }
        binding.crossImg.setOnClickListener {  binding.imagePickLayout.setVisibility(View.GONE)  }
    }


    private var fileToUploadPath = ""
    private var progressDialog: ProgressDialog? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        progressDialog = ProgressDialog(this@ProfileActivity)
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_FILE) {

                //  CommonFunction.showProgressDialog(ct, "Loading");
                // showLoader()
                val handler = Handler()
                handler.postDelayed({ onSelectFromGalleryResult(data) }, 1000)
            } else if (requestCode == REQUEST_CAMERA) {
                try {
                    fileToUploadPath = outputFileUri.toString()
                    fileToUploadPath = fileToUploadPath.replace("file://", "")
                    val bmOptions = BitmapFactory.Options()
                    bmOptions.inJustDecodeBounds = false
                    bmOptions.inPurgeable = true
                    try {
                        val cameraBitmap = BitmapFactory.decodeFile(fileToUploadPath)
                        ImageRotation(cameraBitmap, outputFileUri!!, this@ProfileActivity).execute()
                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                    }

                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
            }
        }

        if (requestCode == CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == RESULT_OK) {
                destination = result.uri
                //  destination = result.getUri().getPath();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
            }
            try {
                if (destination != null) {
                    selectedImagePath = destination!!.path!!
                    photo = MediaStore.Images.Media.getBitmap(this@ProfileActivity.getContentResolver(), destination)
                    binding.civProfile.setImageBitmap(photo)
                    val file = File(selectedImagePath)
                    callUploadFileApi(file)
                  /*  val byteArrayOutputStream = ByteArrayOutputStream()
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
                    }*/

                    // UploadImageManager(this).callImageInsert(requestProfile_picture)

                    //  new Upload_profile_pic().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                } else {

                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun onSelectFromGalleryResult(data: Intent?) {
        var bm: Bitmap? = null
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(applicationContext.contentResolver, data.data)
                val selectedImageUri = data.data
                if (selectedImageUri != null) {
                    ImageRotation(bm, selectedImageUri, this@ProfileActivity).execute()
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
                progressDialog?.dismiss()
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
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT //
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE)
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
            val root = File(Environment
                    .getExternalStorageDirectory()
                    .toString() + File.separator + "Vigilant" + File.separator)
            root.mkdirs()
            val builder = VmPolicy.Builder()
            StrictMode.setVmPolicy(builder.build())
            sdImageMainDirectory = File(this.getExternalCacheDir(),
                    System.currentTimeMillis().toString() + ".jpg")
            outputFileUri = Uri.fromFile(sdImageMainDirectory)
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri)
            startActivityForResult(cameraIntent, REQUEST_CAMERA)


        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    private fun getUserImageFile(bitmap: Bitmap): File? {
        return try {
            val f: File = File(this@ProfileActivity.getCacheDir(), UUID.randomUUID().toString() + "_images.png")
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
    }

    private fun callUploadFileApi(file:File) {



        val mFile: RequestBody = RequestBody.create("image/*".toMediaTypeOrNull(), file)
        val fileToUpload: MultipartBody.Part = MultipartBody.Part.createFormData("file", file.name, mFile)
        val api = ServiceGenerator.getClient(true)
        val uploadFile = api.uploadFile(fileToUpload)

        uploadFile.enqueue(object : Callback<UploadFileResponse?> {
            override fun onResponse(call: Call<UploadFileResponse?>, response: Response<UploadFileResponse?>) {
                showSuccessToast(ct,"Uploaded")
            }

            override fun onFailure(call: Call<UploadFileResponse?>, t: Throwable) {
                showErrorToast(ct,"Upload error")
            }
        })
    }
}