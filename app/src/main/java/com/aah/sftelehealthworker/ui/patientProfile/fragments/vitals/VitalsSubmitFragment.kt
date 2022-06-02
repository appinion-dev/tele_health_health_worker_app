package com.aah.sftelehealthworker.ui.patientProfile.fragments.vitals

import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.aah.sftelehealthworker.R
import com.aah.sftelehealthworker.common.BaseFragment
import com.aah.sftelehealthworker.databinding.VitalsSubmitFragmentBinding
import com.aah.sftelehealthworker.utils.AppUtils
import com.aah.sftelehealthworker.utils.PATIENT_ID_KEY

class VitalsSubmitFragment : BaseFragment() {
    private var lastClickTime: Long = 0

    companion object {
        fun newInstance() = VitalsSubmitFragment()
    }

    private lateinit var viewModel: VitalsSubmitViewModel
    private lateinit var binding: VitalsSubmitFragmentBinding
    private lateinit var navController: NavController

    private var patientId = "0"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        return inflater.inflate(R.layout.vitals_submit_fragment, container, false)

        binding =
            DataBindingUtil.inflate(inflater, R.layout.vitals_submit_fragment, container, false)
        viewModel = ViewModelProvider(this).get(VitalsSubmitViewModel::class.java)
        mActivity = activity
        patientId = arguments?.getString(PATIENT_ID_KEY).toString()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(binding.root)
        viewModel.setSuccessfulFalse()

        initMessage()
        initSuccessful()
        binding.done.setOnClickListener {
            if (SystemClock.elapsedRealtime() - lastClickTime < 1000) {
                return@setOnClickListener;
            }
            lastClickTime = SystemClock.elapsedRealtime();

            showProgressDialog()
            val temperature = binding.temperature.text.toString()
            val pulse = binding.pulse.text.toString()
            val respiration = binding.respiration.text.toString()
            val pressure = binding.pressure.text.toString()
            val weight = binding.weight.text.toString()
            val bloodSugar = binding.bloodSugar.text.toString()
//          pulseRate: String, respirationRate: String, bloodPressureLow: String, bloodPressureHigh: String, bodyWeight: String, bloodSuger: String

            viewModel.submitVitals(
                patientId.toInt(),
                temperature,
                pulse,
                respiration,
                pressure,
                weight,
                bloodSugar
            )
        }
    }

    private fun initSuccessful() {
        viewModel.isSuccessFull.observe(viewLifecycleOwner, Observer {
            if (it) {
                hideProgressDialogVital()
                navController.popBackStack()
                AppUtils.message(binding.root, "Vital Submitted", context)

            }
        })
    }

    private fun initMessage() {
        viewModel.message.observe(viewLifecycleOwner, Observer {
            hideProgressDialogVital()
            AppUtils.message(binding.root, it, context)
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProviders.of(this).get(VitalsSubmitViewModel::class.java)

    }

}