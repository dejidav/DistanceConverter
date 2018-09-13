package e.dav.distanceconverter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.database.DatabaseUtils;

public class DataManager {
    private SQLiteDatabase db;


    public static final String TABLE_ROW_ID = "_id";
    public static final String TABLE_ROW_PLACEA = "place_a";
    public static final String TABLE_ROW_PLACEB = "place_b";
    public static final String TABLE_ROW_DIST = "distance";

    private static final String DB_NAME = "place_book_db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_N_AND_A =
            "place_and_distance";


    public DataManager(Context context) {
        // Create an instance of our internal
        //CustomSQLiteOpenHelper class
        CustomSQLiteOpenHelper helper =
                new CustomSQLiteOpenHelper(context);
        // Get a writable database
        db = helper.getWritableDatabase();
        }


    public void insert(distanceAB d){
        // Add all the details to the table
        String query = "INSERT INTO " + TABLE_N_AND_A + " (" +
                TABLE_ROW_PLACEA + ", " +
                TABLE_ROW_PLACEB + "," +
                TABLE_ROW_DIST + ") " +
                "VALUES (" +
                "'" + d.getPlaceA() + "'" + ", " +
                "'" + d.getPlaceB() + "'" +", " +
                "'" + d.getDistance() + "'" +
                "); ";
        Log.i("insert() = ", query);
        db.execSQL(query);
    }

    // Delete a record
    public void delete(String placea){
        // Delete the details from the table if already exists
        String query = "DELETE FROM " + TABLE_N_AND_A +
                " WHERE " + TABLE_ROW_PLACEA +
                " = '" + placea + "';";
        Log.i("delete() = ", query);
        db.execSQL(query);
    }

    // Get all the records
    public Cursor selectAll() {
        Cursor c = db.rawQuery("SELECT *" +" from " +
                TABLE_N_AND_A, null);
        return c;
    }

    // Find a specific record
    public Cursor searchName(String placea) {
        String query = "SELECT " +
                TABLE_ROW_ID + ", " +
                TABLE_ROW_PLACEA +
                ", " + TABLE_ROW_PLACEB +
                ", " + TABLE_ROW_DIST +
                " from " +
                TABLE_N_AND_A + " WHERE " +
                TABLE_ROW_PLACEA + " = '" + placea + "';";
        Log.i("searchName() = ", query);
        Cursor c = db.rawQuery(query, null);
        return c;
    }

    public Cursor getCount(){
        String query = "SELECT COUNT(*) FROM"+ TABLE_N_AND_A+";";

        Cursor c = db.rawQuery(query, null);
        return c;
    }

    public long getProfilesCount() {
        long count = DatabaseUtils.queryNumEntries(db, TABLE_N_AND_A);
        db.close();
        return count;
    }


    // This class is created when our DataManager is initialized
    private class CustomSQLiteOpenHelper extends SQLiteOpenHelper {
        public CustomSQLiteOpenHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }
        // This method only runs the first time the database is created
        @Override
        public void onCreate(SQLiteDatabase db) {
            // Create a table for photos and all their details
            String newTableQueryString = "create table "
                    + TABLE_N_AND_A + " ("
                    + TABLE_ROW_ID
                    + " integer primary key autoincrement not null,"
                    + TABLE_ROW_PLACEA
                    + " text not null,"
                    + TABLE_ROW_PLACEB
                    + " text not null,"
                    + TABLE_ROW_DIST
                    + " text not null);";
            db.execSQL(newTableQueryString);
        }
        // This method only runs when we increment DB_VERSION
        // We will look at this in chapter 26
        @Override
        public void onUpgrade(SQLiteDatabase db,
                              int oldVersion, int newVersion) {
        } }

}
