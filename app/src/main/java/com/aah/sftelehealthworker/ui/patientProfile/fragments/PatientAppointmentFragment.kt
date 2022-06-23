package com.aah.sftelehealthworker.ui.patientProfile.fragments

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.aah.sftelehealthworker.R
import com.aah.sftelehealthworker.common.BaseFragment
import com.aah.sftelehealthworker.databinding.AppointmentsFragmentBinding
import com.aah.sftelehealthworker.models.DataResource
import com.aah.sftelehealthworker.models.patient.Appointment
import com.aah.sftelehealthworker.ui.patientProfile.adapter.AppointmentAdapter
import com.aah.sftelehealthworker.utils.AppUtils
import com.aah.sftelehealthworker.utils.CASE_ID_KEY
import com.aah.sftelehealthworker.utils.DateUtils
import com.aah.sftelehealthworker.utils.PATIENT_ID_KEY
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.*
import kotlin.collections.ArrayList

class PatientAppointmentFragment : BaseFragment(), AppointmentAdapter.Interaction {

    companion object {
        fun newInstance() = PatientAppointmentFragment()
    }

    private var calendar: Calendar? = null
    private lateinit var viewModel: PatientAppointmentViewModel
    private lateinit var binding: AppointmentsFragmentBinding
    private lateinit var scheduleRecyclerAdapter: AppointmentAdapter
    private var previousPhoneNo: String = ""
    private var patientPhoneNo: String = ""
    private var patientId: String = ""
    private var glide: RequestManager? = null
    var filterList: ArrayList<Appointment>? = null
    var mainList: ArrayList<Appointment>? = null
    private lateinit var navController: NavController

//    private lateinit var navController: NavController

    public fun setPreviousPhoneNo(previousPhoneNo: String) {
        this.previousPhoneNo = previousPhoneNo
    }

    public fun setPatientPhoneNo(patientPhoneNo: String) {
        this.patientPhoneNo = patientPhoneNo
    }

