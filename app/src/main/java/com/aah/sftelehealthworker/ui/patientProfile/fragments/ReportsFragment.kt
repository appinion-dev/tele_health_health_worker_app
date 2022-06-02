package com.aah.sftelehealthworker.ui.patientProfile.fragments

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.aah.sftelehealthworker.R
import com.aah.sftelehealthworker.databinding.ReportsFragmentBinding
import com.aah.sftelehealthworker.models.newPatient.Report
import com.aah.sftelehealthworker.ui.patientProfile.adapter.ReportRecyclerAdapter
import com.aah.sftelehealthworker.utils.AppUtils

class ReportsFragment : Fragment(), ReportRecyclerAdapter.Interaction {

    companion object {
        fun newInstance() = ReportsFragment()
    }

    private lateinit var viewModel: ReportsViewModel
    private lateinit var binding: ReportsFragmentBinding
    private lateinit var navController: NavController

    private lateinit var reportRecyclerAdapter: ReportRecyclerAdapter

    private var patientId: String = ""
    var isOpen = false

    public fun setPatientId(patientId: String) {
        this.patientId = patientId
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        return inflater.inflate(R.layout.reports_fragment, container, false)
        binding = DataBindingUtil.inflate(inflater, R.layout.reports_fragment, container, false)
        viewModel = ViewModelProvider(this).get(ReportsViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(binding.root)
        initAdapter()
        initMessage()
        initRefresh()

        viewModel.loadData(patientId)

        binding.extendedFab.setOnClickListener {
            if (isOpen) {
                isOpen = false
                binding.addImage.visibility = View.GONE
                binding.addPdf.visibility = View.GONE
            } else {
                isOpen = true
                binding.addImage.visibility = View.VISIBLE
                binding.addPdf.visibility = View.VISIBLE

                binding.addImage.setOnClickListener {
                    gotoSubmitReport("image")
                }
                binding.addPdf.setOnClickListener {
                    gotoSubmitReport("pdf")
                }
            }
        }
    }

    private fun initMessage() {
        viewModel.message.observe(viewLifecycleOwner, Observer {
            AppUtils.message(binding.root, it, context)
        })
    }

    private fun initRefresh() {

        binding.refresh.setOnRefreshListener {
            viewModel.loadData(patientId)
        }

        viewModel.refresh.observe(viewLifecycleOwner, Observer {
            binding.refresh.isRefreshing = it
        })
    }

    private fun initAdapter() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            reportRecyclerAdapter = ReportRecyclerAdapter(this@ReportsFragment)
            adapter = reportRecyclerAdapter
        }

        viewModel.reportMutableLiveData.observe(viewLifecycleOwner, Observer {
            if (it.isNullOrEmpty()) {
                AppUtils.message(binding.root, "No report found", context)
                reportRecyclerAdapter.submitList(emptyList())
            } else {
                reportRecyclerAdapter.submitList(it)
                binding.recyclerView.scheduleLayoutAnimation()
            }
        })


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProviders.of(this).get(ReportsViewModel::class.java)
    }

    override fun onItemSelected(position: Int, item: Report) {
        gotoReportInfo(item, getDocumentType(item))
//        getDocumentType(item)
    }

    private fun getDocumentType(item: Report): String {
        var dataType = "jpg"
        val i: Int = item.previewUrl!!.lastIndexOf('.')
        if (i > 0) {
            dataType = item.previewUrl!!.substring(i + 1)
        }
        return if (dataType == "pdf") "pdf" else "image"
    }

    private fun gotoSubmitReport(status: String) {
        val bundle = bundleOf("patientId" to patientId, "status" to status)
//        action_patientProfileFragment_to_reportSubmitFragment
        navController.navigate(R.id.action_patientProfileFragment_to_reportSubmitFragment, bundle)
    }

    private fun gotoReportInfo(item: Report, status: String) {
        if (status == "image") {
            val bundle =
                bundleOf("patientId" to patientId, "url" to item.previewUrl, "status" to status)
//        action_patientProfileFragment_to_reportSubmitFragment
            navController.navigate(R.id.action_patientProfileFragment_to_reportInfoFragment, bundle)
        } else {
            openOptions(item)
        }
    }

    private fun openOptions(item: Report) {
//        val browserIntent = Intent(Intent.ACTION_VIEW)
//        browserIntent.setDataAndType(Uri.parse(item.previewUrl), Constants.MIME_PDF)
//
//        val chooser = Intent.createChooser(browserIntent, getString(R.string.chooser_title))
//        chooser.flags = Intent.FLAG_ACTIVITY_NEW_TASK // optional
//
//        startActivity(chooser)

        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(item.previewUrl))
        startActivity(browserIntent)
    }
}