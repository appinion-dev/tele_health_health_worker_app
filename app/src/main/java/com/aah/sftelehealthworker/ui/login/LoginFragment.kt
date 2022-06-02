package com.aah.sftelehealthworker.ui.login

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.aah.sftelehealthworker.R
import com.aah.sftelehealthworker.databinding.LoginFragmentBinding
import com.aah.sftelehealthworker.utils.AppUtils
import com.aah.sftelehealthworker.utils.NumberValidation
import com.aah.sftelehealthworker.utils.OTP
import com.aah.sftelehealthworker.utils.PHONE_NO

class LoginFragment : Fragment() {

//    companion object {
//        fun newInstance() = LoginFragment()
//    }

    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: LoginFragmentBinding
    private lateinit var nav: NavController
    private var otp = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val view =  inflater.inflate(R.layout.login_fragment, container, false)
        binding = DataBindingUtil.inflate(inflater, R.layout.login_fragment, container, false)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        nav = Navigation.findNavController(view)
        initMessage()
        initSuccessFull()
        initOtp()
        NumberValidation.checkMobileNumber(binding.mobileNo)
        binding.next.setOnClickListener {
//            nav = Navigation.findNavController(view)
            //1310440086
            if (!NumberValidation.isValid(binding.mobileNo.text.toString())) {
                AppUtils.message(view, "Please input a valid mobile number!", Color.RED, Color.GRAY)
                return@setOnClickListener
            }
            viewModel.loadData(binding.mobileNo.text.toString())
//            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_otpFragment)
        }
    }


    private fun initSuccessFull() {
        viewModel.isSuccessFull.observe(viewLifecycleOwner, Observer {
            if (it) {
                gotoOtp()
            }
        })
    }

    private fun initMessage() {
        viewModel.message.observe(viewLifecycleOwner, Observer {
            AppUtils.message(view, it, context)
        })
    }

    private fun initOtp() {
        viewModel.otpLiveData.observe(viewLifecycleOwner, Observer {
            it?.otp?.let {
                otp = it
            }
        })
    }

    private fun gotoOtp() {
//        val mobileNo = view.mobileNo.text.toString()
//        val action = LoginFragmentDirections.actionLoginFragmentToOtpFragment(mobileNo)
        val bundle =
            bundleOf(PHONE_NO to binding.mobileNo.text.toString(), OTP to otp.toString())
//        val bundle = bundleOf("otp" to otp)
        nav.navigate(R.id.action_loginFragment_to_otpFragment, bundle)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

    }

}