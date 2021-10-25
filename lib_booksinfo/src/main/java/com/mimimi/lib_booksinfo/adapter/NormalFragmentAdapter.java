package com.mimimi.lib_booksinfo.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class NormalFragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> titles;

    public NormalFragmentAdapter(@NonNull FragmentManager fm, int be) {
        super(fm,be);
    }
    public NormalFragmentAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    public void setFragmentList(List<Fragment> fragmentList) {
        this.fragmentList = fragmentList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (titles!=null&&titles.size()>0)
        {
            return titles.get(position);
        }
        return super.getPageTitle(position);
    }

    @Override
    public int getCount() {
        return fragmentList==null?0:fragmentList.size();
    }

    public void setTitles(List<String> titles) {
        this.titles = titles;
    }

    public List<String> getTitles() {
        return titles;
    }
}
