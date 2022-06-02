package com.aah.sftelehealthworker.utils

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Color
import android.location.Location
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.aah.sftelehealthworker.R
import com.aah.sftelehealthworker.models.ResponseModel
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

object AppUtils {
    private const val TAG = "AppUtils"

    fun log(tag: String, msg: String) {
        Log.d(tag, msg)
    }

    fun millisToDateString(millis: String?): String {
        return millisLongToDateString(java.lang.Long.valueOf(millis))
    }

    fun millisLongToDateString(millis: Long): String {
        val d = Date()
        d.time = millis
        val dateFormat: DateFormat =
            SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        return dateFormat.format(d)
    }

    fun millisToDateString(
        millis: Long,
        pattern: String?,
        language: String?
    ): String {
        val d = Date()
        d.time = millis
        val dateFormat: DateFormat = SimpleDateFormat(pattern, Locale(language))
        return dateFormat.format(d)
    }

    val todayMillis: Long
        get() {
            val d = Date()
            val dateFormat: DateFormat =
                SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            val date = dateFormat.format(d)
            try {
                return dateFormat.parse(date).time
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return 0L
        }

    //    public static MaterialDialog.Builder dialog(Context context, String title, String body, boolean isCancelable) {
    //        return new MaterialDialog.Builder(context)
    //                .title(title)
    //                .titleColor(ContextCompat.getColor(context, R.color.colorPrimary))
    //                .cancelable(isCancelable)
    //                .content(body);
    //    }
    fun hideSoftInput(activity: Activity) {
        val view =
            activity.findViewById<View>(android.R.id.content)
        if (view != null) {
            val imm =
                activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    ?: return
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var activeNetworkInfo: NetworkInfo? = null
        if (connectivityManager != null) {
            activeNetworkInfo = connectivityManager.activeNetworkInfo
        }
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    fun visibility(visibility: Boolean): Int {
        return if (visibility) View.VISIBLE else View.GONE
    }

    fun hasPermissions(
        context: Context?,
        vararg permissions: String?
    ): Boolean {
        if (context != null && permissions != null) {
            for (permission in permissions) {
                if (ActivityCompat.checkSelfPermission(
                        context,
                        permission!!
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return false
                }
            }
        }
        return true
    }

    fun message(
        view: View?,
        msg: String?,
        textColor: Int,
        backgroundColor: Int
    ) {
        if (view == null) return
        val snack = Snackbar.make(view, msg!!, Snackbar.LENGTH_SHORT)
        val snackBarView = snack.view
        snackBarView.setBackgroundColor(backgroundColor)
        val snackBarText = snackBarView.findViewById<TextView>(R.id.snackbar_text)
        snackBarText.setTextColor(textColor)
        snack.show()
    }

    fun message(
        view: View?,
        msg: String?,
        textColor: Int,
        backgroundColor: Int,
        duration: Int
    ) {
        if (view == null) return
        val snack = Snackbar.make(view, msg!!, Snackbar.LENGTH_SHORT)
        val snackBarView = snack.view
        snackBarView.setBackgroundColor(backgroundColor)
        val snackBarText = snackBarView.findViewById<TextView>(R.id.snackbar_text)
        snackBarText.setTextColor(textColor)
        snack.duration = duration
        snack.show()
    }

    fun message(
        view: View?,
        msg: String?,
        context: Context?
    ) {
        try {
            if (view == null) return
            val snack = Snackbar.make(view, msg!!, Snackbar.LENGTH_SHORT)
            val snackBarView = snack.view
            snackBarView.setBackgroundColor(
                ContextCompat.getColor(context!!, R.color.colorPrimary)
            )
            val snackBarText = snackBarView.findViewById<TextView>(R.id.snackbar_text)
            snackBarText.setTextColor(Color.WHITE)
            snack.show()
        } catch (e: Exception) {
            return
        }
    }


    fun dateToMillisecond(dateStr: String?): String {
        val dateFormat: DateFormat =
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return try {
            dateFormat.parse(dateStr).time.toString()
        } catch (e: ParseException) {
            e.printStackTrace()
            "0"
        }
    }

    fun changeDateFormat(date: String?): String {
        val datePickerFormat = SimpleDateFormat("dd MMM, yyyy", Locale.US)
        val myFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        try {
            val dateFromUser = datePickerFormat.parse(date)
            return myFormat.format(dateFromUser)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return "0"
    }

    fun changeDateFormat(date: String?, existingFormat: String, newFormat: String): String {

        val datePickerFormat = SimpleDateFormat(existingFormat, Locale.US)
        val myFormat = SimpleDateFormat(newFormat, Locale.getDefault())
        try {
            val dateFromUser = datePickerFormat.parse(date)
            val cal = Calendar.getInstance()
            cal.time = dateFromUser
            cal.add(Calendar.HOUR, 6);
            return myFormat.format(cal.time)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return "0"
    }

    fun changeDateFormatGMT(date: String?, existingFormat: String, newFormat: String): String {

        val datePickerFormat = SimpleDateFormat(existingFormat, Locale.US)
        val myFormat = SimpleDateFormat(newFormat, Locale.getDefault())
        try {
            val dateFromUser = datePickerFormat.parse(date)
            val cal = Calendar.getInstance()
            cal.time = dateFromUser
            cal.add(Calendar.HOUR, 6);
            return myFormat.format(cal.time)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return "0"
    }

    fun getPercentage(numberOfGiven: Int, total: Int): String {
        return if (numberOfGiven != 0 && total != 0) {
            val percentage = numberOfGiven * 100 / total
            percentage.toString()
        } else {
            0.toString()
        }

    }


    val todayAndTimeMillis: Long
        get() {
            val d = Date()
            val dateFormat: DateFormat =
                SimpleDateFormat("dd-MM-yyyy hh:mm:ss a", Locale.getDefault())
            val date = dateFormat.format(d)
            try {
                return dateFormat.parse(date).time
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return 0L
        }

    fun substringTextColor(
        text: String?,
        startPosition: Int,
        endPosition: Int,
        color: Int
    ): SpannableString {
        val spannableString = SpannableString(text)
        val foregroundColorSpan = ForegroundColorSpan(color)
        spannableString.setSpan(
            foregroundColorSpan,
            startPosition,
            endPosition,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return spannableString
    }

    @Throws(RuntimeException::class)
    fun getDistanceBetween(
        startLatitude: String?,
        startLongitude: String?,
        endLatitude: String?,
        endLongitude: String?,
        distance: Int
    ): Boolean {
        if (startLatitude == null || startLongitude == null || endLatitude == null || endLongitude == null) return false
        val result = FloatArray(1)
        Location.distanceBetween(
            startLatitude.toDouble(),
            startLongitude.toDouble(),
            endLatitude.toDouble(),
            endLongitude.toDouble(),
            result
        )
        return result[0] / distance <= 1
    }

    fun customAlertDialog(
        context: Context?,
        title: String?,
        body: String?,
        isCancelable: Boolean
    ): AlertDialog.Builder {
        return AlertDialog.Builder(context!!)
            .setTitle(title)
            .setCancelable(isCancelable)
            .setMessage(body)
    }

    //    public static MaterialDialog showProgressDialog(Context context, String title, String body) {
    //        return new MaterialDialog.Builder(context)
    //                .title(title)
    //                .cancelable(false)
    //                .content(body)
    //                .progress(true, 1)
    //                .build();
    //    }
    fun dpToPx(dp: Int): Int {
        return (dp * Resources.getSystem().displayMetrics.density).toInt()
    }

    //    public static String digitFormat(String s) {
    //        double d = 0.0;
    //        DecimalFormat df = new DecimalFormat("0.00");
    //        if (s != null && !s.equals("")) {
    //            d = Double.parseDouble(s);
    //        }
    //
    //        if (d == (long) d)
    //            return String.format("%s", (long) d);
    //        else
    //            return String.format("%s", df.format(d));
    //    }
    fun getYoutubeVideoId(youtubeUrl: String?): String {
        var video_id = ""
        if (youtubeUrl != null && youtubeUrl.trim { it <= ' ' }.length > 0 && youtubeUrl.startsWith(
                "http"
            )
        ) {
            val expression =
                "^.*((youtu.be" + "\\/)" + "|(v\\/)|(\\/u\\/w\\/)|(embed\\/)|(watch\\?))\\??v?=?([^#\\&\\?]*).*" // var regExp = /^.*((youtu.be\/)|(v\/)|(\/u\/\w\/)|(embed\/)|(watch\?))\??v?=?([^#\&\?]*).*/;
            val input: CharSequence = youtubeUrl
            val pattern = Pattern.compile(
                expression,
                Pattern.CASE_INSENSITIVE
            )
            val matcher = pattern.matcher(input)
            if (matcher.matches()) {
                val groupIndex1 = matcher.group(7)
                if (groupIndex1 != null && groupIndex1.length == 11) video_id = groupIndex1
            }
        }
        return video_id
    }

    fun MillisectoDate(millis: Long, format: String): String {
        val d = Date()
        d.time = millis
        val dateFormat: DateFormat = SimpleDateFormat(format, Locale.getDefault())
        return dateFormat.format(d)
    }

    //    @SuppressLint("SimpleDateFormat")
    //    public static File imageToFile(Bitmap image) {
    //        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    //        String imageFileName = "sajida_field_force_" + timeStamp + ".jpg";
    //
    //        File direct = new File(DIRECTORY+"image/");
    //
    //        if (!direct.exists()) {
    //            File wallpaperDirectory = new File(DIRECTORY+"image/");
    //            wallpaperDirectory.mkdirs();
    //        }
    //
    //        File file = new File(new File(DIRECTORY+"image/"), imageFileName);
    //
    //        if (file.exists()) {
    //            file.delete();
    //        }
    //
    //        try {
    //            FileOutputStream out = new FileOutputStream(file);
    //            image.compress(Bitmap.CompressFormat.JPEG, 75, out);
    //            out.flush();
    //            out.close();
    //            Log.d(TAG, "moneyReceiptFile path: " + file.getAbsolutePath());
    //            return new File(file.getAbsolutePath());
    //        } catch (Exception e) {
    //            e.printStackTrace();
    //            return null;
    //        }
    //    }
    fun preventTwoClick(view: View) {
        view.isEnabled = false
        view.postDelayed({ view.isEnabled = true }, 500)
    }

    fun parseErrorResponse(errorBody: String): ResponseModel {
        try {
            return Gson().fromJson(errorBody, ResponseModel::class.java)
        } catch (e: Exception) {
        }
        return ResponseModel("Something went wrong", 500)

    }


}