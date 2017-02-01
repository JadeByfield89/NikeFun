package permoveo.com.library.network;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import permoveo.com.library.application.NikeFunApplication;
import permoveo.com.library.constants.AppConstants;
import permoveo.com.library.model.GooglePlaceResult;

/**
 * Created by byfieldj on 2/1/17.
 *
 * This class executes a Volley request for a specific data type, in this case a JSONArray
 *
 * It then iterates the array and grabs the first 3 JSONObjects inside of it, converting those objects to GooglePlaceResult POJOS for later use.
 */

public class GooglePlaceRequest {


    // Some hooks we can use to notify any concerned classes that will be making this request
    public interface GooglePlaceRequestListener {

        void onPlaceRequestComplete(ArrayList<GooglePlaceResult> results);

        void onPlaceRequestError(String error);
    }

    private GooglePlaceRequestListener mListener;

    public void getNearbyNikeRetailers(String lat, String lon, GooglePlaceRequestListener listener) {

        this.mListener = listener;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, String.format(AppConstants.GOOGLE_PLACE_SEARCH_ENDPOINT, lat, lon, AppConstants.GOOGLE_PLACE_API_KEY), null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                // Extract the "results" array from the JSON that the Google Place API returns
                try {
                    JSONArray array = response.getJSONArray("results");
                    ArrayList<GooglePlaceResult> results = new ArrayList<>();

                    // Only grab the first 3 objects in the result list
                    for (int i = 0; i < 3; i++) {
                        JSONObject placeObject = array.getJSONObject(i);
                        GooglePlaceResult placeResult = new GooglePlaceResult(placeObject);
                        results.add(placeResult);
                    }

                    mListener.onPlaceRequestComplete(results);

                } catch (JSONException e) {
                    e.printStackTrace();
                    mListener.onPlaceRequestError(e.getMessage());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();
                mListener.onPlaceRequestError(error.getMessage());
            }
        });

        NikeFunApplication.getInstance().addToRequestQueue(request);

    }


}
