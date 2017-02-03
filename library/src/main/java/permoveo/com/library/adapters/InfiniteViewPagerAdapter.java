package permoveo.com.library.adapters;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import permoveo.com.library.R;

/**
 * Created by byfieldj on 1/31/17.
 */

public class InfiniteViewPagerAdapter extends PagerAdapter {

    private Context mContext;
    private LayoutInflater mInflater;

    private ArrayList<Integer> mImages;
    private View mCurrentView;


    public InfiniteViewPagerAdapter(Context context, ArrayList<Integer> images) {
        this.mContext = context;
        this.mImages = images;

        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mInflater.inflate(R.layout.view_pager_item, container, false);

        ImageView currentImage = (ImageView) itemView.findViewById(R.id.iv_pager_item);


        // Using the modulus operator allows our position values to be "cycled", ie start over once the last position is reached
        int virtualPosition = position % mImages.size();
        Picasso.with(mContext).load(mImages.get(virtualPosition)).fit().into(currentImage);
        Log.d("InfiniteViewPager", "Position -> " + position);
        Log.d("InfiniteViewPager", "Virtual Position -> " + virtualPosition);

        container.addView(itemView);


        return itemView;


    }

    public int getRealCount() {
        return mImages.size();
    }


    // Return an incredibly high number to "simulate" infinity
    @Override
    public int getCount() {

        return Integer.MAX_VALUE;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((View) object);
    }


    // Viewpager is designed to have each page or "slide" take up the full height and width of the screen
    // no matter the dimensions of the page's child views

    // Altering this getPageWidth() to return .33f will make each page take up a max of 1/3 of the screen
    @Override
    public float getPageWidth(int position) {
        return 0.33f;
    }


    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {

        // Make sure we "shrink" down the previous center animate before zooming the next
        if (mCurrentView != null) {
            //scaleDownMiddleView(mCurrentView);
        }

        //Log.d("InfiniteView", "Middle item index " + (container.getChildCount() / 2 + 1));
        //mCurrentView = container.getChildAt((container.getChildCount() / 2) + 1);

       // scaleUpMiddleView(mCurrentView);


    }


    // Simulate a "zoom in" animation
    private void scaleUpMiddleView(View view) {

        ObjectAnimator scaleUpX = ObjectAnimator.ofFloat(view, "scaleX", 1.5f);
        ObjectAnimator scaleUpY = ObjectAnimator.ofFloat(view, "scaleY", 1.5f);
        scaleUpX.setDuration(400);
        scaleUpY.setDuration(400);

        AnimatorSet scaleDown = new AnimatorSet();

        scaleDown.play(scaleUpX).with(scaleUpY);
        scaleDown.start();

    }

    // Animate the image back down to its original size
    private void scaleDownMiddleView(View view) {

        ObjectAnimator scaleUpX = ObjectAnimator.ofFloat(view, "scaleX", 1.0f);
        ObjectAnimator scaleUpY = ObjectAnimator.ofFloat(view, "scaleY", 1.0f);
        scaleUpX.setDuration(400);
        scaleUpY.setDuration(400);

        AnimatorSet scaleDown = new AnimatorSet();

        scaleDown.play(scaleUpX).with(scaleUpY);
        scaleDown.start();

    }


}
