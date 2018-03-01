package senanayake.udayanga.com.bustime.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import senanayake.udayanga.com.bustime.R;

public class ExportDataActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_FILE = 0;
    private View mLayout;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export_data);
        mLayout = findViewById(R.id.main_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        button = findViewById(R.id.export);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFilePreview();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        // BEGIN_INCLUDE(onRequestPermissionsResult)
        if (requestCode == PERMISSION_REQUEST_FILE) {
            // Request for camera permission.
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                exportData();
            } else {
                // Permission request was denied.
                Snackbar.make(mLayout, R.string.file_permission_denied,
                        Snackbar.LENGTH_SHORT)
                        .show();
            }
        }
    }

    private void showFilePreview() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            exportData();
        } else {
            requestFilePermission();
        }
    }

    private void requestFilePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Snackbar.make(mLayout, R.string.camera_access,
                    Snackbar.LENGTH_INDEFINITE).setAction(R.string.ok, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ActivityCompat.requestPermissions(ExportDataActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_FILE);
                }
            }).show();
        } else {
            Snackbar.make(mLayout, R.string.permision_available,
                    Snackbar.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_FILE);
        }
    }

    private void exportData() {
        Toast.makeText(ExportDataActivity.this, "Exporting data", Toast.LENGTH_SHORT).show();
    }

}
