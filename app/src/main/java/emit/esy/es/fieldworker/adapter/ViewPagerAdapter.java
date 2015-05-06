package emit.esy.es.fieldworker.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import emit.esy.es.fieldworker.LocationFragment;
import emit.esy.es.fieldworker.PostFragment;

/**
 * Created by Emil Makovac on 02/04/2015.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private static final int PAGE_COUNT = 2;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {

            case 0:
                PostFragment postFragment = new PostFragment();
                return postFragment;

            case 1:
                LocationFragment locationFragment = new LocationFragment();
                return locationFragment;

        }
        return null;
    }

}