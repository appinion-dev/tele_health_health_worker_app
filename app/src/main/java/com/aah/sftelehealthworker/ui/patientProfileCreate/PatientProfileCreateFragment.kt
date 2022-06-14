package com.aah.sftelehealthworker.ui.patientProfileCreate

import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.aah.sftelehealthworker.R
import com.aah.sftelehealthworker.common.BaseFragment
import com.aah.sftelehealthworker.databinding.PatientProfileCreateFragmentBinding
import com.aah.sftelehealthworker.models.newPatient.District
import com.aah.sftelehealthworker.models.newPatient.PatientProfile
import com.aah.sftelehealthworker.models.newPatient.Upazilla
import com.aah.sftelehealthworker.ui.MainActivity
import com.aah.sftelehealthworker.utils.AppUtils
import com.aah.sftelehealthworker.utils.GlideApp
import com.aah.sftelehealthworker.utils.PHONE_NO
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import gun0912.tedimagepicker.builder.TedImagePicker
import id.zelory.compressor.Compressor
import kotlinx.android.synthetic.main.patient_profile_create_fragment.*
import kotlinx.coroutines.launch
import java.io.File


class PatientProfileCreateFragment : BaseFragment() {
    private var lastClickTime: Long = 0

    companion object {
        fun newInstance() = PatientProfileCreateFragment()
    }

    private lateinit var viewModel: PatientProfileCreateViewModel
    private lateinit var binding: PatientProfileCreateFragmentBinding
    private var phoneNo: String = ""
    private lateinit var district: List<District>
    private lateinit var upazilla: List<Upazilla>
    private lateinit var patientProfile: PatientProfile
    private lateinit var navController: NavController
    private lateinit var uri: Uri
    var data: String? = null;
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        val view = inflater.inflate(R.layout.patient_profile_create_fragment, container, false)
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.patient_profile_create_fragment,
            container,
            false
        )
        viewModel = ViewModelProvider(this).get(PatientProfileCreateViewModel::class.java)
        phoneNo = arguments?.getString(PHONE_NO).toString()
