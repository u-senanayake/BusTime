package senanayake.udayanga.com.bustime;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Udayanga on 12/10/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final int databaseVersion = 1;
    private static final String databaseName = "bus_time";
    private static final String tableName = "bus_time";

    private static final String keyId = "id";
    private static final String keyAddedPlace = "added_place";
    private static final String keyFrom = "start";
    private static final String keyTo = "end";
    private static final String keyRoute = "route";
    private static final String keyTime = "time";

    private static final String TAG = "DBHelper";

    public DBHelper(Context context) {
        super(context, databaseName, null, databaseVersion);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d(TAG, "Creating table...");
        String createTable = "CREATE TABLE " + tableName + " ( " +
                keyId + " INTEGER PRIMARY KEY, " +
                keyAddedPlace + " TEXT, " +
                keyFrom + " TEXT, " +
                keyTo + " TEXT, " +
                keyTime + " TEXT, " +
                keyRoute + " INTEGER)";
        sqLiteDatabase.execSQL(createTable);
        Log.d(TAG, "Table successfully created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + tableName);
        onCreate(sqLiteDatabase);
    }

    public void addRoute(Route route) {
        Log.d(TAG, "Inserting new route to database");
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(keyAddedPlace, route.getPlaceAdded());
        values.put(keyFrom, route.getFrom());
        values.put(keyTo, route.getTo());
        values.put(keyRoute, route.getRouteNo());
        values.put(keyTime, route.getTime());
        database.insert(tableName, null, values);
        database.close();
        Log.d(TAG, "New route adding success");

    }

    public Route getRoute(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(tableName, new String[]{keyAddedPlace,
                        keyFrom, keyTo, keyRoute, keyTime}, keyId + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Route route = new Route(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(3),
                cursor.getString(4),
                Integer.parseInt(cursor.getString(5)),
                cursor.getString(6));
        return route;
    }


    public ArrayList<Route> getAllRoutes() {
        ArrayList<Route> routeList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + tableName;

        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Route route = new Route();
                route.setId(cursor.getInt(0));
                route.setPlaceAdded(cursor.getString(1));
                route.setFrom(cursor.getString(2));
                route.setTo(cursor.getString(3));
                route.setRouteNo(cursor.getInt(5));
                route.setTime(cursor.getString(4));
                routeList.add(route);

            } while (cursor.moveToNext());

        }
        return routeList;
    }

    public ArrayList<Route> getRoutesByAddedLocation(String from, String to) {
        ArrayList<Route> routeList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + tableName + " where " + keyAddedPlace + " = " + "'" + from + "' ORDER BY " + keyTo + " ASC";
        Log.d(TAG, "Executing Query " + selectQuery);
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Route route = new Route();
                route.setId(cursor.getInt(0));
                route.setPlaceAdded(cursor.getString(1));
                route.setFrom(cursor.getString(2));
                route.setTo(cursor.getString(3));
                route.setRouteNo(cursor.getInt(5));
                route.setTime(cursor.getString(4));
                routeList.add(route);

            } while (cursor.moveToNext());

        }
        return routeList;
    }

    public ArrayList<Route> getRoutesByFromLocation(String from, String to) {
        ArrayList<Route> routeList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + tableName + " where " + keyFrom + " = " + "'" + from + "' ORDER BY " + keyTo + " ASC";
        Log.d(TAG, "Executing Query " + selectQuery);
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Route route = new Route();
                route.setId(cursor.getInt(0));
                route.setPlaceAdded(cursor.getString(1));
                route.setFrom(cursor.getString(2));
                route.setTo(cursor.getString(3));
                route.setRouteNo(cursor.getInt(5));
                route.setTime(cursor.getString(4));
                routeList.add(route);
            } while (cursor.moveToNext());
        }
        return routeList;
    }

    public List<String> getAddedLocations() {
        String selectQuery = "SELECT  * FROM " + tableName + " GROUP BY " + keyAddedPlace;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        List<String> locations = new ArrayList<String>();
        int i = 0;
        while (cursor.moveToNext()) {
            Log.d(TAG, String.valueOf(cursor.getColumnIndex(keyAddedPlace)));
            locations.add(cursor.getString(cursor.getColumnIndex(keyAddedPlace)));
            i++;
        }
        return locations;
    }

    public List<String> getFromLocations() {
        String selectQuery = "SELECT  * FROM " + tableName + " GROUP BY " + keyFrom;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        List<String> locations = new ArrayList<String>();
        int i = 0;
        while (cursor.moveToNext()) {
            Log.d(TAG, String.valueOf(cursor.getColumnIndex(keyFrom)));
            locations.add(cursor.getString(cursor.getColumnIndex(keyFrom)));
            i++;
        }
        return locations;
    }

    public List<String> getToLocations() {
        String selectQuery = "SELECT  * FROM " + tableName + " GROUP BY " + keyTo;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        List<String> locations = new ArrayList<String>();
        int i = 0;
        while (cursor.moveToNext()) {
            locations.add(cursor.getString(cursor.getColumnIndex(keyTo)));
            i++;
        }
        return locations;
    }

    public int updateRoute(Route route) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(keyId, route.getId());
        values.put(keyAddedPlace, route.getPlaceAdded());
        values.put(keyFrom, route.getFrom());
        values.put(keyTo, route.getTo());
        values.put(keyRoute, route.getRouteNo());
        values.put(keyTime, route.getTime());

        // updating row
        return db.update(tableName, values, keyId + " = ?",
                new String[]{String.valueOf(route.getId())});
    }

    public void deleteContact(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tableName, keyId + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }
}
