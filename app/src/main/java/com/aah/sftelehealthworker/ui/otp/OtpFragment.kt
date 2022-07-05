package com.aah.sftelehealthworker.ui.otp

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.aah.sftelehealthworker.R
import com.aah.sftelehealthworker.databinding.OtpFragmentBinding
import com.aah.sftelehealthworker.utils.AppUtils
import com.aah.sftelehealthworker.utils.OTP
import com.aah.sftelehealthworker.utils.PHONE_NO
import kotlinx.android.synthetic.main.otp_fragment.view.*


class OtpFragment : Fragment() {

    private lateinit var viewModel: OtpViewModel
    private lateinit var binding: OtpFragmentBinding
    private lateinit var nav: NavController
    private var phoneNo: String = ""
    private var otpString: String = ""
    private val waitingTime = 10000L

//    ENTER OTP SENT TO

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        val view = inflater.inflate(R.layout.otp_fragment, container, false)
        binding = DataBindingUtil.inflate(inflater, R.layout.otp_fragment, container, false)
        viewModel = ViewModelProvider(this).get(OtpViewModel::class.java)

//        phoneNo = arguments?.getString(PHONE_NO).toString()
//        otpString = arguments?.getString(OTP).toString()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        viewModel = ViewModelProvider(this).get(OtpViewModel::class.java)

        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        nav = Navigation.findNavController(view)
//        countDown(waitingTime)
//        initResendOtp()

//        val phoneNoText = "ENTER OTP SENT TO $phoneNo"
//        binding.mobileNo.text = phoneNoText
//        binding.otp.setText(otpString)

        view.next.setOnClickListener {
            viewModel.loadData(binding.mobileNo.text.toString(), "111111")
            hideKeyboard()
        }

        viewModel.isSuccessFull.observe(viewLifecycleOwner, Observer {
            if (it)
                gotoHome()
        })
        viewModel.message.observe(viewLifecycleOwner, Observer {
            AppUtils.message(view, it, Color.WHITE, Color.GRAY)
        })
    }

//    private fun initResendOtp() {
//        viewModel.otpLiveData.observe(viewLifecycleOwner, Observer { otpModel ->
//            if(otpModel.otp != null){
//                binding.otp.setText(otpModel.otp.toString())
//            }
//        })
//    }

    private fun gotoHome() {

        nav.navigate(R.id.action_otpFragment_to_homeFragment)
//        Navigation.findNavController(view).navigate(R.id.action_otpFragment_to_homeFragment)
    }

//    fun countDown(waitingTime: Long) {
//        object : CountDownTimer(waitingTime, 1000) {
//            override fun onTick(millisUntilFinished: Long) {
//
//                var millis = millisUntilFinished / 1000
//                if(millis > 0L) {
//                val waitingTime = "${millis}  s"
//                    binding.waitingTime.text = waitingTime
//                }
//                else{
//                    binding.resend.visibility = View.INVISIBLE
//                    binding.waitingTime.text = "Resend OTP"
//
//                }
//                //here you can have your logic to set text to edittext
//            }
//
//            override fun onFinish() {
//                binding.resend.visibility = View.INVISIBLE
//                binding.waitingTime.text = "Resend OTP"
////                mTextField.setText("done!")
////                binding.resend.text = getString(R.string.resend_otp)
//                binding.resend.setOnClickListener {
//
//                }
//                binding.waitingTime.setOnClickListener {
//                    countDown(waitingTime)
//                    binding.resend.visibility = View.VISIBLE
//                    viewModel.resendOtp(phoneNo)
//                }
//            }
//        }.start()
//    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProviders.of(this).get(OtpViewModel::class.java)
        // TODO: Use the ViewModel
    }
    @SuppressLint("UseRequireInsteadOfGet")
    fun hideKeyboard() {
        // Check if no view has focus:
        val view = activity!!.currentFocus
        if (view != null) {
            val inputManager =
                activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(
                view.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }
}