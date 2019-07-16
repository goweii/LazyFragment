package per.goweii.android.lazyfragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class FixFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments = null;

    public FixFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public FixFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    public void setFragments(List<Fragment> fragments) {
        this.fragments = fragments;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments == null ? 0 : fragments.size();
    }
}
