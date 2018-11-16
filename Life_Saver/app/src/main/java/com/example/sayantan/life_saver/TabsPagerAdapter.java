package com.example.sayantan.life_saver;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;



class TabsPagerAdapter extends FragmentStatePagerAdapter {


    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);

    }

    @Override
    public  android.support.v4.app.Fragment getItem(int position) {
        switch (position){
            case 0:YourFragment yourFragment=new YourFragment();
                return yourFragment;

            case 1:DonarFragment donarFragment=new DonarFragment();
                return donarFragment;

            case 2:RecipientFragment recipientFragment=new RecipientFragment();
                return recipientFragment;

            case 3:CoolDown coolFragment=new CoolDown();
                return coolFragment;

            default:return null;

        }
    }

    @Override
    public int getCount() {
        return 4;
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:return "Requisition";
            case 1:return "Donor";
            case 2:return "Recipient";
            case 3:return "Blood Bank";
            default:return null;
        }
    }

}
