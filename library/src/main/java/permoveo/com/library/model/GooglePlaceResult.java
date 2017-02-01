package permoveo.com.library.model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by byfieldj on 2/1/17.
 *
 * A simple POJO that represents, holds, and parses the values returned by the Google Place API
 *
 * Could've probably used GSON for faster/more efficient object mapping/serialization
 */

public class GooglePlaceResult {

    private String mBusinessName;
    private String mBusinessAddress;

    private static final String KEY_NAME = "name";
    private static final String KEY_ADDRESS = "vicinity";

    public GooglePlaceResult(JSONObject object) {
        Log.d("GooglePlaceResult", "GooglePlaceResult JSON -> " + object.toString());

        try {
            if (object.has(KEY_NAME)) {
                setBusinessName(object.getString(KEY_NAME));
            }

            if (object.has(KEY_ADDRESS)) {
                setBusinessAddress(object.getString(KEY_ADDRESS));
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
}
