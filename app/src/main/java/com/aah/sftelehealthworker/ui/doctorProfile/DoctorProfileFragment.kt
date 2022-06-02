package com.aah.sftelehealthworker.ui.doctorProfile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.aah.sftelehealthworker.R
import com.aah.sftelehealthworker.databinding.DoctorProfileFragmentBinding
import com.aah.sftelehealthworker.models.appoinment.Doctor
import com.aah.sftelehealthworker.models.doctor.CallbackCost
import com.aah.sftelehealthworker.ui.adapter.UniversalPagerAdapter
import com.aah.sftelehealthworker.ui.doctorProfile.fragments.DoctorAdviceFragment
import com.aah.sftelehealthworker.ui.doctorProfile.fragments.DoctorDetailsFragment
import com.aah.sftelehealthworker.ui.doctorProfile.fragments.DoctorDocumentsFragment
import com.aah.sftelehealthworker.utils.AppUtils
import com.aah.sftelehealthworker.utils.GlideApp
import java.util.*

class DoctorProfileFragment : Fragment() {

    companion object {
        fun newInstance() = DoctorProfileFragment()
    }

    private lateinit var viewModel: DoctorProfileViewModel
    private lateinit var binding: DoctorProfileFragmentBinding
    private lateinit var navController: NavController
    private lateinit var stringFragmentMap: MutableMap<String, Fragment>

    private var doctorId = "0"
    private var patientId = "0"
    private var timeSlotId = "0"
    private var date = ""
    private var categoryId = "0"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        return inflater.inflate(R.layout.doctor_profile_fragment, container, false)
        binding =
            DataBindingUtil.inflate(inflater, R.layout.doctor_profile_fragment, container, false)
        viewModel = ViewModelProvider(this).get(DoctorProfileViewModel::class.java)

        doctorId = arguments?.getString("doctorId").toString()
        patientId = arguments?.getString("patientId").toString()
        timeSlotId = arguments?.getString("timeSlotId").toString()
        date = arguments?.getString("date").toString()
        categoryId = arguments?.getString("categoryId").toString()

        AppUtils.log(
            "DoctorProfileLog",
            "doctorId: $doctorId ,patientId: $patientId , timeSlotId: $timeSlotId ,date: $date , categoryId: $categoryId"
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Doctor Profile"

        navController = Navigation.findNavController(binding.root)
        viewModel.loadData(doctorId, timeSlotId)
        viewModel.setSuccessFullFalse()

        initInfo()
        initSuccessful()
        initMessage()
//        initViewPager()

        binding.confirm.setOnClickListener {
            viewModel.createAppointment(
                doctorId.toInt(),
                patientId.toInt(),
                timeSlotId.toInt(),
                date,
                categoryId.toInt()
            )
        }
    }

    private fun initMessage() {
        viewModel.message.observe(viewLifecycleOwner, Observer {
            AppUtils.message(binding.root, it,context)
        })
    }

    private fun initSuccessful() {
        viewModel.isSuccessFull.observe(viewLifecycleOwner, Observer {
            if (it) {
                gotoHome()
            }
        })
    }

    private fun gotoHome() {
        navController.navigate(R.id.action_doctorProfileFragment_to_homeFragment)
    }

    private fun initInfo() {
        viewModel.doctorsInfoModelMutableLiveData.observe(viewLifecycleOwner, Observer {

            it.doctor?.let { it1 -> showInfo(it1) }
            it.callbackCost?.let { it1 -> showRate(it1) }
        })
    }

    private fun showRate(callbackCost: CallbackCost) {
        val rate = "৳ ${callbackCost.callbackRate}"
        binding.rate.text = rate
    }

    private fun showInfo(doctor: Doctor) {
        GlideApp
            .with(this)
            .load(doctor.image)
            .centerCrop()
            .placeholder(context?.getDrawable(R.drawable.ic_healthcare_and_medical))
            .error(context?.getDrawable(R.drawable.ic_healthcare_and_medical))
            .fallback(context?.getDrawable(R.drawable.ic_healthcare_and_medical))
            .into(binding.image)

//        doctor.callbackCost?.callbackRate
//        doctor.callbackCost?.let {
//            val rate = "৳ ${it.callbackRate}"
//            binding.rate.text = rate
//        }
//        val rate = "৳ ${doctor.callbackCost?.callbackRate}"
//        binding.rate.text = rate
        val name = "${doctor.title} ${doctor.firstName} ${doctor.lastName}"
        binding.name.text = name

        binding.experience.text = getExperience(doctor.workingSince)
        binding.qualification.text = doctor.qualification

//        if (::doctor.isInitialized){
        binding.regNo.text = doctor.regNo
        binding.language.text = doctor.languages
        binding.workPlace.text = doctor.hospital
        binding.location.text = doctor.location
//        }

//        initViewPager(doctor)
    }

    private fun getExperience(workingSince: Int): String {
        val presentYear = Calendar.getInstance().get(Calendar.YEAR)
        val expYear = (presentYear - workingSince)
        return "EXP $expYear YEARS"
    }

//    private fun initViewPager(doctor: Doctor) {
//        binding.slidingTabs.setupWithViewPager(binding.viewpager)
//        binding.viewpager.offscreenPageLimit = 2
//        setupFragments(doctor)
//        val adapter = UniversalPagerAdapter(childFragmentManager, stringFragmentMap)
//        binding.viewpager.adapter = adapter
//    }

//    private fun setupFragments(doctor: Doctor) {
//
//        stringFragmentMap = LinkedHashMap()
//
//        val doctorDetailsFragment = DoctorDetailsFragment()
//        val doctorAdviceFragment = DoctorAdviceFragment()
//        val doctorDocumentsFragment = DoctorDocumentsFragment()
//
//        doctorDetailsFragment.setInfo(doctor)
//
//        val bundle = Bundle()
//        stringFragmentMap.put(resources.getString(R.string.detail), doctorDetailsFragment as Fragment)
//        stringFragmentMap.put(resources.getString(R.string.advice), doctorAdviceFragment as Fragment)
//        stringFragmentMap.put(resources.getString(R.string.documents), doctorDocumentsFragment as Fragment)
//    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProviders.of(this).get(DoctorProfileViewModel::class.java)
    }

}