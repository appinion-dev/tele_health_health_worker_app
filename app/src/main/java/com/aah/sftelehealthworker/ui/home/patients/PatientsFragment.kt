package com.aah.sftelehealthworker.ui.home.patients

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.aah.sftelehealthworker.R
import com.aah.sftelehealthworker.databinding.PatientsFragmentBinding
import com.aah.sftelehealthworker.models.home.Patient
import com.aah.sftelehealthworker.ui.home.adapter.PatientsRecyclerAdapter
import com.aah.sftelehealthworker.utils.AppUtils
import com.bumptech.glide.Glide


class PatientsFragment : Fragment(), PatientsRecyclerAdapter.Interaction {

    companion object {
        fun newInstance() = PatientsFragment()
    }

    private lateinit var viewModel: PatientsViewModel
    private lateinit var binding: PatientsFragmentBinding
    private lateinit var patientsRecyclerAdapter: PatientsRecyclerAdapter
    private var patients = mutableListOf<Patient>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        val view = inflater.inflate(R.layout.patients_fragment, container, false)
        binding = DataBindingUtil.inflate(inflater, R.layout.patients_fragment, container, false)
        viewModel = ViewModelProvider(this).get(PatientsViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val glide = Glide.with(view.context)
        initRecyclerView()
        initPatientList()
        initMessage()
        initSearchView()
        initRefresh()

        viewModel.loadData("")

        binding.refresh.setOnRefreshListener {
            binding.searchView.text = null
            viewModel.loadData("")
        }

        binding.extendedFab.setOnClickListener {
            gotoVerifyPatientNumber()
        }
    }

    private fun initRefresh() {
        viewModel.refresh.observe(viewLifecycleOwner, Observer {
            binding.refresh.isRefreshing = it
        })
    }

    private fun initSearchView() {
        binding.searchView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.loadData(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
        binding.btnSearch.setOnClickListener {
            viewModel.loadData(binding.searchView.text.toString())
        }
    }

    private fun initMessage() {
        viewModel.message.observe(viewLifecycleOwner, Observer {
            AppUtils.message(binding.root, it, context)
        })
    }

    private fun initPatientList() {
        viewModel.patientMutableLiveData.observe(viewLifecycleOwner, Observer {
            if (it.isNullOrEmpty()) {
                patientsRecyclerAdapter.submitList(emptyList())
               // AppUtils.message(binding.root, "No data found!", context)
            } else {
                patientsRecyclerAdapter.submitList(it)
                binding.recyclerView.scheduleLayoutAnimation()
            }
        })
    }

    private fun gotoVerifyPatientNumber() {
        // val bundle = bundleOf( "phone" to item.phone)
        Navigation.findNavController(binding.root)
            .navigate(R.id.action_homeFragment_to_verifyPatientOtpFragment)
//        Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_verifyPatientMobileNumberFragment)
    }

    private fun gotoPatientProfile(item: Patient) {
        val bundle = bundleOf("id" to item.id, "phone" to item.phone
        ,"name" to item.firstName+item.lastName,
            "gender" to item.gender,
            "age" to item.age)
        Navigation.findNavController(binding.root)
            .navigate(R.id.action_homeFragment_to_patientProfileFragment, bundle)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProviders.of(this).get(PatientsViewModel::class.java)
    }

    private fun initRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
//            val topSpacingDecorator = TopSpacingItemDecoration(30)
//            addItemDecoration(topSpacingDecorator)
//            val itemDecor = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
//            addItemDecoration(itemDecor)
            patientsRecyclerAdapter = PatientsRecyclerAdapter(this@PatientsFragment)
            adapter = patientsRecyclerAdapter
        }
//        getData()
    }

    override fun onItemSelected(position: Int, item: Patient) {
        gotoPatientProfile(item)
//        gotoSymptomsChecker(item)
    }

//    private fun gotoSymptomsChecker(item: Patient) {
//        val bundle = bundleOf("id" to item.id, "phone" to item.phone)
//        Navigation.findNavController(binding.root).navigate(R.id.action_homeFragment_to_symptomsCheckerFragment, bundle)
//    }

}