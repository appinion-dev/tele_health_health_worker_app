package com.aah.sftelehealthworker.ui.home.settings

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.aah.sftelehealthworker.R
import com.aah.sftelehealthworker.databinding.SettingsFragmentBinding
import com.aah.sftelehealthworker.ui.splash.SplashViewModel

class SettingsFragment : Fragment() {

    companion object {
        fun newInstance() = SettingsFragment()
    }

    private lateinit var viewModel: SettingsViewModel
    private lateinit var binding: SettingsFragmentBinding

//    action_homeFragment_to_splashFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        return inflater.inflate(R.layout.settings_fragment, container, false)
        binding = DataBindingUtil.inflate(inflater, R.layout.settings_fragment, container, false)
        viewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.logout.setOnClickListener {
            viewModel.delete()
            gotoSplash()
        }

        viewModel.getHealthWorker()
        viewModel.healthWorkerMutableLiveData.observe(viewLifecycleOwner, Observer {
            if(it!= null){
                binding.name.text = it.name
                binding.age.text = "${it.isdCode}${it.phone}"
                binding.sex.text = it.gender
            }
        })
    }

    private fun gotoSplash() {
        Navigation.findNavController(binding.root).navigate(R.id.action_homeFragment_to_splashFragment)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SettingsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}