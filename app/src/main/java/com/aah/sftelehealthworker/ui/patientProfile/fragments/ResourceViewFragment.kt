package com.aah.sftelehealthworker.ui.patientProfile.fragments

import android.graphics.Color
import android.net.wifi.WifiConfiguration.AuthAlgorithm.strings
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.aah.sftelehealthworker.R
import com.aah.sftelehealthworker.databinding.FragmentResourceViewBinding
import com.aah.sftelehealthworker.models.document.Document
import com.aah.sftelehealthworker.utils.AppUtils
import com.aah.sftelehealthworker.utils.DOCUMENT_KEY
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.io.BufferedInputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class ResourceViewFragment : Fragment() {
    private lateinit var navController: NavController
    lateinit var binding: FragmentResourceViewBinding
    private lateinit var viewModel: PrescriptionsViewModel
    private var document: Document? = null

    private lateinit var retrievePDFStream: RetrievePDFStream

    companion object {
        fun newInstance() = ResourceViewFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        return inflater.inflate(R.layout.reports_fragment, container, false)
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_resource_view,
            container,
            false
        )
        viewModel = ViewModelProvider(this).get(PrescriptionsViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(binding.root)

        document = arguments?.getParcelable(DOCUMENT_KEY)

        if (document!!.documentType.equals("image")) {
            binding.pdfView.setVisibility(View.GONE)
            binding.imageView.setVisibility(View.VISIBLE)
            binding.loadingProgressBar.setVisibility(View.GONE)
            Glide.with(this)
                .load(document!!.previewUrl)
                .apply(
                    RequestOptions().placeholder(R.drawable.person_male)
                        .error(R.drawable.person_male)
                ).into(binding.imageView)
        } else {
            binding.pdfView.setVisibility(View.VISIBLE)
            binding.imageView.setVisibility(View.GONE)
            retrievePDFStream = RetrievePDFStream(binding)
            retrievePDFStream.execute(document!!.previewUrl)
        }

        binding.back.setOnClickListener {
            navController.popBackStack()
        }
    }

    internal class RetrievePDFStream constructor(var binding: FragmentResourceViewBinding) :
        AsyncTask<String?, Void?, InputStream?>() {

        override fun onPostExecute(inputStream: InputStream?) {
            if (inputStream != null) {
                binding.pdfView.fromStream(inputStream).load()
            } else {
                AppUtils.message(binding.root, "Invalid link", Color.WHITE, Color.RED)
            }
            binding.loadingProgressBar.setVisibility(View.GONE)
        }

        override fun doInBackground(vararg params: String?): InputStream? {
            var inputStream: InputStream? = null
            return try {
                if (!strings[0].endsWith(".pdf")) {
                    null
                } else {
                    val url = URL(strings[0])
                    val urlConnection = url.openConnection() as HttpURLConnection
                    if (urlConnection.responseCode == 200) {
                        inputStream = BufferedInputStream(urlConnection.inputStream)
                    }
                    inputStream
                }
            } catch (e: Exception) {
                AppUtils.message(binding.root, "Invalid link", Color.WHITE, Color.RED)
                null
            }
        }
    }

    override fun onDestroy() {
        retrievePDFStream.cancel(true)
        super.onDestroy()
    }
}