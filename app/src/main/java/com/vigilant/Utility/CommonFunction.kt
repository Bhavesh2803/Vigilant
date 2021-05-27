package com.mssinfotech.mycity.Utility

import android.Manifest
import android.app.Activity
import android.app.ActivityManager
import android.app.ActivityManager.RunningAppProcessInfo
import android.app.KeyguardManager
import android.app.ProgressDialog
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.*
import android.os.Build
import android.os.Environment
import android.provider.Settings
import android.text.TextUtils
import android.util.Base64
import android.util.Patterns
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import es.dmoral.toasty.Toasty
import java.io.ByteArrayOutputStream
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

/**
 * Created by Maroof Ahmed Siddique on 9/26/2016.
 */
object CommonFunction {
    private const val keys = ""
    fun getDeviceID(context: Context): String {
        var deviceID = ""
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            var permissionResult = context.checkCallingOrSelfPermission(Manifest.permission.READ_PHONE_STATE)
            if (permissionResult == PackageManager.PERMISSION_DENIED) {
                permissionResult = context.checkCallingOrSelfPermission("android.permission.READ_PRIVILEGED_PHONE_STATE")
            }
            val isPermissionGranted = permissionResult == PackageManager.PERMISSION_GRANTED
            deviceID = if (!isPermissionGranted) {
                Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
            } else {
                Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
            }
        } else {
            deviceID = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        }
        return deviceID
    }

    fun hideKeyBoard(context: Context) {
        val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
    }

    val screenWidth: Int
        get() = Resources.getSystem().displayMetrics.widthPixels
    val screenHeight: Int
        get() = Resources.getSystem().displayMetrics.heightPixels

    fun gettime(): String {
        var time = ""
        try {
            val c = Calendar.getInstance()
            val df = SimpleDateFormat("HH-mm-ss")
            time = df.format(c.time)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return time
    }

    fun getDevicewidth(ct: Context): Int {
        val displayMetrics = ct.resources.displayMetrics
        return displayMetrics.widthPixels
    }

    fun getDeviceHeight(ct: Context): Int {
        val displayMetrics = ct.resources.displayMetrics
        return displayMetrics.heightPixels
    }

    fun getdate(): String {
        var time = ""
        try {
            val c = Calendar.getInstance()
            val df = SimpleDateFormat("yyyy-MM-dd")
            time = df.format(c.time)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return time
    }

    fun getmmddyyyy_date(): String {
        var time = ""
        try {
            val c = Calendar.getInstance()
            val df = SimpleDateFormat("MM/dd/yyyy HH:mm:ss")
            time = df.format(c.time)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return time
    }

    private var current: Calendar? = null
    private var miliSeconds: Long = 0
    private var resultdate: Date? = null


    fun StringTodateEditprofile(dtStart: String): Date? {
        var date: Date? = null
        val parsedate = dtStart.substring(0, 10)
        val format = SimpleDateFormat("MM/dd/yyyy")
        try {
            date = format.parse(parsedate)
            println(date)
        } catch (e: Exception) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }
        return date
    }

    fun StringTodate(dtStart: String): Date? {
        var date: Date? = null
        val parsedate = dtStart.substring(0, 19)
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        try {
            date = format.parse(parsedate)
            println(date)
        } catch (e: Exception) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }
        return date
    }

    fun getDateToString(date: Date): String {
        var time = ""
        try {
            // Calendar c = Calendar.getInstance();
            val df = SimpleDateFormat("MM/dd/yyyy")
            time = df.format(date.time)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return time
    }

    val currentdateTime: String
        get() {
            var time = ""
            try {
                val c = Calendar.getInstance()
                val df = SimpleDateFormat("MM/dd/yyyy HH:mm")
                time = df.format(c.time)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return time
        }

    fun distance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val theta = lon1 - lon2
        var dist = (Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + (Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta))))
        dist = Math.acos(dist)
        dist = rad2deg(dist)
        dist = dist * 60 * 1.1515 * 1000
        return dist
    }

    fun deg2rad(deg: Double): Double {
        return deg * Math.PI / 180.0
    }

    fun rad2deg(rad: Double): Double {
        return rad * 180.0 / Math.PI
    }

    fun GetDDMM(dd_mm_yyyy: String): String {
        var date_time = ""
        var day = ""
        var Month = ""
        val year = ""
        val appint_array = dd_mm_yyyy.split(" ".toRegex()).toTypedArray()
        if (appint_array != null && appint_array.size > 0) {
            val date_suggestby_counsel = appint_array[0]
            val datesplitarray = date_suggestby_counsel.split("/".toRegex()).toTypedArray()
            if (datesplitarray != null && datesplitarray.size > 2) {
                day = datesplitarray[1]
                Month = datesplitarray[0]
            }
            date_time = "$day/$Month"
        }
        return date_time
    }

    fun ChangeDateFormat(mm_dd_yyyy: String): String {
        var date_time = ""
        var day = ""
        var Month = ""
        var year = ""
        val appint_array = mm_dd_yyyy.split(" ".toRegex()).toTypedArray()
        if (appint_array != null && appint_array.size > 0) {
            val date_suggestby_counsel = appint_array[0]
            val datesplitarray = date_suggestby_counsel.split("/".toRegex()).toTypedArray()
            if (datesplitarray != null && datesplitarray.size > 2) {
                day = datesplitarray[1]
                Month = datesplitarray[0]
                year = datesplitarray[2]
            }
            date_time = day + "/" + Month + "/" + year + " " + appint_array[1]
        }
        return date_time
    }

    fun GetTimerData(time: String?, CompareDate: String?): String {
        var day = ""
        try {
            val outputPattern = "MM/dd/yyyy HH:mm:ss"
            val format = SimpleDateFormat(outputPattern)
            val Date1 = format.parse(CompareDate)
            val Date2 = format.parse(time)
            val mills = Date2.time - Date1.time
            val Day1 = mills / 1000
            // long Day1 = mills / (1000 * 60 * 60);
            val remaing_sec = 86400 - Day1
            if (remaing_sec > 0) {
                val hours = remaing_sec.toInt() / (60 * 60)
                val min = (remaing_sec / 60) as Int % 60
                val sec = remaing_sec.toInt() % 60
                var hour_str = "" + hours
                var min_str = "" + min
                var sec_str = "" + sec
                if (hours < 10) hour_str = "0$hours"
                if (min < 10) min_str = "0$min"
                if (sec < 10) sec_str = "0$sec"
                day = "$hour_str:"
                day = "$day$min_str:"
                day = day + "" + sec_str
            }


            /* if (day < 0)
                day = 0;*/
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return day
    }

    fun CheckPlanExpiry(time: String?, CompareDate: String?): Int {
        var day = 0
        try {
            val outputPattern = "yyyy-MM-dd"
            val format = SimpleDateFormat(outputPattern)
            val Date1 = format.parse(CompareDate)
            val Date2 = format.parse(time)
            val mills = Date2.time - Date1.time
            val Day1 = mills / (1000 * 60 * 60)
            day = Day1.toInt() / 24


            /* if (day < 0)
                day = 0;*/
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return day
    }

    fun Getdatediff(time: String?, CompareDate: String?): Int {
        var day = 0
        try {
            val outputPattern = "MM/dd/yyyy HH:mm:ss"
            val format = SimpleDateFormat(outputPattern)
            val Date1 = format.parse(CompareDate)
            val Date2 = format.parse(time)
            val mills = Date2.time - Date1.time
            val Day1 = mills / (1000 * 60 * 60)
            day = Day1.toInt() / 24


            /* if (day < 0)
                day = 0;*/
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return day
    }

    fun isSpecialChar(word: String?): Boolean {
        val special = Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]")
        val hasSpecial = special.matcher(word)
        return hasSpecial.find()
    }

    fun isUpperCase(word: String?): Boolean {
        val letter = Pattern.compile("[A-Z]")
        val hasLetter = letter.matcher(word)
        return hasLetter.find()
    }

    fun isNumber(word: String?): Boolean {
        val digit = Pattern.compile("[0-9]")
        val hasDigit = digit.matcher(word)
        return hasDigit.find()
    }

    /* public static void fetchTriggerWords()
    {

        ParseQuery<ParseObject> fetchTrigger = ParseQuery.getQuery("keywords");
        fetchTrigger.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (objects != null && objects.size() > 0) {
                    ParseObject mFetchObject = objects.get(0);
                    keys = mFetchObject.getString("keys");
                }
            }
        });
    }*/
    fun isValidEmail(target: CharSequence?): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    fun is_numeric(msg: String): Boolean {
        var is_num = false
        for (i in 0..9) {
            if (msg.contains("" + i)) {
                is_num = true
                break
            }
        }
        return is_num
    }

    const val TODAYS_STEPS = "todays_steps"
    fun gettodaysstep(context: Context): Int {
        var restoredText = 0
        try {
            val prefs = context.getSharedPreferences("Silent", Context.MODE_PRIVATE)
            restoredText = prefs.getInt(TODAYS_STEPS, 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return restoredText
    }

    fun storertodaystep(context: Context, steps: Int) {
        try {
            val editor = context.getSharedPreferences("Silent", Context.MODE_PRIVATE).edit()
            editor.putInt(TODAYS_STEPS, steps)
            editor.commit()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    const val TODAYS_RECOMONDED_STEP = "todays_recomonded_steps"
    fun gettodaysrecomndedstep(context: Context): Int {
        var restoredText = 0
        try {
            val prefs = context.getSharedPreferences("Silent", Context.MODE_PRIVATE)
            restoredText = prefs.getInt(TODAYS_RECOMONDED_STEP, 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return restoredText
    }

    fun settodaysrecomndedstep(context: Context, steps: Int) {
        try {
            val editor = context.getSharedPreferences("Silent", Context.MODE_PRIVATE).edit()
            editor.putInt(TODAYS_RECOMONDED_STEP, steps)
            editor.commit()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /*final static String LOCAL_LATLANG = "local_lat_lang";
    public static int GetLatLang(Context context)
    {
        int restoredText = 0;
        try
        {
            SharedPreferences prefs = context.getSharedPreferences("Silent", context.MODE_PRIVATE);
            restoredText = prefs.getInt(LOCAL_LATLANG, 0);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return restoredText;
    }

    public static void SetLatLang(Context context,int steps)
    {
        try
        {
            SharedPreferences.Editor editor = context.getSharedPreferences("Silent", context.MODE_PRIVATE).edit();
            editor.putInt(LOCAL_LATLANG,steps);
            editor.commit();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }*/
    fun Getdiffbettwodate(newtime: String?, oldtime: String?): Int {
        var day = 0
        try {
            val outputPattern = "MM/dd/yyyy"
            val format = SimpleDateFormat(outputPattern)
            val Date1 = format.parse(newtime)
            val Date2 = format.parse(oldtime)
            val mills = Date1.time - Date2.time
            val Day1 = mills / (1000 * 60 * 60)
            day = Day1.toInt() / 24
            if (day < 0) day = 0
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return day
    }

    const val TARGET_PUSH_DATE = "target_push_date"
    fun GetTargetPushDate(context: Context): Int {
        var restoredText = 0
        try {
            val prefs = context.getSharedPreferences("Silent", Context.MODE_PRIVATE)
            restoredText = prefs.getInt(TARGET_PUSH_DATE, 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return restoredText
    }

    fun SetTargetPushDate(context: Context, steps: Int) {
        try {
            val editor = context.getSharedPreferences("Silent", Context.MODE_PRIVATE).edit()
            editor.putInt(TARGET_PUSH_DATE, steps)
            editor.commit()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }







    fun StringtoDate(stringdate: String?): Date? {
        var date: Date? = null
        val format = SimpleDateFormat("MM/dd/yyyy HH:mm")
        try {
            date = format.parse(stringdate)
            println(date)
        } catch (e: Exception) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }
        return date
    }

    fun getCroppedBitmap(bitmap: Bitmap): Bitmap {
        val output: Bitmap

        /*if (bitmap.getWidth() > bitmap.getHeight()) {
            output = Bitmap.createBitmap(bitmap.getHeight(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        } else {
            output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getWidth(), Bitmap.Config.ARGB_8888);
        }*/output = Bitmap.createBitmap(bitmap.width, bitmap.width, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)
        val color = -0xbdbdbe
        val paint = Paint()
        val rect = Rect(0, 0, bitmap.width, bitmap.height)
        var r = 0f
        r = (bitmap.width / 2).toFloat()
        paint.isAntiAlias = true
        canvas.drawARGB(0, 0, 0, 0)
        paint.color = color
        canvas.drawCircle(r, r, r, paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(bitmap, rect, rect, paint)
        return output
    }

    fun BitMapToString(bitmap: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 30, baos)
        val b = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }

    fun StringToBitMap(encodedString: String?): Bitmap? {
        return try {
            val encodeByte = Base64.decode(encodedString, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
        } catch (e: Exception) {
            e.message
            null
        }
    }

    fun get_bitmap_image(path: String?): Bitmap? {
        var bmp: Bitmap? = null
        try {
            val options = BitmapFactory.Options()
            bmp = BitmapFactory.decodeFile(path, options)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return bmp
    }

    //Path = "/storage/sdcard0/Android/data/com.connectmore.ActivityClasses/m-AdCall";
    //ShowLog("CollectionFunctions", "path :: "+Path);
    val intenalSDCardPath: String
        get() {
            var Path = ""
            Path = "/storage/sdcard1/Android/data/com.helio.silentsecret/Cypher"
            var fPath: File? = File(Path)
            if (null != fPath) {
                if (!fPath.exists()) {
                    Path = ""
                    //Path = "/storage/sdcard0/Android/data/com.connectmore.ActivityClasses/m-AdCall";
                    Path = "/storage/sdcard0/Android/data/com.helio.silentsecret/Cypher"
                    fPath = null
                    fPath = File(Path)
                    if (!fPath.exists()) {
                        val extStorageDirectory = Environment.getExternalStorageDirectory().toString()
                        val TARGET_BASE_PATH = extStorageDirectory + File.separator +
                                "Android" + File.separator + "data" + File.separator + "com.helio.silentsecret" + File.separator +
                                "Cypher"
                        fPath = null
                        fPath = File(TARGET_BASE_PATH)
                        fPath.mkdirs()
                        Path = TARGET_BASE_PATH
                    }
                }
            }
            fPath = null
            //ShowLog("CollectionFunctions", "path :: "+Path);
            return Path
        }




    var PI = 3.14159265
    var TWOPI = 2 * PI
    fun coordinate_is_inside_polygon(
            latitude: Double, longitude: Double,
            lat_array: ArrayList<Double>, long_array: ArrayList<Double>): Boolean {
        var i: Int
        var angle = 0.0
        var point1_lat: Double
        var point1_long: Double
        var point2_lat: Double
        var point2_long: Double
        val n = lat_array.size
        i = 0
        while (i < n) {
            point1_lat = lat_array[i] - latitude
            point1_long = long_array[i] - longitude
            point2_lat = lat_array[(i + 1) % n] - latitude
            //you should have paid more attention in high school geometry.
            point2_long = long_array[(i + 1) % n] - longitude
            angle += Angle2D(point1_lat, point1_long, point2_lat, point2_long)
            i++
        }
        return if (Math.abs(angle) < PI) false else true
    }

    fun showErrorToast(ct:Context,message:String){
        Toasty.error(ct, message, Toast.LENGTH_SHORT).show()
    }
    fun showSuccessToast(ct:Context,message:String){
        Toasty.success(ct, message, Toast.LENGTH_SHORT).show()
    }
    fun showInfoToast(ct:Context,message:String){
        Toasty.info(ct, message, Toast.LENGTH_SHORT).show()
    }
    fun Angle2D(y1: Double, x1: Double, y2: Double, x2: Double): Double {
        var dtheta: Double
        val theta1: Double
        val theta2: Double
        theta1 = Math.atan2(y1, x1)
        theta2 = Math.atan2(y2, x2)
        dtheta = theta2 - theta1
        while (dtheta > PI) dtheta -= TWOPI
        while (dtheta < -PI) dtheta += TWOPI
        return dtheta
    }

    var progressDialog: ProgressDialog? = null
    fun cancelProgress() {
        try {
            if (progressDialog != null) {
                progressDialog!!.cancel()
                progressDialog!!.dismiss()
                progressDialog = null
            }
        } catch (ex: IllegalArgumentException) {
            ex.printStackTrace()
        }
    }

    fun isServiceRunning(mContext: Context, serviceClass: Class<*>): Boolean {
        val manager = mContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Int.MAX_VALUE)) {
            if (serviceClass.name == service.service.className && service.pid != 0) {
                //ShowLog("", "ser name "+serviceClass.getName());
                return true
            }
        }
        return false
    }

    fun isAppInForeground(context: Context): Boolean {
        try {
            return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
                val foregroundTaskInfo = am.getRunningTasks(1)[0]
                val foregroundTaskPackageName = foregroundTaskInfo.topActivity!!.packageName
                foregroundTaskPackageName.toLowerCase() == context.packageName.toLowerCase()
            } else {
                val appProcessInfo = RunningAppProcessInfo()
                ActivityManager.getMyMemoryState(appProcessInfo)
                if (appProcessInfo.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND || appProcessInfo.importance == RunningAppProcessInfo.IMPORTANCE_VISIBLE) {
                    return true
                }
                val km = context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
                // App is foreground, but screen is locked, so show notification
                km.inKeyguardRestrictedInputMode()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return true
    }
}