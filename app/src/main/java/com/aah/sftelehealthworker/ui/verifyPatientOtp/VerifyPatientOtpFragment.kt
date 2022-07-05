package com.aah.sftelehealthworker.ui.verifyPatientOtp

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.aah.sftelehealthworker.R
import com.aah.sftelehealthworker.databinding.VerifyPatientOtpFragmentBinding
import com.aah.sftelehealthworker.utils.AppUtils
import com.aah.sftelehealthworker.utils.PHONE_NO
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.verify_patient_mobile_number_fragment.view.*
import kotlinx.android.synthetic.main.verify_patient_otp_fragment.view.*

class VerifyPatientOtpFragment : Fragment() {

    //    fun countDown(waitingTime: Long) {
//        object : CountDownTimer(waitingTime, 1000) {
//            override fun onTick(millisUntilFinished: Long) {
//
//                var millis = millisUntilFinished / 1000
//                if (millis > 0L) {
//                    val waitingTime = "${millis}  s"
//                    binding.waitingTime.text = waitingTime
//                } else {
//                    binding.resend.visibility = View.INVISIBLE
//                    binding.waitingTime.text = "Resend OTP"
//                    binding.waitingTime.setOnClickListener {
//                        countDown(waitingTime)
//                        binding.resend.visibility = View.VISIBLE
//                        //  viewModel.resendOtp(phoneNo)
//                    }
//                }
//                //here you can have your logic to set text to edittext
//            }
//
//            override fun onFinish() {
////                mTextField.setText("done!")
////                binding.resend.text = getString(R.string.resend_otp)
//                binding.resend.setOnClickListener {
//
//                }
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
//                    //viewModel.resendOtp(phoneNo)
//                }
//            }
//        }.start()
//    }
    private val waitingTime = 10000L
    private lateinit var viewModel: VerifyPatientOtpViewModel
    private lateinit var binding: VerifyPatientOtpFragmentBinding
    private var phoneNo: String = ""
    private var otpString: String = ""
    private lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        val view = inflater.inflate(R.layout.verify_patient_otp_fragment, container, false)

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.verify_patient_otp_fragment,
            container,
            false
        )
        viewModel = ViewModelProvider(this).get(VerifyPatientOtpViewModel::class.java)

        phoneNo = arguments?.getString(PHONE_NO).toString()
        //  otpString = arguments?.getString(OTP).toString()
        binding.confirm.setOnClickListener {

            gotoSelectPatient()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //  countDown(waitingTime)
        navController = Navigation.findNavController(binding.root)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

        //  binding.otp.setText(otpString)

        binding.back.setOnClickListener {
            navController.popBackStack()
        }

        binding.confirm.setOnClickListener {
//            if (!AppUtils.isNetworkAvailable(requireContext())) {
//                AppUtils.message(
//                    view,
//                    getString(R.string.internet_not_available),
//                    Color.WHITE,
//                    ContextCompat.getColor(requireActivity(), R.color.colorPrimary), 500
//                )
//
//                return@setOnClickListener
//            }
            if (binding.mobileNo.text.toString().isEmpty()) {
                Snackbar.make(binding.root, "Enter Patient SDO ID", Snackbar.LENGTH_LONG).show()
            } else {
                viewModel.loadData(binding.mobileNo.text.toString(), "111111")
                gotoSelectPatient()
            }

        }

        viewModel.setSuccessFullFalse()
        initSuccessful()
        initMessage()
    }

    private fun initSuccessful() {
        viewModel.isSuccessFull.observe(viewLifecycleOwner, Observer {
            if (it) {
                gotoSelectPatient()
            }
        })
    }

    private fun initMessage() {
        viewModel.message.observe(viewLifecycleOwner, Observer {
            AppUtils.message(binding.root, it, context)
        })
    }

    private fun gotoSelectPatient() {
        if (binding.mobileNo.text.toString().length < 10) {
            AppUtils.message(binding.root, "Number can't be less then 10", Color.WHITE, Color.RED)
        } else {
            val bundle = bundleOf(PHONE_NO to binding.mobileNo.text.toString())

//        Navigation.findNavController(view).navigate(R.id.action_verifyPatientOtpFragment_to_patientProfileCreateFragment, bundle)
            navController.navigate(
                R.id.action_verifyPatientOtpFragment_to_patientProfileCreateFragment,
                bundle
            )
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProviders.of(this).get(VerifyPatientOtpViewModel::class.java)
    }

    companion object {
        fun newInstance() = VerifyPatientOtpFragment()
        fun onBackPressed(): Boolean {
            return true
        }
    }

}