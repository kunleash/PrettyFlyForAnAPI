package com.example.thanos.prettyflyforanapi;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Thanos on 24/1/2017.
 */

public class FetchFlights extends AsyncTask<void, void, void>{

    @Override
    protected void doInBackground(void... params) {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // For URL built
        String CityJsonStr = null;
        String CityAirport = ;


        try{
            // Construct the URL for query
            final String baseUrl = "https://api.sandbox.amadeus.com/v1.2/airports/autocomplete?";
            final String apiKeyParam = "apikey";
            final String CityParam = "term";

            Uri builtUri = Uri.parse(baseUrl).buildUpon()
                    .appendQueryParameter(apiKeyParam, "gK8OrdNsPT0QPLlYodmWK8ukgVCqelmT")
                    .appendQueryParameter(CityParam, CityAirport)
                    .build();

            URL url = new URL(builtUri.toString());


            // Create the request to get the city code, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (buffer == null)
                return null;
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            CityJsonStr = buffer.toString();
        }
        catch (IOException e) {
            Log.e("PlaceholderFragment", "Error ", e);
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
                    Log.e("PlaceholderFragment", "Error closing stream", e);
                }

            }
    }
}
