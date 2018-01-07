package senanayake.udayanga.com.bustime;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button btnViewTime, btnStartTime, btnEndTime;
    private CheckBox checkTimeFilter;
    Spinner spinnerFrom, spinnerTo;
    private RadioGroup radioGroup;
    private RadioButton place, from;
    int radioId = 0;

    private static final String TAG = "Main Activity";
    DBHelper helper = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fillSpinnerFrom(helper.getAddedLocations());
        fillSpinnerTo(helper.getToLocations());


//        setEnabledFalseAll();
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

        radioGroup = findViewById(R.id.radioSearchOption);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                onRadioButtonChanged(radioGroup, i);
            }
        });


    }

    public void fillSpinnerFrom(List<String> locations) {
        spinnerFrom = findViewById(R.id.spinnerFrom);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, locations);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFrom.setAdapter(dataAdapter);
    }

    public void fillSpinnerTo(List<String> locations) {
        spinnerTo = findViewById(R.id.spinnerTo);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, locations);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTo.setAdapter(dataAdapter);
    }

    public void onRadioButtonChanged(RadioGroup radioGroup, int i) {
        btnViewTime = findViewById(R.id.btnViewTime);
        btnViewTime.setEnabled(true);
        radioId = i;
        if (i == 2131230840) {
            fillSpinnerFrom(helper.getAddedLocations());

        } else if (i == 2131230839) {
            fillSpinnerFrom(helper.getFromLocations());
        }

        Toast.makeText(MainActivity.this, String.valueOf(i), Toast.LENGTH_SHORT).show();
        Log.d(TAG, String.valueOf(i));
    }

    public void onViewTimeButtonClick() {
        Intent intent = new Intent(MainActivity.this, ViewTime.class);
        spinnerFrom = findViewById(R.id.spinnerFrom);
        spinnerTo = findViewById(R.id.spinnerTo);
        radioGroup = findViewById(R.id.radioSearchOption);
        intent.putExtra("radioValue", radioGroup.getCheckedRadioButtonId());
        intent.putExtra("fromLocation", spinnerFrom.getSelectedItem().toString());
        intent.putExtra("toLocation", spinnerTo.getSelectedItem().toString());
        startActivity(intent);
    }

    public void onAddTimeClick() {
        Intent intent = new Intent(MainActivity.this, AddTime.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
