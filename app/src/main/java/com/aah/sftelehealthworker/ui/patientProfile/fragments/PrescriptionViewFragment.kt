package com.aah.sftelehealthworker.ui.patientProfile.fragments

import android.app.Activity
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.aah.sftelehealthworker.R
import com.aah.sftelehealthworker.common.BaseFragment
import com.aah.sftelehealthworker.databinding.FragmentPrescriptionViewBinding
import com.aah.sftelehealthworker.models.document.Document
import com.aah.sftelehealthworker.models.newPatient.Medicine
import com.aah.sftelehealthworker.models.newPatient.PrescriptionDetails
import com.aah.sftelehealthworker.utils.*

class PrescriptionViewFragment : BaseFragment() {
    private lateinit var navController: NavController
    var fdh: FileDownloadHelper? = null
    var downloadStatusReceiver: BroadcastReceiver? = null
    var downloadManagerIntent: IntentFilter? = null
    var isReceiverRegistered = false
    var patientId: String? = null
    var caseId: String? = null

    lateinit var binding: FragmentPrescriptionViewBinding
    private lateinit var viewModel: PrescriptionsViewModel

    companion object {
        fun newInstance() = PrescriptionViewFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        return inflater.inflate(R.layout.reports_fragment, container, false)
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_prescription_view,
            container,
            false
        )
        viewModel = ViewModelProvider(this).get(PrescriptionsViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        navController = Navigation.findNavController(view)

        caseId = arguments?.getString(CASE_ID_KEY)
        patientId = arguments?.getString(PATIENT_ID_KEY)
        if (patientId != null && arguments?.getSerializable(PRESCRIPTION_KEY) != null) {
            val prescription = arguments?.getSerializable(PRESCRIPTION_KEY) as PrescriptionDetails
            initView(prescription)
        } else {
            //presenter.getPrescription(caseId)
            viewModel.loadData(patientId!!, caseId!!)
            observeData()
            initMessage()
        }

        downloadManagerIntent = IntentFilter("android.intent.action.DOWNLOAD_COMPLETE")
        downloadStatusReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                if (intent.action == DownloadManager.ACTION_DOWNLOAD_COMPLETE) {
                    AppUtils.message(
                        binding.root,
                        "Prescription download completed",
                        Color.WHITE,
                        R.color.colorPrimary
                    )
                    //                    selectedDocumentPosition = binding.documentsViewPager.getCurrentItem();
//
//                    binding.documentsViewPager.getAdapter().notifyDataSetChanged();
//                    binding.documentsViewPager.setCurrentItem(selectedDocumentPosition);
//
//                    // open the document with the required intent for a PDF
//                    openSelectedDocument(fdh.getInternalFilePath(fdh.getLastDownloadedDocumentPath(intent)));
                }
            }
        }

        binding.back.setOnClickListener {
            navController.popBackStack()
        }

    }

    private fun initMessage() {
        viewModel.message.observe(viewLifecycleOwner, Observer {
            AppUtils.message(binding.root, it, context)
        })
    }


    private fun observeData() {
        viewModel.prescriptionsMutableLiveData.observe(viewLifecycleOwner, Observer {
            if (it.isNullOrEmpty()) {
              //  AppUtils.message(binding.root, "No prescription Found", context)
            } else {
                initView(it[0].prescriptionDetails!!)
            }
        })
    }

    private fun initView(prescription: PrescriptionDetails) {
        binding.medicalAdvice.setText(prescription.advice)
        binding.historyAndSymptoms.setText(prescription.history)
        if (prescription.medicine != null && prescription.medicine!!.size > 0) {
            for (medicine in prescription.medicine!!) {
                binding.addedMedicinesContainer.addView(setMedicineView(medicine))
            }
        }
        binding.download.setOnClickListener(View.OnClickListener {
            if (prescription.link != null && !prescription.link!!.isEmpty()) {
                if (fdh!!.isFileDownloaded(prescription.id!!.toInt())) {
                    val document = Document()
                    document.setPreviewUrl(prescription.link)
                    gotoPdfViewActivity(document)
                } else {
                    if (AppUtils.isNetworkAvailable(activity as Activity)) {
                        fdh!!.downloadFile(
                            prescription.link,
                            prescription.consultId.toString(),
                            "download in progress...",
                            prescription.id!!,
                            prescription.getDocumentFileExtension()!!
                        )
                        requireActivity().registerReceiver(
                            downloadStatusReceiver,
                            downloadManagerIntent
                        )
                        isReceiverRegistered = true
                        AppUtils.message(
                            binding.root,
                            "Prescription download in progress",
                            Color.WHITE,
                            R.color.colorPrimary
                        )
                    } else {

                        AppUtils.message(
                            binding.root,
                            "Connection not found!",
                            context
                        )
                    }
                }
            } else {
                AppUtils.message(
                    binding.root,
                    "Prescription File not found!",
                    context
                )
            }
        })
    }

    private fun gotoPdfViewActivity(document: Document) {
        /*    val intent = Intent(this, ResourceViewActivity::class.java)
            intent.putExtra("DOCUMENT", document)
            startActivity(intent)*/

        val bundle = bundleOf(DOCUMENT_KEY to document)
        Navigation.findNavController(binding.root)
            .navigate(R.id.action_prescriptionViewFragment_to_resourceViewFragment, bundle)
    }

    private fun setMedicineView(medicine: Medicine): View? {
        val view: View = getInflateView(activity as Activity)!!
        val medicineName = view.findViewById<TextView>(R.id.medicineName)
        val instructions = view.findViewById<TextView>(R.id.instructions)
        val dosage = view.findViewById<TextView>(R.id.dosage)
        val dosageMorning = view.findViewById<TextView>(R.id.dosage_morning)
        val dosageAfternoon = view.findViewById<TextView>(R.id.dosage_afternoon)
        val dosageNight = view.findViewById<TextView>(R.id.dosage_night)
        medicineName.setText(medicine.name)
        instructions.setText(medicine.instruction)
        dosage.text =
            java.lang.String.format("%s x %s days", medicine.dosage, medicine.duration)
        if (medicine.dosage!!.length === 5) {
            val dosageArray: Array<String> = medicine.dosage!!.split("-").toTypedArray()
            dosageMorning.text = dosageArray[0]
            dosageAfternoon.text = dosageArray[1]
            dosageNight.text = dosageArray[2]
        }
        return view
    }

    private fun getInflateView(context: Context): View? {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        return inflater.inflate(R.layout.row_medicines, null)
    }

    override fun onDestroy() {
        if (downloadStatusReceiver != null && isReceiverRegistered) {
            requireActivity().unregisterReceiver(downloadStatusReceiver)
            isReceiverRegistered = false
        }
        super.onDestroy()
    }


}