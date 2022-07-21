package com.aah.sftelehealthworker.ui

import android.Manifest
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.aah.sftelehealthworker.R
import com.aah.sftelehealthworker.databinding.MainActivityBinding
import com.aah.sftelehealthworker.ui.home.HomeFragment
import com.aah.sftelehealthworker.utils.AppUtils
import gun0912.tedimagepicker.builder.TedImagePicker


class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding
    var alertDialog: AlertDialog? = null
    //    private lateinit var navController: NavController
    private val PERMISSIONS_STORAGE = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA
    )

    private val PERMISSION_STORAGE_CODE = 120

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.main_activity)
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity)

//        navController = Navigation.findNavController(view)

//        if (savedInstanceState == null) {
//            supportFragmentManager.beginTransaction()
//                    .replace(R.id.container, LoginFragment.newInstance())
//                    .commitNow()
//        }


        initToolbar()
    }

    private fun initToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

//        val navController = Navigation.findNavController(binding.root)
//        val appBarConfiguration = AppBarConfiguration(navController.graph)
//        toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    private fun showSingleImage(uri: Uri) {

        AppUtils.message(binding.root,  "path :" + uri.encodedPath,this)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun getImage(){
//        TedImagePicker.with(this)
//            .start { uri -> showSingleImage(uri) }
    }

    fun checkReadWritePermission(): Boolean {
        if (Build.VERSION.SDK_INT < 23) {
            return true
        }
        val checkPermission = ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        val checkCameraPermission = ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        )
        return if (checkPermission == PackageManager.PERMISSION_GRANTED && checkCameraPermission == PackageManager.PERMISSION_GRANTED) {
            true
        } else {
            ActivityCompat.requestPermissions(
                this,
                PERMISSIONS_STORAGE,
                PERMISSION_STORAGE_CODE
            )
            false
        }
    }


    override fun onStop() {
        super.onStop()
    }

    override fun onBackPressed() {
        val navHost = supportFragmentManager.findFragmentById(R.id.fragment)
        navHost?.let { navFragment ->
            navFragment.childFragmentManager.primaryNavigationFragment?.let { fragment ->
                if (fragment is HomeFragment) {
                    createDialog()
                } else {
                    super.onBackPressed()
                }
            }
        }

       // createDialog()
    }
    fun createDialog() {

        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setMessage("Are you sure you want to exit?")
        alertDialogBuilder.setPositiveButton("Yes") { _: DialogInterface, _: Int ->
            finish()
        }
        alertDialogBuilder.setNegativeButton(
            "Cancel",
            { dialogInterface: DialogInterface, i: Int -> })

        alertDialog = alertDialogBuilder.create()
        alertDialog?.show()
    }

}