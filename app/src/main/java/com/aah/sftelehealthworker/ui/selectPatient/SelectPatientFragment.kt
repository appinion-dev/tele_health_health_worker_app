package com.aah.sftelehealthworker.ui.selectPatient

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.aah.sftelehealthworker.R
import com.aah.sftelehealthworker.models.home.Patient
import com.aah.sftelehealthworker.ui.home.adapter.PatientsRecyclerAdapter
import kotlinx.android.synthetic.main.schedule_fragment.view.recyclerView
import kotlinx.android.synthetic.main.select_patient_fragment.view.*

class SelectPatientFragment : Fragment() {

    companion object {
        fun newInstance() = SelectPatientFragment()
    }

    private lateinit var viewModel: SelectPatientViewModel
    private lateinit var patientsRecyclerAdapter: PatientsRecyclerAdapter
    private var patients= mutableListOf<Patient>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.select_patient_fragment, container, false)

        val toolbar = (activity as AppCompatActivity?)!!.supportActionBar!!
        toolbar.show()
        toolbar.title = "Select Patient"
        initRecyclerView(view)
        view.extendedFab.setOnClickListener {
            gotoNewPatientProfile(view)
        }

        return view
    }

    private fun gotoNewPatientProfile(view: View) {
//        Navigation.findNavController(view).navigate(R.id.action_selectPatientFragment_to_patientProfileCreateFragment)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SelectPatientViewModel::class.java)
    }

    private fun initRecyclerView(view: View) {
        view.recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
//            val topSpacingDecorator = TopSpacingItemDecoration(30)
//            addItemDecoration(topSpacingDecorator)
            val itemDecor = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
            addItemDecoration(itemDecor)
            patientsRecyclerAdapter = PatientsRecyclerAdapter()
            adapter = patientsRecyclerAdapter
        }
//        getData()
    }

//    private fun getData() {
//        patients.clear()
//        for (x in 0 until 10){
//            val patient = Patient(x.toString(), "Farhan Abid" , "28 YEARS", "MALE")
//            patients.add(patient)
//        }
//        patientsRecyclerAdapter.submitList(patients)
//    }

}