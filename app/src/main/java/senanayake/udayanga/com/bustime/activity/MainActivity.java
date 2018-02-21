package senanayake.udayanga.com.bustime.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Objects;

import senanayake.udayanga.com.bustime.R;
import senanayake.udayanga.com.bustime.adapter.DBHelper;
import senanayake.udayanga.com.bustime.adapter.DataAdapter;
import senanayake.udayanga.com.bustime.model.Route;

public class MainActivity extends AppCompatActivity {
    DBHelper helper = new DBHelper(this);
    String TAG = "View time";
    private DataAdapter adapter;
    String keyId = "id", keyPlaceAdded = "placeAdded", keyFrom = "from", keyTo = "to", keyRoute = "route", keyTime = "time";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        String radioId = intent.getStringExtra("radioValue");
        String fromLocation = intent.getStringExtra("fromLocation");
        String toLocation = intent.getStringExtra("toLocation");
        Log.d(TAG, "Searching routs from " + fromLocation + " to " + toLocation + String.valueOf(radioId));

//        getRoutes(radioId, fromLocation, toLocation);

        adapter = new DataAdapter(this, getData(radioId, fromLocation, toLocation));
        ListView myList = findViewById(R.id.list_jason);
        myList.setAdapter(adapter);
        onListItemClick(myList);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private ArrayList<Route> getData(String radioId, String from, String to) {
        ArrayList<Route> routes = helper.getAllRoutes();
        if (Objects.equals(radioId, "My Location")) {
            routes = helper.getRoutesByAddedLocation(from, to);
        } else if (Objects.equals(radioId, "From To")) {
            routes = helper.getRoutesByFromLocation(from, to);
        }
        return routes;
    }

    public void onListItemClick(ListView myList) {
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String selectedItem = String.valueOf(adapterView.getItemAtPosition(position));
                Route route = (Route) adapter.getItem(position);
                Intent intent = new Intent(MainActivity.this, EditRouteActivity.class);
                intent.putExtra(keyId, route.getId());
                intent.putExtra(keyPlaceAdded, route.getPlaceAdded());
                intent.putExtra(keyFrom, route.getFrom());
                intent.putExtra(keyTo, route.getTo());
                intent.putExtra(keyRoute, route.getRouteNo());
                intent.putExtra(keyTime, route.getTime());

                startActivity(intent);
            }
        });


    }
}
