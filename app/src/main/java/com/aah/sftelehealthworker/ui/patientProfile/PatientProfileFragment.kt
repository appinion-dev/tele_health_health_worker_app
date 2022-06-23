package com.aah.sftelehealthworker.ui.patientProfile

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.aah.sftelehealthworker.R
import com.aah.sftelehealthworker.databinding.PatientProfileFragmentBinding
import com.aah.sftelehealthworker.ui.MainActivity
import com.aah.sftelehealthworker.ui.adapter.UniversalPagerAdapter
import com.aah.sftelehealthworker.ui.patientProfile.fragments.*
import com.aah.sftelehealthworker.utils.AppUtils
import com.aah.sftelehealthworker.utils.GlideApp
import com.google.gson.Gson
import gun0912.tedimagepicker.builder.TedImagePicker
import id.zelory.compressor.Compressor
import kotlinx.coroutines.launch
import java.io.File
import java.util.LinkedHashMap

class PatientProfileFragment : Fragment() {

    companion object {
        fun newInstance() = PatientProfileFragment()
    }

    private lateinit var viewModel: PatientProfileViewModel
    private lateinit var binding: PatientProfileFragmentBinding
    private lateinit var stringFragmentMap: MutableMap<String, Fragment>
    private lateinit var patientId: String
    private lateinit var patientPhone: String
    private lateinit var patientName: String
    private lateinit var patientAge: String
    private lateinit var patientGender: String

    private lateinit var uri: Uri

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        val view =  inflater.inflate(R.layout.patient_profile_fragment, container, false)
        binding =
            DataBindingUtil.inflate(inflater, R.layout.patient_profile_fragment, container, false)
        viewModel = ViewModelProvider(this).get(PatientProfileViewModel::class.java)

        patientId = arguments?.getString("id").toString()
        patientPhone = arguments?.getString("phone").toString()
        patientName = arguments?.getString("name").toString()
        patientAge = arguments?.getString("age").toString()
        patientGender = arguments?.getString("gender").toString()

        AppUtils.log("TestTry", Gson().toJson(patientId))

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initProfile()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Patient Profile"

        viewModel.loadData(patientId)
        binding.slidingTabs.setupWithViewPager(binding.viewpager)
        binding.viewpager.offscreenPageLimit = 5
        stringFragmentMap = LinkedHashMap()
        setupFragments()
        val adapter = UniversalPagerAdapter(childFragmentManager, stringFragmentMap)
        binding.viewpager.adapter = adapter

        binding.image.setOnClickListener {
            checkReadWritePermission()
        }
    }

    private fun checkReadWritePermission() {
        if ((activity as MainActivity).checkReadWritePermission()) {
            TedImagePicker.with(activity as MainActivity)
                .start { uri -> showSingleImage(uri) }
        }
    }

    private fun showSingleImage(uri: Uri) {
        this.uri = uri
        GlideApp.with(this)
            .load(uri)
            .circleCrop()
            .placeholder(context?.getDrawable(R.drawable.person_male))
            .error(context?.getDrawable(R.drawable.person_male))
            .fallback(context?.getDrawable(R.drawable.person_male))
            .into(binding.image)

        val file = File(uri.path)
        file.let { file ->
            lifecycleScope.launch {
                val compressedImageFile = context?.let { it1 -> Compressor.compress(it1, file) }
                compressedImageFile?.let { it1 ->
                    viewModel.uploadImage(patientId, it1)
                }
            }
        }
    }

    private fun initProfile() {

//        viewModel.patientProfileMutableLiveData.observe(viewLifecycleOwner, Observer {
//            binding.name.text = it.firstName + " " + it.lastName
//            val age = it.age + " Years"
//            binding.age.text = age
//            binding.sex.text = it.gender
//
//            GlideApp
//                .with(this)
//                .load(it.image)
//                .circleCrop()
//                .placeholder(context?.getDrawable(R.drawable.person_male))
//                .error(context?.getDrawable(R.drawable.person_male))
//                .fallback(context?.getDrawable(R.drawable.person_male))
//                .into(binding.image)
//        })
        binding.name.text = patientName
        val age = "$patientAge Years"
        binding.age.text = age
        binding.sex.text = patientGender

        GlideApp
            .with(this)
            .load(R.drawable.person_male)
            .circleCrop()
            .placeholder(context?.getDrawable(R.drawable.person_male))
            .error(context?.getDrawable(R.drawable.person_male))
            .fallback(context?.getDrawable(R.drawable.person_male))
            .into(binding.image)
    }

    private fun compraceImage() {
        val file = File(uri.path)
        file.let { file ->
            lifecycleScope.launch {
                val compressedImageFile = context?.let { it1 -> Compressor.compress(it1, file) }
                compressedImageFile?.let { it1 ->
//                    viewModel.submitData(it1, binding.fileName.text.toString(), binding.note.text.toString(), patientId.toInt(), "image")
                }
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//      viewModel = ViewModelProviders.of(this).get(PatientProfileViewModel::class.java)
    }

    private fun setupFragments() {
       // val assessmentFragment = AssessmentFragment()
        val appointmentFragment = PatientAppointmentFragment()
        val vitalsFragment = VitalsFragment()
        val prescriptionFragment = PrescriptionsFragment()
        val reportsFragment = ReportsFragment()

//        patientProfile.phone?.let {
//            appointmentFragment.setPreviousPhoneNo(patientPhone)
//            appointmentFragment.setPatientPhoneNo(patientPhone)
//        }

        //assessmentFragment.setPatientId(patientId)
        appointmentFragment.setPreviousPhoneNo(patientPhone)
        appointmentFragment.setPatientPhoneNo(patientPhone)
        appointmentFragment.setPatientId(patientId)

        vitalsFragment.setPatientId(patientId)
        reportsFragment.setPatientId(patientId)
        prescriptionFragment.setPatientId(patientId)

        val bundle = Bundle()
    /*    stringFragmentMap.put(
            resources.getString(R.string.assessments),
            assessmentFragment as Fragment
        )*/
        stringFragmentMap.put(
            resources.getString(R.string.appointments),
            appointmentFragment as Fragment
        )
        stringFragmentMap.put(resources.getString(R.string.vitals), vitalsFragment as Fragment)
        stringFragmentMap.put(
            resources.getString(R.string.prescription),
            prescriptionFragment as Fragment
        )
        stringFragmentMap.put(resources.getString(R.string.reports), reportsFragment as Fragment)
    }

}