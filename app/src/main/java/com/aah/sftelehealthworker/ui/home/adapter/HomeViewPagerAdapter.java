package com.aah.sftelehealthworker.ui.home.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.aah.sftelehealthworker.ui.home.appointments.AppointmentsFragment;
import com.aah.sftelehealthworker.ui.home.patients.PatientsFragment;
import com.aah.sftelehealthworker.ui.home.settings.SettingsFragment;

public class HomeViewPagerAdapter extends FragmentStateAdapter {

    public HomeViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
//        return null;
        switch (position){
            case 0 : return new AppointmentsFragment();
            case 1: return new PatientsFragment();
            case 2: return new SettingsFragment();
            default: return new AppointmentsFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
