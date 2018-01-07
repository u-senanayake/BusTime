package senanayake.udayanga.com.bustime;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewTime extends AppCompatActivity {
    DBHelper helper = new DBHelper(this);
    String TAG = "View time";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_time);
        Intent intent = getIntent();
        int radioId = intent.getIntExtra("radioValue", 0);
        String fromLocation = intent.getStringExtra("fromLocation");
        String toLocation = intent.getStringExtra("toLocation");
        Log.d(TAG, "Searching routs from " + fromLocation + " to " + toLocation + String.valueOf(radioId));
//        Toast.makeText(ViewTime.this, String.valueOf(radioId), Toast.LENGTH_SHORT).show();

        getRoutes(radioId, fromLocation, toLocation);

    }

    public void getRoutes(int radioId, String from, String to) {
        ArrayList<HashMap<String, String>> busList = null;
        if (radioId == 2131230840) {
            busList = helper.getRoutesByAddedLocation(from, to);
            if (busList.size() != 0) {
                ListAdapter adapter = new SimpleAdapter(this, busList,
                        R.layout.bus_list,
                        new String[]{
                                "added_place", "start", "end", "route", "time"},
                        new int[]{
                                R.id.txtPlace, R.id.txtFrom, R.id.txtTo, R.id.txtRoute, R.id.txtTime});
                ListView myList = findViewById(R.id.list_jason);
                myList.setAdapter(adapter);
            }
        } else if (radioId == 2131230839) {
            busList = helper.getRoutesByFromLocation(from, to);
            if (busList.size() != 0) {
                ListAdapter adapter = new SimpleAdapter(this, busList,
                        R.layout.bus_list,
                        new String[]{
                                "added_place", "start", "end", "route", "time"},
                        new int[]{
                                R.id.txtPlace, R.id.txtFrom, R.id.txtTo, R.id.txtRoute, R.id.txtTime});
                ListView myList = findViewById(R.id.list_jason);
                myList.setAdapter(adapter);
            }
        }

    }
}
