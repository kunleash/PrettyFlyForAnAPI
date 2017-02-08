package com.example.thanos.prettyflyforanapi;

import android.support.v4.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by User on 6/2/2017.
 */

public class FlightFragment extends Fragment{ //implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String LOG_TAG = FlightFragment.class.getSimpleName();



    public FlightFragment(){

    }



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.fragment_flight_booker, container, false);

        return rootView;
    }


    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager();
        super.onActivityCreated(savedInstanceState);
    }
    public void updateAirports(String airport) {
        FetchCity fetcher = new FetchCity();
        fetcher.execute(airport);
    }






        // Now create and return a CursorLoader that will take care of
        // creating a Cursor for the data being displayed.

}

