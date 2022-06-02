package com.aah.sftelehealthworker.ui.home

import android.graphics.drawable.Drawable
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.aah.sftelehealthworker.R
import com.aah.sftelehealthworker.ui.adapter.UniversalPagerAdapter
import com.aah.sftelehealthworker.ui.home.adapter.HomeViewPagerAdapter
import com.aah.sftelehealthworker.ui.home.appointments.AppointmentsFragment
import com.aah.sftelehealthworker.ui.home.patients.PatientsFragment
import com.aah.sftelehealthworker.ui.home.settings.SettingsFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.home_fragment.view.*
import java.util.LinkedHashMap

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
        fun onBackPressed(): Boolean {
            return true
        }
    }

    private lateinit var viewModel: HomeViewModel
    private lateinit var stringFragmentMap: MutableMap<String, Fragment>
    private lateinit var iconFragmentMap: MutableMap<String, Drawable>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        getActivity()?.getWindow()?.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        val view = inflater.inflate(R.layout.home_fragment, container, false)

        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
//        setUpViewPager(view)
        view.viewPager2.adapter = HomeViewPagerAdapter(childFragmentManager, lifecycle)
        val tabLayoutMediator = TabLayoutMediator(view.tabLayout,view.viewPager2, object :TabLayoutMediator.TabConfigurationStrategy{
            override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {
                if (position==0){
                    tab.text = "Appoinements"
                    tab.setIcon(R.drawable.ic_baseline_calendar_white_24)
                }
                else if(position==1){
                    tab.text = "Patient"
                    tab.setIcon(R.drawable.ic_baseline_person_white_24)
                }
                else if(position==2){
                    tab.text = "Settings"
                    tab.setIcon(R.drawable.ic_baseline_settings_white_24)
                }
            }

        })
        tabLayoutMediator.attach()
        return view
    }

//    private fun setUpViewPager(view: View) {
//        val viewPager: ViewPager = view.findViewById(R.id.viewpager)
//        val tabLayout: TabLayout = view.findViewById(R.id.sliding_tabs)
//        tabLayout.setupWithViewPager(viewPager)
//        viewPager.offscreenPageLimit = 3
//        stringFragmentMap = LinkedHashMap()
//        iconFragmentMap = LinkedHashMap()
//        setupFragments()
//        val adapter = UniversalPagerAdapter(childFragmentManager, stringFragmentMap, iconFragmentMap)
//        viewPager.adapter = adapter
//    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        // TODO: Use the ViewModel
    }

//    private fun setupFragments() {
//        val appointmentsFragment = AppointmentsFragment()
//        val patientsFragment = PatientsFragment()
//        val settingsFragment = SettingsFragment()
//
//        val bundle = Bundle()
//        stringFragmentMap.put(resources.getString(R.string.appointments), appointmentsFragment as Fragment)
//        stringFragmentMap.put(resources.getString(R.string.patients), patientsFragment as Fragment)
//        stringFragmentMap.put(resources.getString(R.string.settings), settingsFragment as Fragment)
//
//        iconFragmentMap.put(resources.getString(R.string.appointments), getResources().getDrawable(R.drawable.ic_baseline_calendar_white_24))
//        iconFragmentMap.put(resources.getString(R.string.patients), getResources().getDrawable(R.drawable.ic_baseline_person_white_24))
//        iconFragmentMap.put(resources.getString(R.string.settings), getResources().getDrawable(R.drawable.ic_baseline_settings_white_24))
//    }

}