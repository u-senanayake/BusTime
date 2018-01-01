package senanayake.udayanga.com.bustime;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewTime extends AppCompatActivity {
    DBHelper helper = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_time);
        ArrayList<HashMap<String, String>> busList = helper.getAllRoutes();
        if (busList.size() != 0) {
            ListAdapter adapter = new SimpleAdapter(this, busList,
                    R.layout.bus_list,
                    new String[]{
                            "added_place", "start", "end", "route", "time"},
                    new int[]{R.id.txtPlace, R.id.txtFrom, R.id.txtTo, R.id.txtRoute, R.id.txtTime});
            ListView myList = findViewById(R.id.list_jason);
            myList.setAdapter(adapter);
        }
    }
}
