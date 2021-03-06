package com.aah.sftelehealthworker.ui.adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UniversalPagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragments;
    private List<String> fragmentNames;

    public UniversalPagerAdapter(FragmentManager fm, Map<String, Fragment> stringFragmentMap) {
        super(fm);
        fragments = new ArrayList<>();
        fragmentNames = new ArrayList<>();

        for (Map.Entry<String, Fragment> fragmentEntry : stringFragmentMap.entrySet()) {
            fragments.add(fragmentEntry.getValue());
            fragmentNames.add(fragmentEntry.getKey());
        }
    }

    @Override
    public Fragment getItem(int position) {
        if (position < fragments.size())
            return fragments.get(position);
        else
            return null;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position < fragmentNames.size())
            return fragmentNames.get(position);
        else
            return null;
        }
}
