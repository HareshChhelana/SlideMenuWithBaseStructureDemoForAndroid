package com.slidemenubasestructuredemo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.slidemenubasestructuredemo.models.SampleDB;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper databaseHelper = null;
    private int mOpenCounter;
    private SQLiteDatabase mDatabase;

    public DatabaseHelper(Context context) {
        super(context, DatabaseUtilities.DB_NAME, null, DatabaseUtilities.DB_VERSION);
    }

    public static DatabaseHelper getInstance(Context context) {
        if (databaseHelper == null) {
            databaseHelper = new DatabaseHelper(context);
        }
        return databaseHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DatabaseUtilities.CREATE_SAMPLE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public synchronized SQLiteDatabase openDatabase() {
        mOpenCounter++;
        if (mOpenCounter == 1) {
            // Opening database instance
            mDatabase = this.getWritableDatabase();
        }
        return mDatabase;
    }

    public synchronized void closeDatabase() {
        mOpenCounter--;
        if (mOpenCounter == 0) {
            // Closing database instance
            mDatabase.close();

        }
    }

    public void addSampleDataList(ArrayList<SampleDB> sampleDBList) {
        SQLiteDatabase db = openDatabase();

        for (SampleDB sampleDB : sampleDBList) {
            ContentValues values = new ContentValues();
            values.put(DatabaseUtilities.SAMPLE_ID, sampleDB.getId());
            values.put(DatabaseUtilities.SAMPLE_TITLE, sampleDB.getTitle());

            db.insert(DatabaseUtilities.TABLE_SAMPLE, null, values);
        }
        closeDatabase();
    }


    public void addSampleData(SampleDB sampleDB) {
        SQLiteDatabase db = openDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseUtilities.SAMPLE_ID, sampleDB.getId());
        values.put(DatabaseUtilities.SAMPLE_TITLE, sampleDB.getTitle());

        db.insert(DatabaseUtilities.TABLE_SAMPLE, null, values);
        closeDatabase();
    }


    public int updateSampleData(SampleDB sampleDB) {
        int count = 0;
        SQLiteDatabase db = openDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseUtilities.SAMPLE_TITLE, sampleDB.getTitle());

        count += db.update(DatabaseUtilities.TABLE_SAMPLE, values, DatabaseUtilities.SAMPLE_ID + " = ?", new String[]{sampleDB.getId()});

        closeDatabase();
        return count;
    }

    public int updateSampleData(ArrayList<SampleDB> sampleDBList) {
        int count = 0;
        SQLiteDatabase db = openDatabase();

        for (SampleDB sampleDB : sampleDBList){
            ContentValues values = new ContentValues();
            values.put(DatabaseUtilities.SAMPLE_TITLE, sampleDB.getTitle());

            count += db.update(DatabaseUtilities.TABLE_SAMPLE, values, DatabaseUtilities.SAMPLE_ID + " = ?", new String[]{sampleDB.getId()});
        }

        closeDatabase();
        return count;
    }


    public int getSampleDataCount() {
        int count;
        SQLiteDatabase db = openDatabase();
        count = (int) DatabaseUtils.queryNumEntries(db, DatabaseUtilities.TABLE_SAMPLE);
        closeDatabase();
        return count;
    }


    public ArrayList<SampleDB> getAllSampleData() {
        SQLiteDatabase db = openDatabase();

        ArrayList<SampleDB> sampleDBList = new ArrayList<>();

        Cursor cursor = db.rawQuery(DatabaseUtilities.SELECT_ALL_SAMPLE, null);

        while (cursor.moveToNext()) {
            try {
                SampleDB sampleDB = new SampleDB();

                sampleDB.setId(cursor.getString(cursor.getColumnIndex(DatabaseUtilities.SAMPLE_ID)));
                sampleDB.setTitle(cursor.getString(cursor.getColumnIndex(DatabaseUtilities.SAMPLE_TITLE)));

            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

        try {
            cursor.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }

        closeDatabase();
        return sampleDBList;
    }

    public SampleDB getAllSample(String id) {
        SQLiteDatabase db = openDatabase();

        SampleDB sampleDB=null;

        Cursor cursor = db.rawQuery(DatabaseUtilities.SELECT_SAMPLE_FROM_ID, new String[]{id});

        while (cursor.moveToNext()) {
            try {
                sampleDB = new SampleDB();

                sampleDB.setId(cursor.getString(cursor.getColumnIndex(DatabaseUtilities.SAMPLE_ID)));
                sampleDB.setTitle(cursor.getString(cursor.getColumnIndex(DatabaseUtilities.SAMPLE_TITLE)));

            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

        try {
            cursor.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }

        closeDatabase();
        return sampleDB;
    }

}