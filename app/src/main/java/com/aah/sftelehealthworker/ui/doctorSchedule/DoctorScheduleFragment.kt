package com.aah.sftelehealthworker.ui.doctorSchedule

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.CalendarView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.aah.sftelehealthworker.R
import com.aah.sftelehealthworker.databinding.DoctorScheduleFragmentBinding
import com.aah.sftelehealthworker.models.appoinment.TimeSlot
import com.aah.sftelehealthworker.ui.doctorSchedule.adapter.DoctorScheduleAdapter
import com.aah.sftelehealthworker.utils.AppUtils
import java.util.*

class DoctorScheduleFragment : Fragment() , DoctorScheduleAdapter.Interaction{

    companion object {
        fun newInstance() = DoctorScheduleFragment()
    }

    private lateinit var viewModel: DoctorScheduleViewModel
    private lateinit var binding: DoctorScheduleFragmentBinding
    private lateinit var doctorScheduleAdapter: DoctorScheduleAdapter
    private lateinit var selectedDate: String
    private lateinit var navController: NavController
    private var categoryId = ""
    private var doctorId = ""
    private var doctorName = ""
    private var patientId : String = ""
    private lateinit var today:String
    private var isOpen = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        return inflater.inflate(R.layout.doctor_schedule_fragment, container, false)

        binding = DataBindingUtil.inflate(inflater, R.layout.doctor_schedule_fragment, container, false)
        viewModel = ViewModelProvider(this).get(DoctorScheduleViewModel::class.java)

        categoryId = arguments?.getString("categoryId").toString()
        doctorId = arguments?.getString("doctorId").toString()
        doctorName = arguments?.getString("doctorName").toString()
        patientId = arguments?.getString("patientId").toString()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()

        navController = Navigation.findNavController(binding.root)

//        val todayInMil = Calendar.getInstance().timeInMillis
        binding.calendarView.minDate = AppUtils.todayMillis

        initAdapter()
        initMessage()
        initRefresh()
        setCalenderVisibility()

        setDate(AppUtils.todayMillis)

        binding.calendarView.setOnDateChangeListener(object : CalendarView.OnDateChangeListener{
            override fun onSelectedDayChange(p0: CalendarView, year: Int, month :Int, day: Int) {

                //yyyy-MM-dd
                val date = AppUtils.dateToMillisecond("$year-${month+1}-$day")
                setDate(date.toLong())
//                Toast.makeText(context , "$year-${month+1}-$day", Toast.LENGTH_LONG).show()
            }
        })

    }

    private fun initToolbar() {
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Step 3: Select Slot"
        setHasOptionsMenu(true)
    }

    private fun setDate(timeInMillis: Long) {

//        val todayInMil = Calendar.getInstance().timeInMillis
        today = AppUtils.millisToDateString( timeInMillis, "yyyy-MM-dd", Locale.getDefault().language )
        binding.date.text = AppUtils.millisToDateString( timeInMillis, "dd MMM, yyyy", Locale.getDefault().language )
        AppUtils.log("Test", today)

//        binding.calendarView.minDate = todayInMil
        viewModel.loadData( doctorName, doctorId, categoryId, today)
    }

    private fun initAdapter() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
//            val itemDecor = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
//            addItemDecoration(itemDecor)
            doctorScheduleAdapter = DoctorScheduleAdapter(this@DoctorScheduleFragment)
            adapter = doctorScheduleAdapter
        }

        viewModel.timeSlotMutableLiveData.observe(viewLifecycleOwner, Observer {
            if(it.isNullOrEmpty()){
                AppUtils.message(binding.root, "No time slots available",context)
                doctorScheduleAdapter.submitList(emptyList())
            }
            else {
                doctorScheduleAdapter.submitList(it)
            }
        })
    }

    private fun initMessage() {
        viewModel.message.observe(viewLifecycleOwner, Observer {
            AppUtils.message(binding.root, it,context)
        })
    }

    private fun initRefresh() {

        binding.refresh.setOnRefreshListener {
//            viewModel.loadData(doctorName, doctorId, categoryId, AppUtils.todayMillis.toString())
            setDate(AppUtils.todayMillis)
        }

        viewModel.refresh.observe(viewLifecycleOwner, Observer {
            binding.refresh.isRefreshing = it
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProviders.of(this).get(DoctorScheduleViewModel::class.java)
    }

    override fun onItemSelected(position: Int, item: TimeSlot) {
        gotoDoctorProfile(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.schedule_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.schedule -> {
                setCalenderVisibility()
                false
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun setCalenderVisibility() {
        if(isOpen){
            binding.calendarView.visibility = View.GONE
            isOpen = false
        }
        else{
            binding.calendarView.visibility = View.VISIBLE
            isOpen = true
        }
    }

    private fun gotoDoctorProfile(item: TimeSlot) {
        //11-10-2020 05:30 pm
        val date = AppUtils.changeDateFormat(item.startTime, "dd-MM-yyyy h:mm a", "yyyy-MM-dd")
        val bundle = bundleOf("categoryId" to categoryId , "doctorId" to doctorId, "doctorName" to doctorName, "timeSlotId" to item.timeslotId.toString(), "date" to date,  "patientId" to patientId)
        navController.navigate(R.id.action_doctorScheduleFragment_to_doctorProfileFragment, bundle)
    }

}