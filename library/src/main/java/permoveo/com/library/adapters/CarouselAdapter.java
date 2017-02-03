package permoveo.com.library.adapters;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.squareup.picasso.Picasso;

import permoveo.com.library.R;
import permoveo.com.library.views.CircleImageView;

/**
 * Created by byfieldj on 2/3/17.
 */

public class CarouselAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;

    private int[] mResourceIds = {R.drawable.nike_sneaker_one, R.drawable.nike_sneaker_two, R.drawable.nike_sneaker_three, R.drawable.nike_sneaker_four,
            R.drawable.nike_sneaker_five, R.drawable.nike_sneaker_six, R.drawable.nike_sneaker_seven, R.drawable.nike_sneaker_eight, R.drawable.nike_sneaker_nine, R.drawable.nike_sneaker_ten};


    public CarouselAdapter(Context context) {

        this.mContext = context;
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public Object getItem(int position) {
        return mResourceIds[position % mResourceIds.length];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        private CircleImageView mImageView;


        public ViewHolder(View v) {


            mImageView = (CircleImageView) v.findViewById(R.id.iv_pager_item);

            setSelected(true);
        }

        public void setSelected(boolean selected) {

            if (selected) {
                mImageView.setAlpha(1.0f);
            } else {
                mImageView.setAlpha(0.5f);
            }
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder v;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.view_pager_item, parent, false);
            v = new ViewHolder(convertView);
            v.mImageView = (CircleImageView) convertView.findViewById(R.id.iv_pager_item);
            convertView.setTag(v);
        } else {
            v = (ViewHolder) convertView.getTag();
        }

        v.mImageView.setTag(position);

        Picasso.with(mContext).load(mResourceIds[position % mResourceIds.length]).fit().centerCrop().into(v.mImageView);
        scaleDownView(convertView);


        return convertView;
    }

    public void addView() {
        notifyDataSetChanged();
    }

    // Simulate a "zoom in" animation
    private void scaleDownView(View view) {

        ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(view, "scaleX", 1.0f);
        ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(view, "scaleY", 1.0f);
        scaleDownX.setDuration(200);
        scaleDownY.setDuration(200);

        AnimatorSet scaleDown = new AnimatorSet();

        scaleDown.play(scaleDownX).with(scaleDownY);
        scaleDown.start();

    }
}
