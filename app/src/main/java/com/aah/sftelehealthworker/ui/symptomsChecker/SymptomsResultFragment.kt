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
import com.aah.sftelehealthworker.databinding.SymptomsResultFragmentBinding

class SymptomsResultFragment : Fragment() {

    companion object {
        fun newInstance() = SymptomsResultFragment()
    }

    private lateinit var viewModel: SymptomsResultViewModel
    private lateinit var binding: SymptomsResultFragmentBinding
    private lateinit var navController: NavController

    private lateinit var patientId : String
    private lateinit var patientPhone : String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        return inflater.inflate(R.layout.symptoms_result_fragment, container, false)
        binding = DataBindingUtil.inflate(inflater, R.layout.symptoms_result_fragment, container, false)
        viewModel = ViewModelProvider(this).get(SymptomsResultViewModel::class.java)

        patientId = arguments?.getString("id").toString()

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
        val bundle = bundleOf("patientId" to patientId)
        navController.navigate(R.id.action_symptomsResultFragment_to_doctorCategoryFragment, bundle)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProviders.of(this).get(SymptomsResultViewModel::class.java)
    }

}