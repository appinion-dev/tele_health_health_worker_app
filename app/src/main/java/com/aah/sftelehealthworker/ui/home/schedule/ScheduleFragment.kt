package com.aah.sftelehealthworker.ui.home.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.aah.sftelehealthworker.R
import com.aah.sftelehealthworker.models.home.Appointment
import com.aah.sftelehealthworker.ui.home.adapter.ScheduleRecyclerAdapter
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.schedule_fragment.view.*

class ScheduleFragment : Fragment() {

    companion object {
        fun newInstance() = ScheduleFragment()
    }

    private lateinit var viewModel: ScheduleViewModel
    private lateinit var scheduleRecyclerAdapter: ScheduleRecyclerAdapter
    private var schedule= mutableListOf<Appointment>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.schedule_fragment, container, false)

        val glide = Glide.with(view.context)
        initRecyclerView(view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ScheduleViewModel::class.java)
    }

    private fun initRecyclerView(view: View) {
        view.recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
//            val topSpacingDecorator = TopSpacingItemDecoration(30)
//            addItemDecoration(topSpacingDecorator)
//            val itemDecor = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
//            addItemDecoration(itemDecor)
            scheduleRecyclerAdapter = ScheduleRecyclerAdapter()
            adapter = scheduleRecyclerAdapter
        }
//        getData()
    }

//    private fun getData() {
//        schedule.clear()
//        for (x in 0 until 10){
//            val patientSchedule = Appointment(x.toString(), "Nayem Islam" , "May 14, 2020 | 04.00 PM", "Callback Requested to Dr. Arman Hossain")
//            schedule.add(patientSchedule)
//        }
//        scheduleRecyclerAdapter.submitList(schedule)
//    }

}