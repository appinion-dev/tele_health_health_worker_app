package com.aah.sftelehealthworker.ui.verifyPatientMobileNumber

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
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.aah.sftelehealthworker.R
import com.aah.sftelehealthworker.databinding.VerifyPatientMobileNumberFragmentBinding
import com.aah.sftelehealthworker.ui.login.LoginViewModel
import com.aah.sftelehealthworker.utils.AppUtils
import com.aah.sftelehealthworker.utils.NumberValidation
import kotlinx.android.synthetic.main.verify_patient_mobile_number_fragment.view.*


class VerifyPatientMobileNumberFragment : Fragment() {

    companion object {
        fun newInstance() = VerifyPatientMobileNumberFragment()
        fun onBackPressed(): Boolean {
            return true
        }
    }

    private lateinit var binding: VerifyPatientMobileNumberFragmentBinding
    private lateinit var viewModel: VerifyPatientMobileNumberViewModel
    private var otpString = ""
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        val view =  inflater.inflate(R.layout.verify_patient_mobile_number_fragment, container, false)

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.verify_patient_mobile_number_fragment,
            container,
            false
        )
        viewModel = ViewModelProvider(this).get(VerifyPatientMobileNumberViewModel::class.java)
//        view.back.setOnClickListener {
//            gotoPrevious(view)
//        }

//        view.sendOtp.setOnClickListener {
//            viewModel.loadData(view.mobileNo.text.toString())
////            gotoVerifyPatientOtp(view)
//        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(binding.root)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        NumberValidation.checkMobileNumber(binding.mobileNo)
        binding.back.setOnClickListener {
            gotoPrevious()
        }

        binding.sendOtp.setOnClickListener {
            if (!AppUtils.isNetworkAvailable(requireContext())) {
                AppUtils.message(
                    view,
                    getString(R.string.internet_not_available),
                    Color.WHITE,
                    ContextCompat.getColor(requireActivity(), R.color.colorPrimary), 5000
                )
                return@setOnClickListener

            }
            if (!NumberValidation.isValid(binding.mobileNo.text.toString())) {
                AppUtils.message(
                    view,
                    "Please input a valid mobile number!",
                    Color.WHITE,
                    ContextCompat.getColor(requireActivity(), R.color.colorPrimary)
                )
                return@setOnClickListener
            }
            viewModel.loadData(binding.mobileNo.text.toString())
        }

        viewModel.setSuccessFullFalse()
        initOtp()
        initSuccessful()
        initRefresh()
        initMessage()
    }

    private fun initOtp() {
        viewModel.otpLiveData.observe(viewLifecycleOwner, Observer {
            it?.let { otp ->
                otpString = otp.otp.toString()
            }
        })
    }

    private fun initSuccessful() {
        viewModel.isSuccessFull.observe(viewLifecycleOwner, Observer {
            if (it)
                gotoVerifyPatientOtp()
        })
    }

    private fun initRefresh() {
        viewModel.refresh.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.progress.visibility = View.VISIBLE
            } else {
                binding.progress.visibility = View.GONE
            }
        })
    }

    private fun initMessage() {
        viewModel.message.observe(viewLifecycleOwner, Observer {
            AppUtils.message(binding.root, it,context)
        })
    }

    private fun gotoVerifyPatientOtp() {
        val bundle = bundleOf("phoneNo" to binding.mobileNo.text.toString(), "otp" to otpString)
//        Navigation.findNavController(binding.root).navigate(R.id.action_verifyPatientMobileNumberFragment_to_verifyPatientOtpFragment, bundle)
        navController.navigate(
            R.id.action_homeFragment_to_verifyPatientOtpFragment,
            bundle
        )
    }

    private fun gotoPrevious() {
        navController.popBackStack()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProviders.of(this).get(VerifyPatientMobileNumberViewModel::class.java)
    }

}