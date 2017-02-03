package permoveo.com.library.views;

/**
 * Created by byfieldj on 2/1/17.
 * <p>
 * * A ViewPager that allows pseudo-infinite paging by "faking" the index of the current item
 */

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import permoveo.com.library.adapters.InfiniteViewPagerAdapter;

public class InfiniteViewPager extends ViewPager implements ViewPager.OnPageChangeListener {

    public InfiniteViewPager(Context context) {
        super(context);
    }

    public InfiniteViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private PagerAdapter mAdapter;
    private int mCurrentPage;

    @Override
    public void setAdapter(PagerAdapter adapter) {
        super.setAdapter(adapter);

        mAdapter = adapter;
        // offset first element so that we can scroll to the left
        setCurrentItem(0);

    }


    @Override
    public void setCurrentItem(int item) {
        // offset the current item to ensure there is space to scroll
        setCurrentItem(item, false);

        /*Log.d("InfiniteViewPagerV", "Current item -> " + item);
        View currentView = getChildAt(item);
        scaleUpView(currentView);*/
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


    // Simulate a "zoom in" animation
    private void scaleUpView(View view, int pixels) {

        ObjectAnimator scaleUpX = ObjectAnimator.ofFloat(view, "scaleX", 1.5f + pixels);
        ObjectAnimator scaleUpY = ObjectAnimator.ofFloat(view, "scaleY", 1.5f + pixels);
        scaleUpX.setDuration(400);
        scaleUpY.setDuration(400);

        AnimatorSet scaleDown = new AnimatorSet();

        scaleDown.play(scaleUpX).with(scaleUpY);
        scaleDown.start();

    }

    @Override
    public void onPageScrolled(int position, float offset, int offsetPixels) {
        super.onPageScrolled(position, offset, offsetPixels);

        View currentView = getChildAt((getChildCount() / 2 ) + 1);
        scaleUpView(currentView, offsetPixels);

        View leftView = getChildAt(getChildCount() / 2 );
        scaleUpView(leftView, offsetPixels);


       /* Log.d("InfiniteViewPagerV", "Scroll offset -> " + offset);
        Log.d("InfiniteViewPagerV", "Scroll offsetPixels -> " + offsetPixels);
        Log.d("InfiniteViewPagerV", "Position -> " + position);

        //if (position == mCurrentPage) {
        // It's gone to right.
        if (offsetPixels > 0.5) {
            Log.d("InfiniteViewPagerV", "Scrolled to RIGHT -> ");

        }

        // }
        else {
            // It's gone to left.
            if (offsetPixels < 0.5) {
                Log.d("InfiniteViewPagerV", "Scrolled to LEFT-> ");

            }

        }*/


    }

    @Override
    public void onPageSelected(int position) {

        mCurrentPage = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        /*switch (state) {
            case ViewPager.SCROLL_STATE_DRAGGING:
                Log.d("InfiniteViewPagerV", "User is dragging");
                scaleUpView(getChildAt(1));
                break;
        }*/
    }
}
