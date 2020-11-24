package com.example.tasty.adapters.home;

import android.content.Context;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.tasty.fragments.Favorite2Fragment;
import com.example.tasty.fragments.FavoriteFragment;
import com.example.tasty.fragments.HomeFragment;
import com.example.tasty.fragments.TalherFragment;
import com.example.tasty.sessionManagement.SessionManagement;

public class PageAdapter extends FragmentPagerAdapter {

    private int numOfTabs;
    private Context context;

    public PageAdapter(FragmentManager fm, int numOfTabs, Context applicationContext) {
        super(fm);
        this.numOfTabs = numOfTabs;
        context = applicationContext;
    }


    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new HomeFragment();
            case 1:
                return new TalherFragment();
            case 2:
                SessionManagement sessionManagement = new SessionManagement(context);
                if(sessionManagement.getSession() == -1)
                    return new FavoriteFragment();
                else
                    return new Favorite2Fragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
