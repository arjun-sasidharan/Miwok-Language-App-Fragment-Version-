package com.example.android.miwok;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class CategoryAdapter extends FragmentPagerAdapter {

    Context mContext;

    public CategoryAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }
//fragments according to position
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new NumberFragment();
        } else if (position == 1){
            return new FamilyFragment();
        } else if (position == 2) {
            return new ColorsFragment();
        }
        else {
            return new PhrasesFragment();
        }
    }
//no of fragments
    @Override
    public int getCount() {
        return 4;
    }
//tag name / title of fragments according to position
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return mContext.getString(R.string.category_numbers);
        } else if (position == 1){
            return mContext.getString(R.string.category_family);
        } else if (position == 2) {
            return mContext.getString(R.string.category_colors);
        }
        else {
            return mContext.getString(R.string.category_phrases);
        }
    }
}
