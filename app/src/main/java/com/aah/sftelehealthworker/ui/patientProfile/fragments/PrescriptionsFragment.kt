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
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.aah.sftelehealthworker.R
import com.aah.sftelehealthworker.databinding.PrescriptionsFragmentBinding
import com.aah.sftelehealthworker.models.home.Patient
import com.aah.sftelehealthworker.models.newPatient.Prescription
import com.aah.sftelehealthworker.ui.patientProfile.adapter.PrescriptionsRecyclerAdapter
import com.aah.sftelehealthworker.utils.AppUtils
import com.aah.sftelehealthworker.utils.CASE_ID_KEY
import com.aah.sftelehealthworker.utils.PATIENT_ID_KEY
import com.aah.sftelehealthworker.utils.PRESCRIPTION_KEY

class PrescriptionsFragment : Fragment(), PrescriptionsRecyclerAdapter.Interaction {

    companion object {
        fun newInstance() = PrescriptionsFragment()
    }

    private lateinit var viewModel: PrescriptionsViewModel
    private lateinit var binding: PrescriptionsFragmentBinding
    private lateinit var prescriptionsRecyclerAdapter: PrescriptionsRecyclerAdapter

    private var patientId: String = ""

    public fun setPatientId(patientId: String) {
        this.patientId = patientId
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        return inflater.inflate(R.layout.prescriptions_fragment, container, false)
        binding =
            DataBindingUtil.inflate(inflater, R.layout.prescriptions_fragment, container, false)
        viewModel = ViewModelProvider(this).get(PrescriptionsViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        initMessage()
        initRefresh()

        viewModel.loadData(patientId,"")
    }

    private fun initMessage() {
        viewModel.message.observe(viewLifecycleOwner, Observer {
            AppUtils.message(binding.root, it, context)
        })
    }

    private fun initRefresh() {

        binding.refresh.setOnRefreshListener {
            viewModel.loadData(patientId,"")
        }

        viewModel.refresh.observe(viewLifecycleOwner, Observer {
            binding.refresh.isRefreshing = it
        })
    }

    private fun initAdapter() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            prescriptionsRecyclerAdapter = PrescriptionsRecyclerAdapter(this@PrescriptionsFragment)
            adapter = prescriptionsRecyclerAdapter
        }

        viewModel.prescriptionsMutableLiveData.observe(viewLifecycleOwner, Observer {
            if (it.isNullOrEmpty()) {
                //AppUtils.message(binding.root, "No prescription Found", context)
                prescriptionsRecyclerAdapter.submitList(emptyList())
            } else {
                prescriptionsRecyclerAdapter.submitList(it)
                binding.recyclerView.scheduleLayoutAnimation()
            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProviders.of(this).get(PrescriptionsViewModel::class.java)
    }

    override fun onItemSelected(position: Int, item: Prescription) {
        gotoPrescriptionView(item)
    }

    private fun gotoPrescriptionView(prescription: Prescription) {
        val bundle = bundleOf(
            PATIENT_ID_KEY to patientId,
            CASE_ID_KEY to prescription.caseId.toString(),
            PRESCRIPTION_KEY to prescription.prescriptionDetails
        )
        Navigation.findNavController(binding.root)
            .navigate(R.id.action_patientProfileFragment_to_prescriptionViewFragment, bundle)
    }

}