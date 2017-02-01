package permoveo.com.library.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import permoveo.com.library.R;
import permoveo.com.library.constants.AppConstants;
import permoveo.com.library.model.GooglePlaceResult;
import permoveo.com.library.network.GooglePlaceRequest;

/**
 * Created by byfieldj on 2/1/17.
 *
 * This is an animated Fragment that shows the user his/her current location, as well as a short list
 * of nearby Nike retail locations
 */

public class MapLocationFragment extends DialogFragment {

    private static final String EXTRA_LATITUDE = "lat";
    private static final String EXTRA_LONGITUDE = "lon";
    private static String mLatitude;
    private static String mLongitude;
    private ArrayList<GooglePlaceResult> mNearbyLocations;
    private boolean mResultsDisplayed;


    ImageView mMapImage;
    TextView mCoordinates;
    TextView mDescription;
    Button mDone;
    Button mShowNikeStore;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NO_TITLE, 0);
    }

    @Override
    public void onStart() {
        super.onStart();


        final View decorView = getDialog()
                .getWindow()
                .getDecorView();

        // Let's give this fragment some personality with a Wave animation once it is started
       YoYo.with(Techniques.Wave).duration(600).playOn(decorView);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map_location, container, false);

        mMapImage = (ImageView) view.findViewById(R.id.iv_map_image);
        mCoordinates = (TextView) view.findViewById(R.id.tv_coordinates);
        mDescription = (TextView) view.findViewById(R.id.tv_location_desc);
        mDone = (Button) view.findViewById(R.id.b_ok);
        mShowNikeStore = (Button) view.findViewById(R.id.b_show_nike_store);

        getMapImageUrlAndDisplay();


        mDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        mShowNikeStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAndDisplayNearestNikeRetailStores();
            }
        });


        return view;
    }

    // Execute a Google nearby place request, our radius is set to 10000 meters
    private void getAndDisplayNearestNikeRetailStores() {
        GooglePlaceRequest placeRequest = new GooglePlaceRequest();
        placeRequest.getNearbyNikeRetailers(mLatitude, mLongitude, new GooglePlaceRequest.GooglePlaceRequestListener() {
            @Override
            public void onPlaceRequestComplete(ArrayList<GooglePlaceResult> results) {
                mNearbyLocations = results;
                displaySearchResults();
            }

            @Override
            public void onPlaceRequestError(String error) {
                Toast.makeText(getContext(), "Something went wrong finding nearest store locations!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displaySearchResults() {
        if(!mResultsDisplayed) {
            mDescription.setTextSize(15.0f);
            mDescription.setText("Your nearest Nike retail locations are " + "\n\n" + mNearbyLocations.get(0).getBusinessName() + "\n" + mNearbyLocations.get(1).getBusinessAddress() + "\n\n" + mNearbyLocations.get(1).getBusinessName() + "\n" + mNearbyLocations.get(1).getBusinessAddress() + "\n\n" + mNearbyLocations.get(2).getBusinessName() + "\n" + mNearbyLocations.get(2).getBusinessAddress());
            mResultsDisplayed = true;
        }
        else{
            Toast.makeText(getContext(), "Already showing nearby results", Toast.LENGTH_SHORT).show();

        }
    }

    private void getMapImageUrlAndDisplay() {

        String staticImageUrl = "https://maps.googleapis.com/maps/api/staticmap?center=%s,%s&zoom=13&size=500x200&markers=color:red|%s,%s|&key=%s";

        String formattedUrl = String.format(staticImageUrl, mLatitude, mLongitude, mLatitude, mLongitude, AppConstants.GOOGLE_MAP_IMAGE_API_KEY);

        // Load the static map image with Picasso
        Picasso.with(getContext()).load(formattedUrl).fit().into(mMapImage);

        mCoordinates.setText(mLatitude + "," + mLongitude);


    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    public static MapLocationFragment newInstance(String lat, String lon) {

        mLatitude = lat;
        mLongitude = lon;

        Bundle args = new Bundle();

        MapLocationFragment fragment = new MapLocationFragment();
        args.putString(EXTRA_LATITUDE, lat);
        args.putString(EXTRA_LONGITUDE, lon);
        fragment.setArguments(args);
        return fragment;
    }
}