    public fun setPatientId(patientId: String) {
        this.patientId = patientId
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        val view = inflater.inflate(R.layout.appointments_fragment, container, false)

        binding =
            DataBindingUtil.inflate(inflater, R.layout.appointments_fragment, container, false)
        viewModel = ViewModelProvider(this).get(PatientAppointmentViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        calendar = Calendar.getInstance()
        navController = Navigation.findNavController(binding.root)

        filterList = ArrayList<Appointment>()
        mainList = ArrayList<Appointment>()
        glide = Glide.with(binding.root)
//        navController = Navigation.findNavController(binding.root)
        binding.tvDate.text = DateUtils.currentDate()

        initRecyclerView()
        initPatientList()
        initMessage()
        initSearchView()
        initRefresh()
        initFab()

        viewModel.loadData(patientId, "", "")

        binding.sort.setOnClickListener {
            showDialog()
        }

        binding.refresh.setOnRefreshListener {
            binding.sort.text = resources.getString(R.string.all_appiontments)
            binding.searchView.text = null
            viewModel.loadData(patientId, "", "")
        }
        binding.tvDate.setOnClickListener {
            showDatePicker(binding.tvDate, calendar!!)
        }
    }

    private fun initFab() {
        if (patientPhoneNo.isEmpty()) {
            binding.extendedFab.visibility = View.GONE
        } else {
//            binding.extendedFab.visibility = View.VISIBLE
//            binding.extendedFab.setOnClickListener {
//                gotoCategory()
//            }
        }

        binding.extendedFab.setOnClickListener {
            gotoSymptomsChecker()
        }
    }

    private fun gotoSymptomsChecker() {
        val bundle = bundleOf("patientId" to patientId)
        navController.navigate(R.id.action_patientProfileFragment_to_doctorCategoryFragment, bundle)
    }

    private fun gotoCategory() {
        val bundle = bundleOf("patientId" to patientId)
//        navController.navigate(R.id.action_patientProfileFragment_to_doctorCategoryFragment)
        Navigation.findNavController(binding.root)
            .navigate(R.id.action_patientProfileFragment_to_doctorCategoryFragment, bundle)
    }

    private fun showDialog() {
        val options = arrayOf("Upcoming", "Completed", "Expired")
        context?.let {
            MaterialAlertDialogBuilder(it)
//                .setTitle(resources.getString(R.string.select_district))
                .setItems(options) { dialog, which ->
                    binding.sort.text = options[which]
                    viewModel.loadData(patientId, options[which].toLowerCase(), "")
                }
                .show()
        }
    }

    private fun initRefresh() {

    }

    private fun initSearchView() {
        binding.searchView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
               // filterList = getFilterData(mainList!!, s!!)
              //  scheduleRecyclerAdapter.submitList(filterList!!)
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
        binding.btnSearch.setOnClickListener {
            viewModel.loadData(
                patientId, binding.searchView.text.toString(), ""
            )
        }
    }

    private fun initMessage() {

    }

    private fun initPatientList() {
        viewModel.observeAppointmentData().observe(viewLifecycleOwner, Observer { dataResource ->
            when (dataResource.status) {
                DataResource.DataStatus.LOADING -> {
                    binding.refresh.isRefreshing = true
                }
                DataResource.DataStatus.ERROR -> {
                    AppUtils.message(binding.root, dataResource.message, context)
                    binding.refresh.isRefreshing = false
                }

                DataResource.DataStatus.SUCCESS -> {
                    binding.refresh.isRefreshing = false
                    if (dataResource.data!!.appointments.isNullOrEmpty()) {
                        scheduleRecyclerAdapter.submitList(emptyList())
                        AppUtils.message(binding.root, "No Appointment Available", context)
                    } else {
                        if (!mainList!!.isEmpty()) {
                            mainList!!.clear()
                        }
                        scheduleRecyclerAdapter.submitList(dataResource.data!!.appointments)
                        binding.recyclerView.scheduleLayoutAnimation()
                    }
                }

            }

        })
    }

    private fun initRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
//            val itemDecor = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
//            addItemDecoration(itemDecor)
            scheduleRecyclerAdapter = AppointmentAdapter(this@PatientAppointmentFragment)
            adapter = scheduleRecyclerAdapter
        }
//        glide = Glide.with(binding.root)
        glide?.let { scheduleRecyclerAdapter.setGlideRequestManager(it) }
    }

    private fun gotoPrevious() {
//        navController.popBackStack()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProviders.of(this).get(AppointmentsViewModel::class.java)
    }

    override fun onItemSelected(position: Int, item: Appointment) {

    }

    private fun getFilterData(
        models: ArrayList<Appointment>,
        searchKey: CharSequence
    ): ArrayList<Appointment>? {
        var searchKey = searchKey
        searchKey = searchKey.toString().toLowerCase()
        val filteredModelList: ArrayList<Appointment> = ArrayList<Appointment>()
        for (model in models) {
            val name: String = model.doctorName.toLowerCase()
            val status: String = model.status.toLowerCase()
            if (name.contains(searchKey) || status.contains(searchKey)) {
                filteredModelList.add(model)
            }
        }
        return filteredModelList
    }

    override fun onAttachmentClick(position: Int, item: Appointment) {
        val bundle = bundleOf(
            PATIENT_ID_KEY to patientId,
            CASE_ID_KEY to item.caseId.toString(),
        )
        Navigation.findNavController(binding.root)
            .navigate(R.id.action_patientProfileFragment_to_prescriptionViewFragment, bundle)
    }

    private fun showDatePicker(tv: TextView, c: Calendar) {
        val dpd = DatePickerDialog(
            activity as Context, R.style.DatePickerDialogTheme,
            { datePicker, yyyy, mm, dd ->
                var mm = mm
                mm += 1
                viewModel.loadData(patientId, "", "$yyyy-$mm-$dd")
                tv.setText(DateUtils.currentDateFilter("$yyyy-$mm-$dd"))
            },
            c[Calendar.YEAR],
            c[Calendar.MONTH],
            c[Calendar.DAY_OF_MONTH]
        )
        dpd.show()
    }
}
