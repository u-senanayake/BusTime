package senanayake.udayanga.com.bustime.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Objects;

import senanayake.udayanga.com.bustime.R;
import senanayake.udayanga.com.bustime.adapter.DBHelper;
import senanayake.udayanga.com.bustime.adapter.DataAdapter;
import senanayake.udayanga.com.bustime.model.Route;

public class ViewTimeActivity extends AppCompatActivity {
    DBHelper helper = new DBHelper(this);
    String TAG = "View time";
    private DataAdapter adapter;
    String keyId = "id", keyPlaceAdded = "placeAdded", keyFrom = "from", keyTo = "to", keyRoute = "route", keyTime = "time";
    private Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_time);
        Intent intent = getIntent();
        String radioId = intent.getStringExtra("radioValue");
        String fromLocation = intent.getStringExtra("fromLocation");
        String toLocation = intent.getStringExtra("toLocation");
        Log.d(TAG, "Searching routs from " + fromLocation + " to " + toLocation + String.valueOf(radioId));
//        Toast.makeText(ViewTimeActivity.this, String.valueOf(radioId), Toast.LENGTH_SHORT).show();

//        getRoutes(radioId, fromLocation, toLocation);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        adapter = new DataAdapter(this, getData(radioId, fromLocation, toLocation));
        ListView myList = findViewById(R.id.list_jason);
        myList.setAdapter(adapter);
        onListItemClick(myList);

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
                Intent intent = new Intent(ViewTimeActivity.this, EditRouteActivity.class);
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_back, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
