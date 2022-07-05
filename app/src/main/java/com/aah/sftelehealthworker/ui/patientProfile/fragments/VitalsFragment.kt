package com.aah.sftelehealthworker.ui.patientProfile.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.aah.sftelehealthworker.R
import com.aah.sftelehealthworker.common.BaseFragment
import com.aah.sftelehealthworker.databinding.VitalsFragmentBinding
import com.aah.sftelehealthworker.models.newPatient.Vital
import com.aah.sftelehealthworker.ui.patientProfile.adapter.VitalsRecyclerAdapter
import com.aah.sftelehealthworker.utils.AppUtils
import com.aah.sftelehealthworker.utils.PATIENT_ID_KEY

class VitalsFragment : BaseFragment(), VitalsRecyclerAdapter.Interaction {

    companion object {
        fun newInstance() = VitalsFragment()
    }

    private lateinit var viewModel: VitalsViewModel
    private lateinit var binding: VitalsFragmentBinding
    private lateinit var navController: NavController

    private lateinit var vitalsRecyclerAdapter: VitalsRecyclerAdapter

    private var patientId: String = ""

    public fun setPatientId(patientId: String) {
        this.patientId = patientId
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        return inflater.inflate(R.layout.vitals_fragment, container, false)

        binding = DataBindingUtil.inflate(inflater, R.layout.vitals_fragment, container, false)
        viewModel = ViewModelProvider(this).get(VitalsViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(binding.root)
        initAdapter()
        initMessage()
        initRefresh()

        viewModel.loadData(patientId)
        binding.extendedFab.setOnClickListener {
            gotoVitalsSubmit()
        }
    }

    private fun initMessage() {
        viewModel.message.observe(viewLifecycleOwner, Observer {
            AppUtils.message(binding.root, it, context)
        })
    }

    private fun initRefresh() {

        binding.refresh.setOnRefreshListener {
            viewModel.loadData(patientId)
        }

        viewModel.refresh.observe(viewLifecycleOwner, Observer {
            binding.refresh.isRefreshing = it
        })
    }

    private fun initAdapter() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            vitalsRecyclerAdapter = VitalsRecyclerAdapter(this@VitalsFragment)
            adapter = vitalsRecyclerAdapter
        }

        viewModel.vitalMutableLiveData.observe(viewLifecycleOwner, Observer {
            hideProgressDialog()
            if (it.isNullOrEmpty()) {
               // AppUtils.message(binding.root, "No vitals found", context)
                vitalsRecyclerAdapter.submitList(emptyList())
            } else {
                vitalsRecyclerAdapter.submitList(it)
                binding.recyclerView.scheduleLayoutAnimation()
            }
        })
    }

    private fun gotoVitalsSubmit() {
        val bundle = bundleOf(PATIENT_ID_KEY to patientId)
        navController.navigate(R.id.action_patientProfileFragment_to_vitalsSubmitFragment, bundle)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProviders.of(this).get(VitalsViewModel::class.java)
    }

    override fun onItemSelected(position: Int, item: Vital) {

        gotoVitalsInfo(item)
    }

    private fun gotoVitalsInfo(item: Vital) {
        val bundle = bundleOf(
            PATIENT_ID_KEY to patientId,
            "bloodTemperature" to item.bloodTemperature,
            "pulseRate" to item.pulseRate,
            "respirationRate" to item.respirationRate,
            "bloodPressure" to item.bloodPressure,
            "bodyWeight" to item.bodyWeight,
            "bloodSuger" to item.bloodSuger,
            "createdAt" to item.createdAt,
            "updatedAt" to item.updatedAt
        )
        navController.navigate(R.id.action_patientProfileFragment_to_vitalsInfoFragment, bundle)
    }

}