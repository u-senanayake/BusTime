package senanayake.udayanga.com.bustime;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ViewTime extends AppCompatActivity {
    DBHelper helper = new DBHelper(this);
    String TAG = "View time";
    private DataAdapter adapter;
    String keyId = "id", keyPlaceAdded = "placeAdded", keyFrom = "from", keyTo = "to", keyRoute = "route", keyTime = "time";

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

//        getRoutes(radioId, fromLocation, toLocation);
        adapter = new DataAdapter(this, getData(radioId, fromLocation, toLocation));
        ListView myList = findViewById(R.id.list_jason);
        myList.setAdapter(adapter);
        onListItemClick(myList);

    }

    private ArrayList<Route> getData(int radioId, String from, String to) {
        ArrayList<Route> routes = helper.getAllRoutes();
        if (radioId == 2131230840) {
            routes = helper.getRoutesByAddedLocation(from, to);
        } else if (radioId == 2131230839) {
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
                Intent intent = new Intent(ViewTime.this, EditRoute.class);
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
