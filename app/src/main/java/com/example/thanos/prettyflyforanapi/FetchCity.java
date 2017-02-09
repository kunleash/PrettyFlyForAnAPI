package com.example.thanos.prettyflyforanapi;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by User on 27/1/2017.
 */

@RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
public class FetchCity extends AsyncTask<String ,Void, Void>{
    private final String LOG_TAG = FetchCity.class.getSimpleName();



    public FetchCity(){

    }
    String[] resultStrsLabels=null;
    String[] resultStrsValues=null;

    private Void getAirportsFromJson(String cityJsonStr)
            throws JSONException {

        // These are the names of the JSON objects that need to be extracted.
        //final String OWM_LIST = "list";
        final String OMW_VALUE = "value";
        final String OWM_LABEL = "label";


        try {
            JSONObject cityJson = new JSONObject(cityJsonStr);
            JSONArray airportArray = cityJson.getJSONArray("");
            int length = airportArray.length();
            resultStrsLabels = new String[length];
            resultStrsValues = new String[length];

            for (int i = 0; i < airportArray.length(); i++) {
                // For now, using the format "Day, description, hi/low"
                String value;
                String label;

                JSONObject airport = airportArray.getJSONObject(i);


                value = airport.getString(OMW_VALUE);
                label = airport.getString(OWM_LABEL);

                resultStrsValues[i] = "value: " + value;
                resultStrsLabels[i] = "label: " + label;

            }
            for (String s : resultStrsLabels) {
                Log.v(LOG_TAG, "airport entry: " + s);
            }

        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }return null;

    }


    @Override
    protected Void doInBackground(String... params) {

        if (params.length == 0) {

        }
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String cityJsonStr = null;
        String cityName= params[0];



        try {

            final String baseUrl = "https://api.sandbox.amadeus.com/v1.2/airports/autocomplete?";
            final String apiKeyParam = "apikey";
            final String cityNameParam = "term";



            Uri builtUri = Uri.parse(baseUrl).buildUpon()
                    .appendQueryParameter(apiKeyParam,"gK8OrdNsPT0QPLlYodmWK8ukgVCqelmT")
                    .appendQueryParameter(cityNameParam,cityName)//??
                    .build();

            URL url = new URL(builtUri.toString());

            Log.v(LOG_TAG, "Built URI: "+builtUri.toString());

            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.

            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.

            }
            cityJsonStr = buffer.toString();
            Log.v(LOG_TAG,"Airports from JSON String: "+cityJsonStr);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attemping
            // to parse it.

        } finally{
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
        try {
            getAirportsFromJson(cityJsonStr);
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }


        return null;
    }
}
