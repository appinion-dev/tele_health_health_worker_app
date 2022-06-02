package com.aah.sftelehealthworker.ui.doctorProfile.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.aah.sftelehealthworker.R
import com.aah.sftelehealthworker.databinding.DoctorDetailsFragmentBinding
import com.aah.sftelehealthworker.models.appoinment.Doctor

class DoctorDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = DoctorDetailsFragment()
    }

    private lateinit var viewModel: DoctorDetailsViewModel
    private lateinit var binding : DoctorDetailsFragmentBinding

    private lateinit var doctor: Doctor

    fun setInfo(doctor: Doctor) {
        this.doctor = doctor
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        return inflater.inflate(R.layout.doctor_details_fragment, container, false)

        binding = DataBindingUtil.inflate(inflater, R.layout.doctor_details_fragment, container, false)
        viewModel = ViewModelProvider(this).get(DoctorDetailsViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (::doctor.isInitialized){
            binding.regNo.text = doctor.regNo
            binding.language.text = doctor.languages
            binding.workPlace.text = doctor.hospital
            binding.location.text = doctor.location
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProviders.of(this).get(DoctorDetailsViewModel::class.java)
    }

}