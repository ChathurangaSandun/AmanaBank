package com.clivekumara.amanabank;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by chathuranga on 1/21/2016.
 */
public class DBhelper  extends SQLiteOpenHelper{

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "amanabank.db";

    // marks table name
    public static final String TABLE_BRANCH= "branch";

    // marks Table Columns names
    public static final String KEY_BRANCH_CODE = "branchcode";
    public static final String KEY_BRANCH_NAME = "branchname";
    public static final String KEY_LATITUDE= "latitude";
    public static final String KEY_LONGITUDE= "longitude";
    public static final String KEY_ADDRESS= "address";
    public static final String KEY_TEL= "tel";
    public static final String KEY_FAX= "fax";

    //crating table quary
    private static final String CREATE_BRANCH_TABLE =   "CREATE TABLE " +TABLE_BRANCH+" ("+
            KEY_BRANCH_CODE+ " INTEGER PRIMARY KEY, " +
            KEY_BRANCH_NAME+" TEXT, "+
            KEY_LATITUDE+" REAL, "+
            KEY_LONGITUDE+" REAL, "+
            KEY_ADDRESS+" TEXT, "+
            KEY_TEL+" INTEGER, "+
            KEY_FAX+" INTEGER "+   ")";

    //all columns
    public static final String[] ALL_COLUMNS = {KEY_BRANCH_CODE,KEY_BRANCH_NAME,KEY_LATITUDE,KEY_LATITUDE,KEY_LONGITUDE,KEY_ADDRESS,KEY_TEL,KEY_FAX};

    public DBhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BRANCH_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BRANCH);
        onCreate(db);
    }


    // _______________________ for branch table___________________________________________________//



    public void addBranch(Branch branch) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DELETE FROM " + TABLE_BRANCH);


        ContentValues values = new ContentValues();
        values.put(KEY_BRANCH_CODE,branch.getBranchCode());
        values.put(KEY_BRANCH_NAME,branch.getBranchName());
        values.put(KEY_LATITUDE,branch.getLatitude());
        values.put(KEY_LONGITUDE,branch.getLongitude());
        values.put(KEY_ADDRESS,branch.getAddress());
        values.put(KEY_TEL,branch.getTel());
        values.put(KEY_FAX,branch.getFax());


        // Inserting Row
        db.insert(TABLE_BRANCH, null, values);
        db.close(); // Closing database connection
    }


}
