package com.aah.sftelehealthworker.ui.home.appointments

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.aah.sftelehealthworker.R
import com.aah.sftelehealthworker.databinding.AppointmentsFragmentBinding
import com.aah.sftelehealthworker.models.home.Appointment
import com.aah.sftelehealthworker.ui.home.adapter.ScheduleRecyclerAdapter
import com.aah.sftelehealthworker.utils.AppUtils
import com.aah.sftelehealthworker.utils.CASE_ID_KEY
import com.aah.sftelehealthworker.utils.DateUtils
import com.aah.sftelehealthworker.utils.PATIENT_ID_KEY
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.*

class AppointmentsFragment : Fragment(), ScheduleRecyclerAdapter.Interaction {
    private var calendar: Calendar? = null

    companion object {
        fun newInstance() = AppointmentsFragment()
    }

    private lateinit var viewModel: AppointmentsViewModel
    private lateinit var binding: AppointmentsFragmentBinding
    private lateinit var scheduleRecyclerAdapter: ScheduleRecyclerAdapter
    private var previousPhoneNo: String = ""
    private var patientPhoneNo: String = ""
    private var patientId: String = ""
    private var glide: RequestManager? = null
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
        viewModel = ViewModelProvider(this).get(AppointmentsViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        glide = Glide.with(binding.root)
//        navController = Navigation.findNavController(binding.root)
        calendar = Calendar.getInstance()

        initRecyclerView()
        initPatientList()
        initMessage()
        initSearchView()
        initRefresh()
        initFab()

        binding.tvDate.text = DateUtils.currentDate()
        viewModel.loadData("", previousPhoneNo, "")

        binding.sort.setOnClickListener {
            showDialog()
        }

        binding.refresh.setOnRefreshListener {
            binding.sort.text = resources.getString(R.string.all_appiontments)
            binding.searchView.text = null
            viewModel.loadData("", previousPhoneNo, "")
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
                    viewModel.loadData(options[which].toLowerCase(), previousPhoneNo, "")
                }
                .show()
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
                viewModel.loadData("", s.toString(), "")
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
        binding.tvDate.setOnClickListener {
            showDatePicker(binding.tvDate, calendar!!)
        }
    }

    private fun initMessage() {
        viewModel.message.observe(viewLifecycleOwner, Observer {
            AppUtils.message(binding.root, it, context)
        })
    }

    private fun initPatientList() {
        viewModel.appointmentMutableLiveData.observe(viewLifecycleOwner, Observer {
            if (it.isNullOrEmpty()) {
                scheduleRecyclerAdapter.submitList(emptyList())
                AppUtils.message(binding.root, "No Appointment Available", context)
            } else {
                scheduleRecyclerAdapter.submitList(it)
                binding.recyclerView.scheduleLayoutAnimation()

            }
        })
    }

    private fun initRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
//            val itemDecor = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
//            addItemDecoration(itemDecor)
            scheduleRecyclerAdapter = ScheduleRecyclerAdapter(this@AppointmentsFragment)
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

    private fun showDatePicker(tv: TextView, c: Calendar) {
        val dpd = DatePickerDialog(
            activity as Context, R.style.DatePickerDialogTheme,
            { datePicker, yyyy, mm, dd ->
                var mm = mm
                mm += 1
                viewModel.loadData("", "", "$yyyy-$mm-$dd")
                tv.setText(DateUtils.currentDateFilter("$yyyy-$mm-$dd"))
            },
            c[Calendar.YEAR],
            c[Calendar.MONTH],
            c[Calendar.DAY_OF_MONTH]
        )
        dpd.show()
    }

    override fun onItemSelected(position: Int, item: Appointment) {

    }

    override fun onAttachmentClick(position: Int, item: Appointment) {
        val bundle = bundleOf(
            PATIENT_ID_KEY to patientId,
            CASE_ID_KEY to item.caseId.toString(),
        )
        Navigation.findNavController(binding.root)
            .navigate(R.id.action_homeFragment_to_prescriptionViewFragment, bundle)
    }
}