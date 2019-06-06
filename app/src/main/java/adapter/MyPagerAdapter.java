package adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class MyPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentsList;

    public MyPagerAdapter(FragmentManager fm, List<Fragment> fragmentsList) {
        super(fm);
        this.fragmentsList = fragmentsList;
    }

    @Override
    public Fragment getItem(int i) {
        return this.fragmentsList.get(i);
    }

    @Override
    public int getCount() {
        return this.fragmentsList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Sun";
            case 1:
                return "Mon";
            case 2:
                return "Tue";
            case 3:
                return "Wed";
            case 4:
                return "Thu";
            case 5:
                return "Fri";
            case 6:
                return "Sat";
            case 7:
                return "Sun";
            case 8:
                return "Mon";
        }

        return null;
    }
}
