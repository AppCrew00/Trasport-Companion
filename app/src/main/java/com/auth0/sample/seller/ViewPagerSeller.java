package com.auth0.sample.seller;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.auth0.sample.Job;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerSeller extends FragmentStatePagerAdapter {

    private int tabCount;
    private Context context;
    private List<Job> jobList = new ArrayList<>();
    private List<Job> localJobs = new ArrayList<>();
    private List<Job> nonLocalJobs = new ArrayList<>();
    private SharedPreferences sharedPreferences;

    public ViewPagerSeller(@NonNull @NotNull FragmentManager fm, int behavior , Context context , List<Job> jobList) {
        super(fm, behavior);
        this.tabCount = behavior;
        this.context = context;
        this.jobList = jobList;
        sharedPreferences = context.getSharedPreferences("EmailVar", Context.MODE_PRIVATE);
        sort_List();
    }

    @Override
    public int getItemPosition(@NonNull @NotNull Object object) {
        return POSITION_NONE;
    }

    public void  sort_List()
    {
        for ( int i=0 ; i<jobList.size() ; i++ )
        {
            if (jobList.get(i).getPincode().equalsIgnoreCase(sharedPreferences.getString("pincode","")))
            {
                localJobs.add(jobList.get(i));
            }
            else
            {
                nonLocalJobs.add(jobList.get(i));
            }
        }
    }

    @NonNull
    @NotNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0 : return new FragmentSeller( context , localJobs);
            case 1 : return new FragmentSeller( context , nonLocalJobs );
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position)
        {
            case 0 : return "LOCAL";
            case 1 : return "Non LOCAL";
            default: return null;
        }
    }


}
