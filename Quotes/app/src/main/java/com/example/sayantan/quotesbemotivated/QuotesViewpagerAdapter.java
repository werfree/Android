package com.example.sayantan.quotesbemotivated;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class QuotesViewpagerAdapter extends FragmentPagerAdapter {

    private List<Fragment>fragment;
    public QuotesViewpagerAdapter(FragmentManager fm,List<Fragment>fragment) {
        super(fm);
        this.fragment=fragment;
    }

    @Override
    public Fragment getItem(int i) {
        return this.fragment.get(i);
    }

    @Override
    public int getCount() {
        return this.fragment.size();
    }
}
