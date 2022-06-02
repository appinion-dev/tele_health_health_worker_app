package com.aah.sftelehealthworker.ui.doctorProfile.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aah.sftelehealthworker.R

class DoctorDocumentsFragment : Fragment() {

    companion object {
        fun newInstance() = DoctorDocumentsFragment()
    }

    private lateinit var viewModel: DoctorDocumentsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.doctor_documents_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DoctorDocumentsViewModel::class.java)
    }

}