package senanayake.udayanga.com.bustime;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class EditRoute extends AppCompatActivity {
    String TAG = "Edit Route";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_route);

        Intent intent = getIntent();
        String selectedItem = intent.getStringExtra("selectedItem");
        Log.d(TAG, "Showing data " + selectedItem);
        Toast.makeText(EditRoute.this, selectedItem, Toast.LENGTH_SHORT).show();

    }
}
