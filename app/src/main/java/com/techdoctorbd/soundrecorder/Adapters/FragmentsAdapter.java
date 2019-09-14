package com.techdoctorbd.soundrecorder.Adapters;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.techdoctorbd.soundrecorder.Fragments.FileListFragment;
import com.techdoctorbd.soundrecorder.Fragments.RecordFragment;

public class FragmentsAdapter extends FragmentPagerAdapter {
    private Context context;

    public FragmentsAdapter(FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0)
            return RecordFragment.getInstance();
        else if (position == 1)
            return FileListFragment.getInstance();
        else return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position)
        {
            case 0:
                return "Record";

            case 1:
                return "Saved Record";

        }
        return "";
    }
}
