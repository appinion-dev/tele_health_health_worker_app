package com.aah.sftelehealthworker.ui.patientProfile.fragments.report

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.aah.sftelehealthworker.R
import com.aah.sftelehealthworker.common.BaseFragment
import com.aah.sftelehealthworker.databinding.ReportSubmitFragmentBinding
import com.aah.sftelehealthworker.ui.MainActivity
import com.aah.sftelehealthworker.utils.*
import droidninja.filepicker.FilePickerBuilder
import droidninja.filepicker.FilePickerConst
import gun0912.tedimagepicker.builder.TedImagePicker
import id.zelory.compressor.Compressor
import kotlinx.coroutines.launch
import java.io.File
import java.util.*

class ReportSubmitFragment : BaseFragment() {

    companion object {
        fun newInstance() = ReportSubmitFragment()
    }

    private lateinit var viewModel: ReportSubmitViewModel
    private lateinit var binding: ReportSubmitFragmentBinding

    private lateinit var navController: NavController

    private lateinit var patientId: String
    private lateinit var status: String

    private var uri: Uri? = null
    private val pdfRequestCode = 101

    private var file: File? = null
    private var fileType: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        return inflater.inflate(R.layout.report_submit_fragment, container, false)
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.report_submit_fragment,
            container,
            false
        )
        viewModel = ViewModelProvider(this).get(ReportSubmitViewModel::class.java)
        mActivity = activity
        patientId = arguments?.getString(PATIENT_ID_KEY).toString()
        status = arguments?.getString(STATUS_KEY).toString()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (status == "image") {
            binding.imageLabel.visibility = View.VISIBLE
            binding.pdfUploadLayout.visibility = View.GONE
            initImage()
        } else if (status == "pdf") {
            binding.imageLabel.visibility = View.GONE
            binding.pdfUploadLayout.visibility = View.VISIBLE

            binding.pdfUploadLayout.setOnClickListener {
                showPdf()
            }
            binding.select.setOnClickListener {
                showPdf()
            }
        }

        navController = Navigation.findNavController(binding.root)
//        viewModel.setSuccessFullFalse()
        initSuccessful()

        binding.upload.setOnClickListener {
            if (!isValid()) {
                return@setOnClickListener
            }

            if (status == IMAGE) {
                compraceImage()
            } else if (status == PDF) {
//                val file = File(uri.path)
                if (file == null) {
                    AppUtils.message(binding.root, getString(R.string.pdf_validation), context)
                    return@setOnClickListener
                }

                showProgressDialog("File uploading....")
                file?.let { it1 ->
                    viewModel.submitData(
                        it1,
                        binding.fileName.text.toString(),
                        binding.note.text.toString(),
                        patientId.toInt(),
                        PDF
                    )
                }
            }
        }
    }

    private fun initSuccessful() {

        viewModel.isSuccessFull.observe(viewLifecycleOwner, Observer {
            if (it) {
                hideProgressDialog()
                navController.popBackStack()
                AppUtils.message(binding.root, getString(R.string.report_save_msg), context)

            }
        })

        viewModel.message.observe(viewLifecycleOwner, Observer {
            hideProgressDialog()
            AppUtils.message(binding.root, it, context)
        })
    }

    private fun compraceImage() {
        showProgressDialog("File uploading....")

        val file = File(uri!!.path)
        file.let { file ->
            lifecycleScope.launch {
                val compressedImageFile = context?.let { it1 -> Compressor.compress(it1, file) }
                compressedImageFile?.let { it1 ->
                    viewModel.submitData(
                        it1,
                        binding.fileName.text.toString(),
                        binding.note.text.toString(),
                        patientId.toInt(),
                        IMAGE
                    )
                }
            }
        }
    }

    private fun showPdf() {
        if ((activity as MainActivity).checkReadWritePermission()) {
            val pdfs = arrayOf(PDF)
            FilePickerBuilder.instance
                .setMaxCount(1)
                .setActivityTheme(R.style.AppTheme)
                .enableDocSupport(false)
                .addFileSupport(FilePickerConst.PDF, pdfs, R.drawable.ic_pdf)
                .pickFile(this)
        }
    }

    private fun initImage() {
        binding.documentImage.setOnClickListener {
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
            .fitCenter()
            .placeholder(context?.getDrawable(R.drawable.person_male))
            .error(context?.getDrawable(R.drawable.person_male))
            .fallback(context?.getDrawable(R.drawable.person_male))
            .into(binding.documentImage)

        binding.moneyReceiptText.visibility = View.GONE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == FilePickerConst.REQUEST_CODE_DOC) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                val docPaths = ArrayList<String>()
                docPaths.addAll(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_DOCS)!!)
                file = File(docPaths[0])
                binding.pdfFileName.setText(file!!.name)
                val i = docPaths[0].lastIndexOf('.')
                if (i > 0) {
                    fileType = docPaths[0].substring(i + 1)
                }
//                val docPaths = ArrayList<Uri>()
//                docPaths.addAll(data.getParcelableArrayListExtra<Uri>(FilePickerConst.PDF)!!)
//                uri = docPaths[0]
//                if(file != null) {
//                    binding.pdfFileName.setText(file!!.getName())
//                    val i = docPaths[0].lastIndexOf('.')
//                    if (i > 0) {
//                        fileType = docPaths[0].substring(i + 1)
//                    }
//                }
            }
        }

    }

    private fun isValid(): Boolean {
        if (status == IMAGE) {
            if (uri == null) {
                AppUtils.message(binding.root, getString(R.string.image_validation), context)
                return false
            }
        } else if (status == PDF) {
            if (file == null) {
                AppUtils.message(binding.root, getString(R.string.pdf_validation), context)
                return false
            }
        } else if (binding.fileName.text.toString().equals("")) {
            binding.fileName.setError(getString(R.string.file_name_validation_text))
            return false
        } else if (binding.note.text.toString().equals("")) {
            binding.note.setError(getString(R.string.note_validation))
            return false
        }

        return true
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProviders.of(this).get(ReportSubmitViewModel::class.java)
    }

}