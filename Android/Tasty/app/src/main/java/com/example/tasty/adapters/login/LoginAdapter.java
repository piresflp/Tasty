package com.example.tasty.adapters.login;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.tasty.activities.usuario.cadastro.CadastroTabFragmentActivity;
import com.example.tasty.activities.usuario.login.LoginTabFragmentActivity;

public class LoginAdapter extends FragmentPagerAdapter {

    private Context context;
    int totalTabs;

    public LoginAdapter(FragmentManager fm, Context context, int totalTabs){
        super(fm);
        this.context = context;
        this.totalTabs = totalTabs;
    }

    @Override
    public int getCount() {
        return totalTabs;
    }

    public Fragment getItem(int position){
        switch (position){
            case 0:
                LoginTabFragmentActivity loginTabFragment = new LoginTabFragmentActivity();
                return  loginTabFragment;
            case 1:
                CadastroTabFragmentActivity cadastroTabFragment = new CadastroTabFragmentActivity();
                return  cadastroTabFragment;
            default:
                return null;
        }
    }
}
