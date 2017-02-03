package permoveo.com.library.activities;

import android.content.pm.ActivityInfo;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import permoveo.com.library.R;
import permoveo.com.library.adapters.CarouselAdapter;
import permoveo.com.library.adapters.InfiniteViewPagerAdapter;
import permoveo.com.library.fragments.MapLocationFragment;
import permoveo.com.library.location.GPSLocator;
import permoveo.com.library.views.CarouselView;
import permoveo.com.library.views.InfiniteViewPager;
import permoveo.com.library.views.LocateMeView;

/**
 * Created by byfieldj on 2/1/17.
 * <p>
 * This Activity is designed to be re-usable. It's abstract because it doesn't need to be instantiated, we just want to store common, dependable features here and let subclasses account for specific
 * functionality. Dependent classes or applications simply need to extend LocationActivity in order to reuse its UI
 */

public abstract class LocationActivity extends AppCompatActivity {

    private final ArrayList<Integer> mImages = new ArrayList<>(Arrays.asList(R.drawable.nike_sneaker_one, R.drawable.nike_sneaker_two, R.drawable.nike_sneaker_three, R.drawable.nike_sneaker_four, R.drawable.nike_sneaker_five, R.drawable.nike_sneaker_six, R.drawable.nike_sneaker_seven, R.drawable.nike_sneaker_eight, R.drawable.nike_sneaker_nine, R.drawable.nike_sneaker_ten));

    InfiniteViewPager mViewPager;
    InfiniteViewPagerAdapter mPagerAdapter;

    private CarouselView mCarousel;


    private GPSLocator mLocator;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocateMeView mainContent = new LocateMeView(this);
        setContentView(mainContent);

        mViewPager = (InfiniteViewPager) findViewById(R.id.pager);
        mCarousel = (CarouselView) findViewById(R.id.carousel);

        mainContent.setListener(new LocateMeView.LocationListener() {
            @Override
            public void onLocateButtonSelected() {

                Log.d("LocationActivity", "onLocationButtonSelected");
                if (mLocator.getCurrentLocation() != null) {
                    Location currentLocation = mLocator.getCurrentLocation();

                    MapLocationFragment mapFragment = MapLocationFragment.newInstance(String.valueOf(currentLocation.getLatitude()), String.valueOf(currentLocation.getLongitude()));
                    mapFragment.show(getSupportFragmentManager(), "mapFragment");
                } else {

                    Toast.makeText(LocationActivity.this, "Are you sure GPS is turned on?", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onLocationError() {

            }
        });

        mLocator = new GPSLocator(this);


        final CarouselAdapter adapter = new CarouselAdapter(this);
        mCarousel.setAdapter(adapter);
        mCarousel.setSelection(adapter.getCount() / 2); //adapter.getCount()-1
        //carousel.setSlowDownCoefficient(1);
        mCarousel.setSpacing(1.3f);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mLocator.startConnection();
        Log.d("LocationActivity", "Starting GPS Connection!");
    }

    @Override
    protected void onStop() {
        super.onStop();
        mLocator.stopConnection();
        Log.d("LocationActivity", "Shutting down GPS Connection!");

    }


}
