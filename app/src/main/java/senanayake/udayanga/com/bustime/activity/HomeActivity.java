package senanayake.udayanga.com.bustime.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;

import java.util.List;

import senanayake.udayanga.com.bustime.R;
import senanayake.udayanga.com.bustime.adapter.DBHelper;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Button btnViewTime, btnStartTime, btnEndTime;
    private CheckBox checkTimeFilter;
    AutoCompleteTextView textFrom, textTo;
    Button clear_from, clear_to;

    private static final String TAG = "Main Activity";
    DBHelper helper = new DBHelper(this);
    String selectedName = "My Location";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_my_location:
                    fillSpinnerFrom(helper.getAddedLocations());
                    selectedName = "My Location";
                    return true;
                case R.id.navigation_from:
                    fillSpinnerFrom(helper.getFromLocations());
                    selectedName = "From To";
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fillSpinnerFrom(helper.getAddedLocations());
        fillSpinnerTo(helper.getToLocations());


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddTimeClick();
            }
        });

        btnViewTime = findViewById(R.id.btnViewTime);
        btnViewTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onViewTimeButtonClick();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        clearField();
    }

    public void fillSpinnerFrom(List<String> locations) {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, locations);

        textFrom = findViewById(R.id.textFrom);
        textFrom.setAdapter(dataAdapter);

    }

    public void fillSpinnerTo(List<String> locations) {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, locations);
        textTo = findViewById(R.id.textTo);
        textTo.setAdapter(dataAdapter);

    }

    public void onViewTimeButtonClick() {
        Intent intent = new Intent(HomeActivity.this, MainActivity.class);
        textFrom = findViewById(R.id.textFrom);
        textTo = findViewById(R.id.textTo);

        intent.putExtra("selectedName", selectedName);
        intent.putExtra("fromLocation", textFrom.getText().toString());
        intent.putExtra("toLocation", textTo.getText().toString());
        startActivity(intent);
    }

    public void onAddTimeClick() {
        Intent intent = new Intent(HomeActivity.this, AddTimeActivity.class);
        startActivity(intent);
    }

    public void clearField() {
        clear_from = findViewById(R.id.clear_from);
        clear_to = findViewById(R.id.clear_to);
        textFrom = findViewById(R.id.textFrom);
        textTo = findViewById(R.id.textTo);
        clear_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textFrom.setText("");
            }
        });
        clear_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textTo.setText("");
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_home, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_export) {
            // Handle the camera action
        } else if (id == R.id.nav_import) {

        } else if (id == R.id.nav_upload) {

        } else if (id == R.id.nav_settings) {
            Intent intent= new Intent(HomeActivity.this, SettingsActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_rate) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
