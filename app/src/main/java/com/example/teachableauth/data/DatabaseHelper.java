package com.example.teachableauth.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "teachDatabase.db";    // Database Name
    private static final int DATABASE_Version = 1;    // Database Version
    private static final String CREATE_TABLE = "CREATE TABLE " + TeachableContract.TeachableEntry.TABLE_NAME + "(" + TeachableContract.TeachableEntry.UID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            TeachableContract.TeachableEntry.COLUMN_FIRSTNAME + " TEXT," + TeachableContract.TeachableEntry.COLUMN_LASTNAME + " TEXT," + TeachableContract.TeachableEntry.COLUMN_USERNAME + " TEXT," +
            TeachableContract.TeachableEntry.COLUMN_EMAIL + " TEXT," + TeachableContract.TeachableEntry.COLUMN_PASSWORD + " TEXT)";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TeachableContract.TeachableEntry.TABLE_NAME;
    private Context context;

    SQLiteDatabase db;

    DatabaseHelper myDbHelper;

    public DatabaseHelper (Context context){
        super(context, DATABASE_NAME, null, DATABASE_Version);
        this.context = context;
    }



    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        this.db = db;
    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     *
     * <p>
     * The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     * </p><p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public boolean checkUser(String Email, String Password) {
        String[] projection = {TeachableContract.TeachableEntry.UID, TeachableContract.TeachableEntry.COLUMN_EMAIL, TeachableContract.TeachableEntry.COLUMN_PASSWORD};

        db = myDbHelper.getReadableDatabase();

        String selection = TeachableContract.TeachableEntry.COLUMN_EMAIL + "m?" + " and " + TeachableContract.TeachableEntry.COLUMN_PASSWORD + "m?";
        String[] selectionArgs = {Email, Password};
        Cursor cursor = db.query(TeachableContract.TeachableEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);

        int count = cursor.getCount();

        cursor.close();
        db.close();

        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }

    public String searchPass(String email){
        db = this.getReadableDatabase();
        String query = "Select Email, Password from " + TeachableContract.TeachableEntry.TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        String mail, password;
        password = "Not found";

        if(cursor.moveToFirst()){
            do{
                mail = cursor.getString(0);


                if(mail.equals(email)){
                    password = cursor.getString(1);
                    break;
                }
            }
            while (cursor.moveToNext());
        }
        return password;
    }
}
