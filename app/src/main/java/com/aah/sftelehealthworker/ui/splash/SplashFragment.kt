package com.aah.sftelehealthworker.ui.splash

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.aah.sftelehealthworker.R
import com.aah.sftelehealthworker.databinding.SplashFragmentBinding
import com.aah.sftelehealthworker.ui.login.LoginViewModel

class SplashFragment : Fragment() {

    companion object {
        fun newInstance() = SplashFragment()
    }

    private lateinit var viewModel: SplashViewModel
    private lateinit var binding: SplashFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        return inflater.inflate(R.layout.splash_fragment, container, false)
        getActivity()?. getWindow()?.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,         WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = DataBindingUtil.inflate(inflater, R.layout.splash_fragment, container, false)
        viewModel = ViewModelProvider(this).get(SplashViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

        Handler().postDelayed({
            viewModel.checkLogin()
        }, 1000)
//        viewModel.checkLogin()
        viewModel.isLogin.observe(viewLifecycleOwner, Observer {
            if (it) {
                gotoHome()
            } else {
                gotoLogin()
            }
        })
    }

    private fun gotoHome() {
        Navigation.findNavController(binding.root)
            .navigate(R.id.action_splashFragment_to_homeFragment)
    }

    private fun gotoLogin() {
        Navigation.findNavController(binding.root)
            .navigate(R.id.action_splashFragment_to_loginFragment)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProviders.of(this).get(SplashViewModel::class.java)
    }

}