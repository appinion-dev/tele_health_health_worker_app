package com.aah.sftelehealthworker.common

import android.app.Activity
import android.app.ProgressDialog
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import com.aah.sftelehealthworker.R
import com.aah.sftelehealthworker.utils.AppUtils

open class BaseFragment : Fragment() {
    private var progressDialog: ProgressDialog? = null
    var mActivity: Activity? = null
    private var mHandler: Handler? = null

    open fun showProgressDialog() {
        mActivity!!.runOnUiThread(Runnable {
            progressDialog =
                ProgressDialog.show(mActivity, null, getString(R.string.please_wait), true)
        })
    }


    open fun showProgressDialog(msg: String) {
        mActivity!!.runOnUiThread(Runnable {
            progressDialog =
                ProgressDialog.show(mActivity, null, msg, true)
        })
    }

    open fun hideProgressDialog() {
        mHandler = Handler(Looper.getMainLooper())
        mHandler!!.postDelayed({
            if (progressDialog != null && progressDialog!!.isShowing()) {
                mActivity!!.runOnUiThread(Runnable { progressDialog!!.dismiss() })
            }
        }, 1000)
    }
    open fun hideProgressDialogVital() {

            if (progressDialog != null && progressDialog!!.isShowing()) {
                mActivity!!.runOnUiThread(Runnable { progressDialog!!.dismiss() })
            }

    }


    override fun onStop() {
        if (mHandler != null)
            mHandler!!.removeCallbacksAndMessages(null)
        super.onStop()
    }

}