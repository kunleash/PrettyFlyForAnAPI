package com.example.thanos.prettyflyforanapi;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public abstract class FetchFlights extends AsyncTask<String , Void , String > {

    private final String LOG_TAG = FetchFlights.class.getSimpleName();



    public FetchFlights() {

    }

    private void getFlightDataFromJson(String flightJsonStr)
            throws JSONException {

        final String OMW_CURRENCY= "currency";
        final String OMW_RESULTS = "results";

        final String OMW_ITINERARIES="itineraries";

        final String OMW_OUTBOUND ="outbound";
        final String OMW_FLIGHTS="flights";
        final String OMW_DEPARTS_AT="departs_at";
        final String OMW_ARRIVES_AT="arrives_at";

        final String OMW_ORIGIN="origin";
        final String OMW_AIRPORT="airport";
        final String OMW_DESTINATION="destination";
        final String OMW_AIRPORTD="airport";

        final String OMW_OPERATING_AIRLINE="operating_airline";
        final String OMW_FLIGHT_NUMBER="flight_number";
        final String OMW_AIRCRAFT="aircraft";
        final String OMW_SEATS_REMAINING="seats_remaining";
        final String OMW_INBOUND="inbound";

        final String OMW_FARE="fare";
        final String OMW_TOTAL_PRICE = "total_price";



        try {
            //Outbound Details
            String outbound_departs_at=null;
            String outbound_arrives_at=null;
            String outbound_origin_airport = null;
            String outbound_destination_airport = null;
            String outbound_operating_airline =null;
            String outbound_flight_number=null;
            //Inbound Details
            String inbound_departs_at=null;
            String inbound_arrives_at=null;
            String inbound_origin_airport = null;
            String inbound_destination_airport = null;
            String inbound_operating_airline =null;
            String inbound_flight_number=null;

            String total_price=null;

            //The whole JSON String
            JSONObject response = new JSONObject(flightJsonStr);

            if (response.has(OMW_RESULTS)){
                //Only the "results" item
                JSONObject results = response.getJSONObject(OMW_RESULTS);
                //Grabbing results Array from results item
                JSONArray resultArray = results.getJSONArray(OMW_RESULTS);
                for (int i = 0 ; i <resultArray.length();i++) {
                    JSONObject result = resultArray.getJSONObject(i);

                    if (result.has(OMW_ITINERARIES)) {
                        //Only the "itineraries" item
                        JSONObject itineraries = result.getJSONObject(OMW_ITINERARIES);
                        //Grabbing the itineraries Array from "itineraries" item
                        JSONArray itinerariesArray = itineraries.getJSONArray(OMW_ITINERARIES);
                        if (itineraries.has(OMW_OUTBOUND)) {
                            //Only the "outbound" item
                            JSONObject outbound = itineraries.getJSONObject(OMW_OUTBOUND);

                            if (outbound.has(OMW_FLIGHTS)) {
                                JSONObject flights = outbound.getJSONObject(OMW_FLIGHTS);
                                if ((flights.has(OMW_DEPARTS_AT) && flights.has(OMW_ARRIVES_AT))) {
                                    outbound_departs_at = flights.getJSONObject(OMW_DEPARTS_AT).toString();
                                    outbound_arrives_at = flights.getJSONObject(OMW_ARRIVES_AT).toString();
                                }
                                if (flights.has(OMW_ORIGIN)) {
                                    outbound_origin_airport = flights.getJSONObject(OMW_ORIGIN).getJSONObject(OMW_AIRPORT).toString();
                                }
                                if (flights.has(OMW_DESTINATION)) {
                                    outbound_destination_airport = flights.getJSONObject(OMW_DESTINATION).getJSONObject(OMW_AIRPORT).toString();
                                }
                                if (flights.has(OMW_OPERATING_AIRLINE)) {
                                    outbound_operating_airline = flights.getJSONObject(OMW_OPERATING_AIRLINE).toString();
                                }
                                if (flights.has(OMW_FLIGHT_NUMBER)) {
                                    outbound_flight_number = flights.getJSONObject(OMW_FLIGHT_NUMBER).toString();
                                }

                            }



                        }
                        if (itineraries.has(OMW_INBOUND)){
                            JSONObject inbound = itineraries.getJSONObject(OMW_INBOUND);
                            if(inbound.has(OMW_FLIGHTS)){
                                JSONObject flights = inbound.getJSONObject(OMW_FLIGHTS);
                                if ((flights.has(OMW_DEPARTS_AT) && flights.has(OMW_ARRIVES_AT))) {
                                    inbound_departs_at = flights.getJSONObject(OMW_DEPARTS_AT).toString();
                                    inbound_arrives_at = flights.getJSONObject(OMW_ARRIVES_AT).toString();
                                }
                                if (flights.has(OMW_ORIGIN)) {
                                    inbound_origin_airport = flights.getJSONObject(OMW_ORIGIN).getJSONObject(OMW_AIRPORT).toString();
                                }
                                if (flights.has(OMW_DESTINATION)) {
                                    inbound_destination_airport = flights.getJSONObject(OMW_DESTINATION).getJSONObject(OMW_AIRPORT).toString();
                                }
                                if (flights.has(OMW_OPERATING_AIRLINE)) {
                                    inbound_operating_airline = flights.getJSONObject(OMW_OPERATING_AIRLINE).toString();
                                }
                                if (flights.has(OMW_FLIGHT_NUMBER)) {
                                    inbound_flight_number = flights.getJSONObject(OMW_FLIGHT_NUMBER).toString();
                                }


                            }


                        }


                    }
                    if (result.has(OMW_FARE)) {
                        //Only the "fares" item
                        JSONObject fares = result.getJSONObject(OMW_FARE);
                        //Grabbing the fares Array from "fares" item
                        JSONArray faresArray = fares.getJSONArray(OMW_FARE);
                        if (fares.has(OMW_TOTAL_PRICE)) {
                            //Grabbing "total_price" as Obj and then casting it to String
                            JSONObject total_priceObj = fares.getJSONObject(OMW_TOTAL_PRICE);
                            total_price = total_priceObj.toString();
                        }

                    }
                }


                // resultArray.getJSONObject(1) is "fare"





                JSONArray fare = resultArray.getJSONArray(1);
                for (int i=0; i < resultArray.length();i++){
                    JSONObject item = resultArray.getJSONObject(i);
                    if(item.has("itineraries")){

                    }
                }
                if (response.has("itineraries")){
                    if(response.has("outbound")){

                    }

                }
            }








        }catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }





    }
    public void updateAirports(){
        AirportsActivity act = new AirportsActivity();
        act.returnAirports();
    }

    protected String doInBackground(String... params) {
        updateAirports();
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // For URL built
        String flightJsonStr = null;
        String CityAirport = params[0];
        String Destination = params[1];
        String Departure_Date=params[3];
        String Return_Date=params[4];
        String Num_of_Adults = params[5];
        String Num_of_Children= "0";
        String Num_of_Infants="0";
        String Max_Price=params[6];
        String NonStop = "true";
        String Currency ="EUR";
        String Num_of_Results= "10";



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
                flightJsonStr = buffer.toString();
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
    return flightJsonStr;
    }


}


