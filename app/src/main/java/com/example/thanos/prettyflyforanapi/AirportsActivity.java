package com.example.thanos.prettyflyforanapi;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

/**
 * Created by Thanos on 8/2/2017.
 */

public class AirportsActivity extends Activity {



    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.fragment_flight_booker);


        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        AutoCompleteTextView textView1 = (AutoCompleteTextView)findViewById(R.id.InitialAirport);
        textView1.setAdapter(adapter1);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_2);
        AutoCompleteTextView textView2 = (AutoCompleteTextView) findViewById(R.id.DestinationAirport);
        textView2.setAdapter(adapter2);


    }
    public void returnAirports(){

        FetchCity fetchInitial = new FetchCity();
        AutoCompleteTextView initCity = (AutoCompleteTextView) findViewById(R.id.InitialAirport);
        String newCity = initCity.getText().toString();
        fetchInitial.execute(newCity);

        FetchCity fetchDestination = new FetchCity();
        AutoCompleteTextView destCity = (AutoCompleteTextView) findViewById(R.id.DestinationAirport);
        newCity= destCity.getText().toString();
        fetchDestination.execute(newCity);

    }





}
