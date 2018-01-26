package senanayake.udayanga.com.bustime;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditRoute extends AppCompatActivity {
    String TAG = "Edit Route";
    String keyId = "id", keyPlaceAdded = "placeAdded", keyFrom = "from", keyTo = "to", keyRoute = "route", keyTime = "time";
    EditText editRoute, editLocation, editFrom, editTo, editTime;
    Button updateRoute;
    int id;
    DBHelper helper= new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_route);
        fillValues();
        updateRoute= findViewById(R.id.updateRoute);
        updateRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateRoute();
            }
        });

    }

    private void fillValues() {
        Intent intent = getIntent();
        id = intent.getIntExtra(keyId, 0);
        String placeAdded = intent.getStringExtra(keyPlaceAdded);
        String from = intent.getStringExtra(keyFrom);
        String to = intent.getStringExtra(keyTo);
        int route = intent.getIntExtra(keyRoute, 0);
        String time = intent.getStringExtra(keyTime);

        editRoute = findViewById(R.id.editRoute);
        editLocation = findViewById(R.id.editLocation);
        editFrom = findViewById(R.id.editFrom);
        editTo = findViewById(R.id.editTo);
        editTime = findViewById(R.id.editTime);

        editRoute.setText(Integer.toString(route));
        editLocation.setText(placeAdded);
        editFrom.setText(from);
        editTo.setText(to);
        editTime.setText(time);

    }
    private void updateRoute(){
        Log.d(TAG, "Updating route");
        editRoute = findViewById(R.id.editRoute);
        editLocation = findViewById(R.id.editLocation);
        editFrom = findViewById(R.id.editFrom);
        editTo = findViewById(R.id.editTo);
        editTime = findViewById(R.id.editTime);

        Route route= new Route();

        route.setTime(String.valueOf(editTime.getText()));
        route.setId(id);
        route.setRouteNo(Integer.parseInt(String.valueOf(editRoute.getText())));
        route.setTo(String.valueOf(editTo.getText()));
        route.setFrom(String.valueOf(editFrom.getText()));
        route.setPlaceAdded(String.valueOf(editLocation.getText()));

        helper.updateRoute(route);
        Toast.makeText(this, "Route updating success", Toast.LENGTH_SHORT).show();
    }


}
