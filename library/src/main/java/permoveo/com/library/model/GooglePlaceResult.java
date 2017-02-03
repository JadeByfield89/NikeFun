package permoveo.com.library.model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by byfieldj on 2/1/17.
 * <p>
 * A simple POJO that represents, holds, and parses the values returned by the Google Place API
 * <p>
 * Could've probably used GSON for faster/more efficient object mapping/serialization
 */

public class GooglePlaceResult {

    private String mBusinessName;
    private String mBusinessAddress;
    private String mLatitude;
    private String mLongitude;


    private static final String KEY_NAME = "name";
    private static final String KEY_ADDRESS = "vicinity";
    private static final String KEY_GEOMETRY = "geometry";
    private static final String KEY_LOCATION = "location";
    private static final String KEY_LAT = "lat";
    private static final String KEY_LON = "lng";


    public GooglePlaceResult(JSONObject object) {
        Log.d("GooglePlaceResult", "GooglePlaceResult JSON -> " + object.toString());

        try {
            if (object.has(KEY_NAME)) {
                setBusinessName(object.getString(KEY_NAME));
            }

            if (object.has(KEY_ADDRESS)) {
                setBusinessAddress(object.getString(KEY_ADDRESS));
                Log.d("GooglePlaceResult", "Store address -> " + getBusinessAddress());
            }

            if (object.has(KEY_GEOMETRY)) {
                Log.d("GooglePlaceResult", "Geometry found!");

                JSONObject geometryObject = object.getJSONObject(KEY_GEOMETRY);

                if (geometryObject.has(KEY_LOCATION)) {
                    JSONObject locationObject = geometryObject.getJSONObject(KEY_LOCATION);

                    Log.d("GooglePlaceResult", "Lat -> " + locationObject.getDouble(KEY_LAT) + ", lon -> " + locationObject.getDouble(KEY_LON));
                    setLatitude("" + locationObject.getDouble(KEY_LAT));
                    setLongitude("" + locationObject.getDouble(KEY_LON));

                }


            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("GooglePlaceResult", "Error converting JSONObject to GooglePlaceResult!");
        }

    }

    private void setBusinessName(String name) {
        this.mBusinessName = name;
    }

    public String getBusinessName() {

        return mBusinessName;
    }

    private void setBusinessAddress(String address) {
        this.mBusinessAddress = address;
    }

    public String getBusinessAddress() {
        return mBusinessAddress;
    }

    private void setLatitude(String lat) {
        this.mLatitude = lat;
    }

    public String getLatitude() {
        return mLatitude;
    }

    private void setLongitude(String longitude) {
        this.mLongitude = longitude;
    }

    public String getLongitude() {
        return mLongitude;
    }
}
