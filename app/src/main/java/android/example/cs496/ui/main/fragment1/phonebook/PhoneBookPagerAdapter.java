package android.example.cs496.ui.main.fragment1.phonebook;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class PhoneBookPagerAdapter extends FragmentPagerAdapter {

    private Fragment mFragment = new Fragment();
    public void add(Fragment fragment){
        mFragment = fragment;
    }

    public PhoneBookPagerAdapter(FragmentManager fm) { super(fm);}

    @Override
    public Fragment getItem(int position) { return mFragment; }

    @Override
    public int getCount() {
        return 1;
    }
}