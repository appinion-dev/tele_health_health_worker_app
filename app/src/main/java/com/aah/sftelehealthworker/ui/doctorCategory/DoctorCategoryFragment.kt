package com.aah.sftelehealthworker.ui.doctorCategory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.aah.sftelehealthworker.R
import com.aah.sftelehealthworker.databinding.DoctorCategoryFragmentBinding
import com.aah.sftelehealthworker.models.appoinment.Category
import com.aah.sftelehealthworker.ui.doctorCategory.adapter.DoctorCategoryRecyclerAdapter
import com.aah.sftelehealthworker.utils.AppUtils

class DoctorCategoryFragment : Fragment(), DoctorCategoryRecyclerAdapter.Interaction {

    companion object {
        fun newInstance() = DoctorCategoryFragment()
    }

    private lateinit var viewModel: DoctorCategoryViewModel
    private lateinit var binding: DoctorCategoryFragmentBinding
    private lateinit var categoryAdapter: DoctorCategoryRecyclerAdapter
    private lateinit var navController: NavController

    private var patientId: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        return inflater.inflate(R.layout.doctor_category_fragment, container, false)
        binding =
            DataBindingUtil.inflate(inflater, R.layout.doctor_category_fragment, container, false)
        viewModel = ViewModelProvider(this).get(DoctorCategoryViewModel::class.java)

        patientId = arguments?.getString("patientId").toString()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Step 1: Select Category"

        navController = Navigation.findNavController(binding.root)

        initAdapter()
        initMessage()
        initRefresh()

        viewModel.loadData()

        binding.refresh.setOnRefreshListener {
            viewModel.loadData()
        }
    }

    private fun initMessage() {
        viewModel.message.observe(viewLifecycleOwner, Observer {
            AppUtils.message(binding.root, it, context)
        })
    }

    private fun initRefresh() {
        viewModel.refresh.observe(viewLifecycleOwner, Observer {
            binding.refresh.isRefreshing = it
        })
    }

    private fun initAdapter() {
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
//            val itemDecor = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
//            addItemDecoration(itemDecor)
            categoryAdapter = DoctorCategoryRecyclerAdapter(this@DoctorCategoryFragment)
            adapter = categoryAdapter
        }

        viewModel.categoryMutableLiveData.observe(viewLifecycleOwner, Observer {
            if (it.isNullOrEmpty()) {
                AppUtils.message(binding.root, "Category not found", context)
                categoryAdapter.submitList(emptyList())
            } else {
                categoryAdapter.submitList(it)
            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProviders.of(this).get(DoctorCategoryViewModel::class.java)
    }

    override fun onItemSelected(position: Int, item: Category) {
        gotoDoctorList(item)
    }

    private fun gotoDoctorList(item: Category) {
        val bundle = bundleOf("categoryId" to item.id.toString(), "patientId" to patientId)
        navController.navigate(R.id.action_doctorCategoryFragment_to_doctorListFragment, bundle)
    }

}