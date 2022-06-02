package com.aah.sftelehealthworker.ui.patientProfile.fragments.vitals

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.aah.sftelehealthworker.R
import com.aah.sftelehealthworker.databinding.VitalsInfoFragmentBinding
import com.aah.sftelehealthworker.ui.patientProfile.fragments.VitalsViewModel
import com.aah.sftelehealthworker.utils.PATIENT_ID_KEY

class VitalsInfoFragment : Fragment() {

    companion object {
        fun newInstance() = VitalsInfoFragment()
    }

    private lateinit var viewModel: VitalsInfoViewModel
    private lateinit var binding: VitalsInfoFragmentBinding

    private var patientId = "0"
    private var bloodTemperature = ""
    private var pulseRate = ""
    private var respirationRate = ""
    private var bloodPressure = ""
    private var bodyWeight = ""
    private var bloodSuger = ""
    private var createdAt = ""
    private var updatedAt = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        return inflater.inflate(R.layout.vitals_info_fragment, container, false)

        binding = DataBindingUtil.inflate(inflater, R.layout.vitals_info_fragment, container, false)
        viewModel = ViewModelProvider(this).get(VitalsInfoViewModel::class.java)

        /*

        val bundle = bundleOf("patientId" to patientId,
        "bloodTemperature" to item.bloodTemperature,
        "pulseRate" to item.pulseRate,
        "respirationRate" to item.respirationRate,
        "bloodPressure" to item.bloodPressure,
        "bodyWeight" to item.bodyWeight,
        "bloodSuger" to item.bloodSuger,
        "createdAt" to item.createdAt,
        "updatedAt" to item.updatedAt)
         */
        patientId = arguments?.getString(PATIENT_ID_KEY).toString()
        bloodTemperature = arguments?.getString("bloodTemperature").toString()
        pulseRate = arguments?.getString("pulseRate").toString()
        respirationRate = arguments?.getString("respirationRate").toString()
        bloodPressure = arguments?.getString("bloodPressure").toString()
        bodyWeight = arguments?.getString("bodyWeight").toString()
        bloodSuger = arguments?.getString("bloodSuger").toString()
        createdAt = arguments?.getString("createdAt").toString()
        updatedAt = arguments?.getString("updatedAt").toString()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bloodTemperature.text = "Blood Temperature: $bloodTemperature"
        binding.pulseRate.text = "Pulse Rate: $pulseRate"
        binding.respirationRate.text = "Respiration Rate: $respirationRate"
        binding.bloodPressure.text = "Blood Pressure: $bloodPressure"
        binding.bodyWeight.text = "Body Weight: $bodyWeight"
        binding.bloodSuger.text = "Blood Suger: $bloodSuger"
        binding.createdAt.text = "Created At: $createdAt"
        binding.updatedAt.text = "Updated At: $updatedAt"
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProviders.of(this).get(VitalsInfoViewModel::class.java)
    }

}