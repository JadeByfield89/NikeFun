package permoveo.com.library.views;

/**
 * Created by byfieldj on 2/1/17.
 *
 *  * A ViewPager that allows pseudo-infinite paging by "faking" the index of the current item

 */

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import permoveo.com.library.adapters.InfiniteViewPagerAdapter;

public class InfiniteViewPager extends ViewPager {

    public InfiniteViewPager(Context context) {
        super(context);
    }

    public InfiniteViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        super.setAdapter(adapter);
        // offset first element so that we can scroll to the left
        setCurrentItem(0);
    }

    @Override
    public void setCurrentItem(int item) {
        // offset the current item to ensure there is space to scroll
        setCurrentItem(item, false);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        if (getAdapter().getCount() == 0) {
            super.setCurrentItem(item, smoothScroll);
            return;
        }
        item = getOffsetAmount() + (item % getAdapter().getCount());
        super.setCurrentItem(item, smoothScroll);
    }

    @Override
    public int getCurrentItem() {
        if (getAdapter().getCount() == 0) {
            return super.getCurrentItem();
        }
        int position = super.getCurrentItem();
        if (getAdapter() instanceof InfiniteViewPagerAdapter) {
            InfiniteViewPagerAdapter infAdapter = (InfiniteViewPagerAdapter) getAdapter();
            // Return the actual item position in the data backing InfiniteViewPagerAdapter
            return (position % infAdapter.getRealCount());
        } else {
            return super.getCurrentItem();
        }
    }

    private int getOffsetAmount() {
        if (getAdapter().getCount() == 0) {
            return 0;
        }
        if (getAdapter() instanceof InfiniteViewPagerAdapter) {
            InfiniteViewPagerAdapter infAdapter = (InfiniteViewPagerAdapter) getAdapter();
            // allow for 100 back cycles from the beginning
            // should be enough to create an illusion of infinity

            return infAdapter.getRealCount() * 100;
        } else {
            return 0;
        }
    }
}
