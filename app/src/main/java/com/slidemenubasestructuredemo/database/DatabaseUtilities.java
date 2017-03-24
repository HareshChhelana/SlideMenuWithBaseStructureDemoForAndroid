package com.slidemenubasestructuredemo.database;

public class DatabaseUtilities {

    public static final String DB_NAME = "SAMPLE_DB";
    public static final int DB_VERSION = 1;

    public static final String TABLE_SAMPLE = "Sample";

    public static final String SAMPLE_ID = "sample_id";
    public static final String SAMPLE_TITLE = "sample_title";


    public static String CREATE_SAMPLE_TABLE = "CREATE TABLE " + TABLE_SAMPLE + "("
            + SAMPLE_ID + " TEXT , "
            + SAMPLE_TITLE + " TEXT "+
            ")";


    public static String DROP_SAMPLE_TABLE = "DROP TABLE IF EXISTS " + TABLE_SAMPLE;

    public static String SELECT_ALL_SAMPLE = "SELECT * FROM " + TABLE_SAMPLE;
    public static String SELECT_SAMPLE_FROM_ID = "SELECT * FROM " + TABLE_SAMPLE+" WHERE "+SAMPLE_ID+"=?";

}