//        view.done.setOnClickListener {
//            gotoPatientProfile(view)
//        }
        mActivity = activity


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(binding.root)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()


        //Spinner Section Start
        val financialCategory = arrayOf("Poor Patient", "Ultra Poor Patient")
        val myAdapter = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_item,
            financialCategory
        )
        spinner.adapter = myAdapter
        spinner.onItemSelectedListener = object :
            AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                p0: AdapterView<*>?,
                view: View?,
                position: Int,
                p3: Long
            ) {

                data = financialCategory[position]


            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {}
        }
        //Spinner Section End

        initDistrict()
        initSuccessful()
        initPatientProfile()
        initImage()
        viewModel.setSuccessFullFalse()

        binding.back.setOnClickListener {
            navController.popBackStack()
        }

        binding.districtLabel.setOnClickListener {
            if (::district.isInitialized) {
                selectDistrict(district)
            }
        }

        binding.upazilaLabel.setOnClickListener {
            if (::upazilla.isInitialized) {
                selectUpazillas(upazilla)
            } else {
                AppUtils.message(binding.root, "Please select district first", context)
            }
        }


        binding.done.setOnClickListener {
            if (SystemClock.elapsedRealtime() - lastClickTime < 1000) {
                return@setOnClickListener;
            }
            lastClickTime = SystemClock.elapsedRealtime();

            if (!AppUtils.isNetworkAvailable(requireContext())) {
                AppUtils.message(
                    view,
                    getString(R.string.internet_not_available),
                    Color.WHITE,
                    ContextCompat.getColor(requireActivity(), R.color.colorPrimary), 5000
                )
                return@setOnClickListener

            }
            if (isValid()) {
                showProgressDialog()
                if (::uri.isInitialized) {
                    val file = File(uri.path)
                    file.let { file ->
                        lifecycleScope.launch {
                            val compressedImageFile =
                                context?.let { it1 -> Compressor.compress(it1, file) }
                            compressedImageFile?.let { it1 ->
                                viewModel.loadData(
                                    phoneNo,
                                    binding.fullName.text.toString(),
                                    binding.age.text.toString(),
                                    getGender(),
                                    getSajidaBenificiary(),
                                    financialCategory(),
                                    getUpazillaId(),
                                    it1
                                )
                            }
                        }
                    }
                } else {
                    viewModel.loadData(
                        phoneNo,
                        binding.fullName.text.toString(),
                        binding.age.text.toString(),
                        getGender(),
                        getSajidaBenificiary(),
                        financialCategory(),
                        getUpazillaId()
                    )
                }
            }
        }
    }

    private fun initImage() {
        binding.image.setOnClickListener {
            checkReadWritePermission()
        }
    }

    private fun checkReadWritePermission() {
        if ((activity as MainActivity).checkReadWritePermission()) {
//            dispatchGalleryIntent()
//            mDialog.dismiss()
//            (activity as MainActivity).getImage()
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
    }

    private fun initPatientProfile() {
        viewModel.patientProfileMutableLiveData.observe(viewLifecycleOwner, Observer {
            it.patientProfile?.let {
                patientProfile = it
                hideProgressDialog()
            }
        })
    }

    private fun initSuccessful() {
        viewModel.isSuccessFull.observe(viewLifecycleOwner, Observer {
            if (it) {
                hideProgressDialog()
                AppUtils.message(binding.root, "Patient added successfully!", context)
                gotoPatientProfile()
            }
        })
    }

    private fun getUpazillaId(): String {
        var upazilaId = ""
        upazilla.forEach {
            if (it.name == binding.upazila.text)
                upazilaId = it.id.toString()
        }
        return upazilaId
    }

    private fun getGender(): String {
        return if (binding.male.isChecked) {
            "Male"
        } else if (binding.female.isChecked) {
            "Female"
        } else {
            ""
        }
    }

    private fun getSajidaBenificiary(): Int {

        var value: Int? = null
        if (binding.Yes.isChecked) {
            value = 1
        } else if (binding.No.isChecked) {
            value = 0
        }
        return value!!
    }

    private fun financialCategory(): Int {
        var value: Int? = null
        if (data.equals("Poor Patient")) {
            value = 1
        }
        if (data.equals("Ultra Poor Patient")) {
            value = 0
        }
        return value!!
    }

    private fun isSajidaBenificiary(): Boolean {
        return if (binding.radioSajidaBenificiary.checkedRadioButtonId == -1) {
            AppUtils.message(binding.root, "Please select a Yes or No", context)
            false
        } else {
            true
        }
    }

    private fun isSelectCategory(): Boolean {
        return if (data == null) {
            AppUtils.message(binding.root, "Please select a Category", context)
            false
        } else {
            true
        }
    }

    private fun isValid(): Boolean {
//        return isGendernSelected() && isNameGiven() && isAgeGiven() && isUpazillaGiven()
        return isGenderSelected() && isNameGiven() && isAgeGiven() && isUpazillaGiven() && isSajidaBenificiary() && isSelectCategory()
//        isGendernSelected()
//        isNameGiven()
//        isAgeGiven()
//        isUpazillaGiven()
    }

    private fun isImageGiven(): Boolean {
        if (::uri.isInitialized) {
            return true
        } else {
            AppUtils.message(binding.root, "Please select a Image", context)
            return false
        }
    }

    private fun isUpazillaGiven(): Boolean {
        return if (binding.upazila.text.isNullOrEmpty() || binding.upazila.text == resources.getString(
                R.string.select_upazila
            )
        ) {
            AppUtils.message(binding.root, "Please select a upazila", context)
            false
        } else {
            true
        }
    }

    private fun isAgeGiven(): Boolean {
        return if (binding.age.text.isNullOrEmpty()) {
            AppUtils.message(binding.root, "Please fill the Age field", context)
            false
        } else {
            true
        }
    }

    private fun isNameGiven(): Boolean {
        return if (binding.fullName.text.isNullOrEmpty()) {
            AppUtils.message(binding.root, "Please fill the Full Name field", context)
            false
        } else {
            true
        }
    }

    private fun isGenderSelected(): Boolean {
        return if (binding.radioGroup.checkedRadioButtonId == -1) {
            AppUtils.message(binding.root, "Please select a Gender", context)
            false
        } else {
            true
        }
    }

    private fun initDistrict() {

        viewModel.loadDistrictUpazillas()

        viewModel.districtMutableLiveData.observe(viewLifecycleOwner, Observer {
            district = it
            AppUtils.log("testDis", Gson().toJson(district))
        })
    }

    private fun gotoPatientProfile() {
//        val patientProfile = PatientProfile()
        val bundle = bundleOf("id" to patientProfile.id.toString(), "phone" to patientProfile.phone)
        Navigation.findNavController(binding.root).navigate(
            R.id.action_patientProfileCreateFragment_to_homeFragment,
            bundle
        )
    }

    private fun selectDistrict(district: List<District>) {
//        val checkedItem = -1
        val districtList = mutableListOf<String>()
        district.forEach { district ->
            district.name?.let { name -> districtList.add(name) }
        }

        context?.let {
            MaterialAlertDialogBuilder(it)
                .setTitle(resources.getString(R.string.select_district))
                .setItems(districtList.toTypedArray()) { dialog, which ->
                    binding.district.text = districtList[which]
                    findUpazillas(district, districtList[which])
                }
                .show()
        }
    }

    private fun findUpazillas(district: List<District>, districtName: String) {
//        var districtTemp = District()
        district.forEach {
            if (it.name == districtName) {
//                districtTemp = it
                it.upazillas?.let { it1 ->
                    upazilla = it1
                    selectUpazillas(it1)
                }
            }
        }
    }

    private fun selectUpazillas(upazilla: List<Upazilla>) {
        val upazillaList = mutableListOf<String>()
        upazilla.forEach { upazilla ->
            upazilla.name?.let { name -> upazillaList.add(name) }
        }
        context?.let {
            MaterialAlertDialogBuilder(it)
                .setTitle(resources.getString(R.string.select_upazila))
                .setItems(upazillaList.toTypedArray()) { dialog, which ->
                    binding.upazila.text = upazillaList[which]
                }
                .show()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProviders.of(this).get(PatientProfileCreateViewModel::class.java)
    }

}