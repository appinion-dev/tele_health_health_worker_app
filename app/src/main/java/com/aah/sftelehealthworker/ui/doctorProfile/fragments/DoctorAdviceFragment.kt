package com.aah.sftelehealthworker.ui.doctorProfile.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aah.sftelehealthworker.R

class DoctorAdviceFragment : Fragment() {

    companion object {
        fun newInstance() = DoctorAdviceFragment()
    }

    private lateinit var viewModel: DoctorAdviceViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.doctor_advice_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DoctorAdviceViewModel::class.java)
    }

}