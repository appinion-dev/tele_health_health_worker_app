package com.aah.sftelehealthworker.ui.patientProfile.fragments.report

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.aah.sftelehealthworker.R
import com.aah.sftelehealthworker.databinding.ReportInfoFragmentBinding
import com.aah.sftelehealthworker.utils.GlideApp

class ReportInfoFragment : Fragment() {

    companion object {
        fun newInstance() = ReportInfoFragment()
    }

    private lateinit var viewModel: ReportInfoViewModel
    private lateinit var binding: ReportInfoFragmentBinding

    private lateinit var status:String
    private lateinit var url:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        return inflater.inflate(R.layout.report_info_fragment, container, false)
        binding = DataBindingUtil.inflate(inflater, R.layout.report_info_fragment, container, false)
        viewModel = ViewModelProvider(this).get(ReportInfoViewModel::class.java)

        status = arguments?.getString("status").toString()
        url = arguments?.getString("url").toString()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()

        if (status == "image" || status == "jpg"){
            binding.imageView.visibility = View.VISIBLE
            binding.pdfView.visibility = View.GONE

            context?.let {
                GlideApp.with(it)
                    .load(url)
                    .fitCenter()
                    .placeholder(it.getDrawable(R.drawable.image_placeholder))
                    .error(it.getDrawable(R.drawable.image_placeholder))
                    .fallback(it.getDrawable(R.drawable.image_placeholder))
                    .into(binding.imageView)
            }
        }
        else if(status == "pdf"){
            binding.imageView.visibility = View.GONE
            binding.pdfView.visibility = View.VISIBLE

//            val pdfPagerAdapter = PDFPagerAdapter(activity, url)
//
//            binding.pdfViewPager.setAdapter(pdfPagerAdapter)
//            binding.pdfViewPager.setVisibility(View.VISIBLE)
        }
    }

    private fun initToolbar() {
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = status
//        setHasOptionsMenu(true)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProviders.of(this).get(ReportInfoViewModel::class.java)
    }


}