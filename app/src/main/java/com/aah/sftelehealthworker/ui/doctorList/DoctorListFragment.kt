package com.aah.sftelehealthworker.ui.doctorList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.aah.sftelehealthworker.R
import com.aah.sftelehealthworker.databinding.DoctorListFragmentBinding
import com.aah.sftelehealthworker.models.appoinment.Doctor
import com.aah.sftelehealthworker.ui.doctorList.adapter.DoctorsRecyclerAdapter
import com.aah.sftelehealthworker.utils.AppUtils

class DoctorListFragment : Fragment(), DoctorsRecyclerAdapter.Interaction {

    companion object {
        fun newInstance() = DoctorListFragment()
    }

    private lateinit var viewModel: DoctorListViewModel
    private lateinit var binding: DoctorListFragmentBinding
    private lateinit var doctorsRecyclerAdapter: DoctorsRecyclerAdapter

    private var categoryId : String = ""
    private var patientId : String = ""

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        return inflater.inflate(R.layout.doctor_list_fragment, container, false)
        binding = DataBindingUtil.inflate(inflater, R.layout.doctor_list_fragment, container, false)
        viewModel = ViewModelProvider(this).get(DoctorListViewModel::class.java)

        categoryId = arguments?.getString("categoryId").toString()
        patientId = arguments?.getString("patientId").toString()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Step 2: Select Doctor"

        navController = Navigation.findNavController(binding.root)
        initAdapter()
        initMessage()
        initRefresh()

        viewModel.loadData(categoryId)

    }

    private fun initMessage() {
        viewModel.message.observe(viewLifecycleOwner, Observer {
            AppUtils.message(binding.root, it,context)
        })
    }

    private fun initRefresh() {

        binding.refresh.setOnRefreshListener {
            viewModel.loadData(categoryId)
        }

        viewModel.refresh.observe(viewLifecycleOwner, Observer {
            binding.refresh.isRefreshing = it
        })
    }

    private fun initAdapter() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
//            val itemDecor = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
//            addItemDecoration(itemDecor)
            doctorsRecyclerAdapter = DoctorsRecyclerAdapter(this@DoctorListFragment)
            adapter = doctorsRecyclerAdapter
        }

        viewModel.doctorMutableLiveData.observe(viewLifecycleOwner, Observer {
            if (it.isNullOrEmpty()) {
                AppUtils.message(binding.root, "No Data found",context)
                doctorsRecyclerAdapter.submitList(emptyList())
            } else {
                doctorsRecyclerAdapter.submitList(it)
            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProviders.of(this).get(DoctorListViewModel::class.java)
    }

    override fun onItemSelected(position: Int, item: Doctor) {
        gotoDoctorProfile(item)
    }

    private fun gotoDoctorProfile(item: Doctor) {
        val name = item.title + " " + item.firstName+ " " + item.lastName
        val bundle = bundleOf("categoryId" to categoryId , "doctorId" to item.id.toString(),"doctorName" to name, "patientId" to patientId )
        navController.navigate(R.id.action_doctorListFragment_to_doctorScheduleFragment, bundle)
    }

}