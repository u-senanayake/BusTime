package senanayake.udayanga.com.bustime.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import au.com.bytecode.opencsv.CSVWriter;
import senanayake.udayanga.com.bustime.R;
import senanayake.udayanga.com.bustime.adapter.DBHelper;

public class ExportDataActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_FILE = 0;
    private View mLayout;
    Button button, btn_open;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export_data);
        mLayout = findViewById(R.id.main_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        button = findViewById(R.id.export);
        btn_open = findViewById(R.id.open_file);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkExportPermission();
            }
        });
        btn_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                openDialog();
                checkImportPermission();
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
//                importData();
            } else {
                // Permission request was denied.
                Snackbar.make(mLayout, R.string.file_permission_denied,
                        Snackbar.LENGTH_SHORT)
                        .show();
            }
        }
    }

    private void checkExportPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            exportData();
        } else {
            requestFilePermission();
        }
    }

    private void checkImportPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
//            importData();
            openDialog();
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

    private static final int READ_REQUEST_CODE = 42;

    private void openDialog() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        startActivityForResult(intent, READ_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri uri;
            if (resultData != null) {
                uri = resultData.getData();
                Log.i("Export", "Uri: " + uri.toString());
                importData(uri);
            }
        }
    }

    private void importData(Uri uri) {
        Toast.makeText(ExportDataActivity.this, "Importing data", Toast.LENGTH_SHORT).show();

        Log.d("Importing", uri.getPath());
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    inputStream));
            String line = "";
            String tableName = "bus_time";
            String columName = "id, added_place, start, end, time,route ";
            String query = "INSERT INTO " + tableName + " ( " + columName + " ) values (";
            String query2 = ");";
            DBHelper dbHelper = new DBHelper(getApplicationContext());
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            db.beginTransaction();
            while ((line = reader.readLine()) != null) {
                StringBuilder sb = new StringBuilder(query);
                String[] str = line.split(",");
                sb.append(str[0]);
                sb.append(",'" + str[1] + "',");
                sb.append("'" + str[2] + "',");
                sb.append("'" + str[3] + "',");
                sb.append("'" + str[4] + "',");
                sb.append(str[5]);
                sb.append(query2);
                db.execSQL(sb.toString());
            }
            db.setTransactionSuccessful();
            db.endTransaction();
            Toast.makeText(ExportDataActivity.this, "Importing Success", Toast.LENGTH_SHORT).show();


        } catch (FileNotFoundException e) {
            Toast.makeText(ExportDataActivity.this, "File format not supporting. Please check and try again", Toast.LENGTH_SHORT).show();

            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(ExportDataActivity.this, "File format not supporting. Please check and try again", Toast.LENGTH_SHORT).show();

        }

    }

    private void exportData() {
        Toast.makeText(ExportDataActivity.this, "Exporting data", Toast.LENGTH_SHORT).show();

        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
        dateFormatter.setLenient(false);
        String dateS = dateFormatter.format(date);
        File dbFile = getDatabasePath("bus_time");
        DBHelper dbHelper = new DBHelper(getApplicationContext());
        File exportDir = new File(Environment.getExternalStorageDirectory(), "/BusTime/");
        if (!exportDir.exists()) {
            exportDir.mkdirs();
        }
        File file = new File(exportDir, dateS + "bus_time.csv");
        try {
            file.createNewFile();
            CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursorCSV = db.rawQuery("select * from bus_time", null);
            csvWrite.writeNext(cursorCSV.getColumnNames());
            while (cursorCSV.moveToNext()) {
                String arrStr[] = {cursorCSV.getString(0), cursorCSV.getString(1), cursorCSV.getString(2), cursorCSV.getString(3), cursorCSV.getString(4), cursorCSV.getString(5)};
                csvWrite.writeNext(arrStr);

            }
            csvWrite.close();
            cursorCSV.close();
            Toast.makeText(ExportDataActivity.this, "Data exported to /BusTime/ successfully ", Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            Log.e("MainActivity", e.getMessage(), e);
        }
    }

}
