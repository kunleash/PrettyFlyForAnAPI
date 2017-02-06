package com.example.thanos.prettyflyforanapi;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public abstract class FetchFlights extends AsyncTask<String , Void , Void > {

    private final String LOG_TAG = FetchFlights.class.getSimpleName();

    private final Context mContext;

    public FetchFlights(Context context) {
        mContext = context;
    }

    private void getWeatherDataFromJson(String flightJsonStr)
            throws JSONException {

        

    }

    protected Void doInBackground(String... params) {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // For URL built
        String CityJsonStr = null;
        String CityAirport = null;
        String Destination = null;
        String Departure_Date=null;
        String Return_Date=null;
        String Num_of_Adults = null;
        String Num_of_Children= null;
        String Num_of_Infants=null;
        String Max_Price=null;
        String NonStop = null;
        String Num_of_Results= null;



        /*https://api.sandbox.amadeus.com/v1.2/flights/low-fare-search?
        apikey=YOUR_KEY
        &origin=SKG
        &destination=DUS
        &departure_date=2016-11-25
        &return_date=2016-11-28
        &adults=1
        &nonstop=true
        &max_price=400
        &currency=EUR
        &number_of_results=10
        */


        try {
            // Construct the URL for query
            final String baseUrl = "https://api.sandbox.amadeus.com/v1.2/airports/autocomplete?";
            final String API_KEY_PARAM = "apikey";
            final String ORIGIN_PARAM = "origin";
            final String DESTINATION_PARAM ="destination";
            final String DEPARTURE_DATE_PARAM ="departure_date";
            final String RETURN_DATE_PARAM="return_date";
            final String ADULTS_PARAM="adults";
            final String CHILDREN_PARAM="children";
            final String INFANTS_PARAM="infants";
            final String NONSTOP_PARAM="nonstop";
            final String MAX_PRICE_PARAM="max_price";
            final String NUMBER_OF_RESULTS_PARAM="number_of_results";



            Uri builtUri = Uri.parse(baseUrl).buildUpon()
                    .appendQueryParameter(API_KEY_PARAM, "gK8OrdNsPT0QPLlYodmWK8ukgVCqelmT")
                    .appendQueryParameter(ORIGIN_PARAM, CityAirport)
                    .appendQueryParameter(DESTINATION_PARAM,Destination)
                    .appendQueryParameter(DEPARTURE_DATE_PARAM,Departure_Date)
                    .appendQueryParameter(RETURN_DATE_PARAM,Return_Date)
                    .appendQueryParameter(ADULTS_PARAM,Num_of_Adults)
                    .appendQueryParameter(CHILDREN_PARAM,Num_of_Children)
                    .appendQueryParameter(INFANTS_PARAM,Num_of_Infants)
                    .appendQueryParameter(NONSTOP_PARAM,NonStop)
                    .appendQueryParameter(MAX_PRICE_PARAM,Max_Price)
                    .appendQueryParameter(NUMBER_OF_RESULTS_PARAM,Num_of_Results)
                    .build();

            URL url = new URL(builtUri.toString());


            // Create the request to get the city code, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            //if (buffer == null)
            //return null;
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            } else
                CityJsonStr = buffer.toString();
        } catch (IOException e) {
            Log.e("PlaceholderFragment", "Error ", e);
            // If the code didn't successfully get the flight data, there's no point in attemping
            // to parse it.
            return null;

        } finally {
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
    return null;
    }


}


