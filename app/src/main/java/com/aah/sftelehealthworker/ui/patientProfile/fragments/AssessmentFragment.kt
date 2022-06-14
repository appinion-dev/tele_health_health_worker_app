package com.aah.sftelehealthworker.ui.patientProfile.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.aah.sftelehealthworker.R
import com.aah.sftelehealthworker.databinding.AssessmentFragmentBinding
import com.aah.sftelehealthworker.models.Assessment
import com.aah.sftelehealthworker.ui.patientProfile.adapter.AssessmentRecyclerAdapter

class AssessmentFragment : Fragment() , AssessmentRecyclerAdapter.Interaction {

    companion object {
        fun newInstance() = AssessmentFragment()
    }

    private lateinit var viewModel: AssessmentViewModel
    private lateinit var binding: AssessmentFragmentBinding
    private lateinit var assessmentRecyclerAdapter: AssessmentRecyclerAdapter
    private var assessments= mutableListOf<Assessment>()
    private lateinit var navController: NavController
    private var patientId:String = ""


    public fun setPatientId (patientId:String ){
        this.patientId=patientId
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        val view = inflater.inflate(R.layout.assessment_fragment, container, false)
//        initRecyclerView(view)
//        return view

        binding = DataBindingUtil.inflate(inflater, R.layout.assessment_fragment, container, false)
        viewModel = ViewModelProvider(this).get(AssessmentViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(binding.root)
        initRecyclerView()

        binding.extendedFab.setOnClickListener {
            gotoSymptomsChecker()
        }
    }

    private fun initRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
//            val topSpacingDecorator = TopSpacingItemDecoration(30)
//            addItemDecoration(topSpacingDecorator)
//            val itemDecor = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
//            addItemDecoration(itemDecor)
            assessmentRecyclerAdapter = AssessmentRecyclerAdapter(this@AssessmentFragment)
            adapter = assessmentRecyclerAdapter
        }
        getData()
    }

    private fun gotoSymptomsChecker() {
        val bundle = bundleOf("patientId" to patientId)
        navController.navigate(R.id.action_patientProfileFragment_to_doctorCategoryFragment, bundle)
    }

    private fun getData() {
        assessments.clear()
        for (x in 0 until 10){
            val patientSchedule = Assessment(x.toString(), "Symptoms" , "09:25 AM | Jan 1, 2020")
            assessments.add(patientSchedule)
        }
        assessmentRecyclerAdapter.submitList(assessments)
        binding.recyclerView.scheduleLayoutAnimation()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProviders.of(this).get(AssessmentViewModel::class.java)
    }

    override fun onItemSelected(position: Int, item: Assessment) {
//        gotoSymptomsChecker()
    }

}