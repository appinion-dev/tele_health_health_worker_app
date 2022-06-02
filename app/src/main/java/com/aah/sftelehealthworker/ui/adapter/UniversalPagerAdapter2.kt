package com.aah.sftelehealthworker.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import java.util.*

class UniversalPagerAdapter2(fm: FragmentManager?, stringFragmentMap: Map<String, Fragment>) : FragmentStatePagerAdapter(fm!!) {
    private val fragments: MutableList<Fragment>
    private val fragmentNames: MutableList<String>

    override fun getItem(position: Int): Fragment {
        return if (position < fragments.size) fragments[position] else fragments[0]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return if (position < fragmentNames.size) fragmentNames[position] else null
    }

    init {
        fragments = ArrayList()
        fragmentNames = ArrayList()
        for ((key, value) in stringFragmentMap) {
            fragments.add(value)
            fragmentNames.add(key)
        }
    }
}