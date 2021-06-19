package com.sadcos.supermarketcomparator.ui.main;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.sadcos.supermarketcomparator.R;
import com.sadcos.supermarketcomparator.ui.main.fragmentsCart.alcampoFragmentCart;
import com.sadcos.supermarketcomparator.ui.main.fragmentsCart.carrefourFragmentCart;
import com.sadcos.supermarketcomparator.ui.main.fragmentsCart.diaFragmentCart;
import com.sadcos.supermarketcomparator.ui.main.fragmentsCart.mercadonaFragmentCart;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter  implements mercadonaFragmentCart.OnFragmentInteractionListener{

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2,R.string.tab_text_3,R.string.tab_text_4};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
       Fragment fragment = null;
       switch (position){
           case 0:
               fragment = new mercadonaFragmentCart();
               break;
           case 1:
               fragment = new carrefourFragmentCart();
               break;
           case 2:
               fragment = new diaFragmentCart();
               break;
           case 3:
               fragment = new alcampoFragmentCart();
               break;
       }
       return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 4;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}