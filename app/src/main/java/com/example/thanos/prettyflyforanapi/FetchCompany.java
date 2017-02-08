package com.example.thanos.prettyflyforanapi;

import android.net.Uri;
import android.os.AsyncTask;
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
 * Created by User on 6/2/2017.
 */

public class FetchCompany extends AsyncTask<String ,Void, String[]> {
    private final String LOG_TAG = FetchCompany.class.getSimpleName();

    private String[] getAirlinesFromJson(String airlineJsonStr)
            throws JSONException {
        final String OMW_AIRLINE_NAME ="name";

        JSONObject airlineJson = new JSONObject(airlineJsonStr);
        String[] resultStrs = new String[1];

        String airline = airlineJson.getString(OMW_AIRLINE_NAME);
        resultStrs[1] = "Airline name: " +airline;


        return resultStrs;
    }



    @Override
    protected String[] doInBackground(String... params) {

        if (params.length == 0) {
            return null;
        }
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String AirlineJsonStr = null;
        String FetchedAirline = getAirlineFromJson();

        try {

            final String baseUrl = "https://iatacodes.org/api/v6/airlines?";
            final String apiKeyParam = "apikey";
            final String AirlineCodeParam = "code";

            Uri builtUri = Uri.parse(baseUrl).buildUpon()
                    .appendQueryParameter(apiKeyParam,"da427aab-be42-4f25-85cc-00655b8b8d9c")
                    .appendQueryParameter(AirlineCodeParam,FetchedAirline)//??
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
                return null;
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
                return null;
            }
            AirlineJsonStr = buffer.toString();
            Log.v(LOG_TAG,"Forecast JSON String: "+AirlineJsonStr);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attemping
            // to parse it.
            return null;
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
            return getAirlinesFromJson(AirlineJsonStr);
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }


        return null;
    }







}



