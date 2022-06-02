package com.aah.sftelehealthworker.ui.symptomsChecker

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.aah.sftelehealthworker.R
import com.aah.sftelehealthworker.databinding.SymptomsCheckerFragmentBinding
import com.aah.sftelehealthworker.ui.patientProfile.PatientProfileViewModel

class SymptomsCheckerFragment : Fragment() {

    companion object {
        fun newInstance() = SymptomsCheckerFragment()
    }

    private lateinit var viewModel: SymptomsCheckerViewModel
    private lateinit var binding: SymptomsCheckerFragmentBinding
    private lateinit var navController: NavController

    private lateinit var patientId : String
    private lateinit var patientPhone : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        return inflater.inflate(R.layout.symptoms_checker_fragment, container, false)
        binding = DataBindingUtil.inflate(inflater, R.layout.symptoms_checker_fragment, container, false)
        viewModel = ViewModelProvider(this).get(SymptomsCheckerViewModel::class.java)

        patientId = arguments?.getString("patientId").toString()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(binding.root)
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Symptoms Checker"

        binding.previous.setOnClickListener {
            navController.popBackStack()
        }
        binding.next.setOnClickListener {
            gotoResult()
        }
    }

    private fun gotoResult() {
        val bundle = bundleOf("id" to patientId)
        navController.navigate(R.id.action_symptomsCheckerFragment_to_symptomsResultFragment, bundle)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProviders.of(this).get(SymptomsCheckerViewModel::class.java)
    }

}