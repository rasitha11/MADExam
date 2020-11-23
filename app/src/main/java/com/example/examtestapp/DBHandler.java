package com.example.examtestapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "MovieDB.db";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES_Users);
        db.execSQL(SQL_CREATE_ENTRIES_Movies);
        db.execSQL(SQL_CREATE_ENTRIES_Comments);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES_Users);
        db.execSQL(SQL_DELETE_ENTRIES_Movies);
        db.execSQL(SQL_DELETE_ENTRIES_Comments);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    //SQLite Strings definition
    private static final String SQL_CREATE_ENTRIES_Users =
            "CREATE TABLE " + DatabaseMaster.Users.TABLE_NAME + " (" +
                    DatabaseMaster.Users._ID + " INTEGER PRIMARY KEY," +
                    DatabaseMaster.Users.COLUMN_1 + " TEXT," +
                    DatabaseMaster.Users.COLUMN_2 + " TEXT," +
                    DatabaseMaster.Users.COLUMN_3 + " TEXT)";

    private static final String SQL_DELETE_ENTRIES_Users =
            "DROP TABLE IF EXISTS " + DatabaseMaster.Users.TABLE_NAME;

    private static final String SQL_CREATE_ENTRIES_Movies =
            "CREATE TABLE " + DatabaseMaster.Movies.TABLE_NAME + " (" +
                    DatabaseMaster.Movies._ID + " INTEGER PRIMARY KEY," +
                    DatabaseMaster.Movies.COLUMN_1 + " TEXT," +
                    DatabaseMaster.Movies.COLUMN_2 + " INT)";

    private static final String SQL_DELETE_ENTRIES_Movies =
            "DROP TABLE IF EXISTS " + DatabaseMaster.Movies.TABLE_NAME;

    private static final String SQL_CREATE_ENTRIES_Comments =
            "CREATE TABLE " + DatabaseMaster.Comments.TABLE_NAME + " (" +
                    DatabaseMaster.Comments._ID + " INTEGER PRIMARY KEY," +
                    DatabaseMaster.Comments.COLUMN_1 + " TEXT," +
                    DatabaseMaster.Comments.COLUMN_2 + " INT," +
                    DatabaseMaster.Comments.COLUMN_3 + " TEXT)";

    private static final String SQL_DELETE_ENTRIES_Comments =
            "DROP TABLE IF EXISTS " + DatabaseMaster.Comments.TABLE_NAME;


    public long registerUser(String userName, String password) {
        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DatabaseMaster.Users.COLUMN_1, userName);
        values.put(DatabaseMaster.Users.COLUMN_2, password);
        if(userName.equals("admin")){
            values.put(DatabaseMaster.Users.COLUMN_3, "admin");
        }
        else {
            values.put(DatabaseMaster.Users.COLUMN_3, "user");
        }

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(DatabaseMaster.Users.TABLE_NAME, null, values);
        return newRowId;
    }

    public List loginUser(String userName, String password) {
        SQLiteDatabase db = getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                DatabaseMaster.Users.COLUMN_1,
                DatabaseMaster.Users.COLUMN_2,
                DatabaseMaster.Users.COLUMN_3

        };

        // Filter results WHERE "title" = 'My Title'
        String selection = DatabaseMaster.Users.COLUMN_1 + " = ? AND " + DatabaseMaster.Users.COLUMN_2 + " = ?";
        String[] selectionArgs = {userName, password};

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                DatabaseMaster.Users.COLUMN_1 + " ASC";

        Cursor cursor = db.query(
                DatabaseMaster.Users.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        List validUser = new ArrayList<>();
        while (cursor.moveToNext()) {
            String user = null;
                   user = cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseMaster.Users.COLUMN_3));
                    System.out.println(user);
            validUser.add(user);
        }
        cursor.close();

        return validUser;

//        if (validUser.isEmpty())
//            return false;
//        else
//            return true;

    }

    public boolean addMovie(String movieName,int year){
        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DatabaseMaster.Movies.COLUMN_1, movieName);
        values.put(DatabaseMaster.Movies.COLUMN_2, year);

        long result = -99;
        // Insert the new row, returning the primary key value of the new row
        result = db.insert(DatabaseMaster.Movies.TABLE_NAME, null, values);

        if(result != -99)
            return true;
        else
            return false;
    }

    public List viewMovies() {
        SQLiteDatabase db = getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                DatabaseMaster.Movies.COLUMN_1
        };

        // Filter results WHERE "title" = 'My Title'
         String selection = DatabaseMaster.Movies.COLUMN_1 + " = ? ";
         String[] selectionArgs = { "userName" };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                DatabaseMaster.Movies.COLUMN_1 + " ASC";

        Cursor cursor = db.query(
                DatabaseMaster.Movies.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        List movieNames = new ArrayList<>();
        while (cursor.moveToNext()) {
            String movie = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseMaster.Movies.COLUMN_1));
            movieNames.add(movie);
        }
        cursor.close();

        return movieNames;
    }

    public long insertComments(String movieName, int rating, String comment){

        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DatabaseMaster.Comments.COLUMN_1, movieName);
        values.put(DatabaseMaster.Comments.COLUMN_2, rating);
        values.put(DatabaseMaster.Comments.COLUMN_3, comment);


       Long insert = db.insert(DatabaseMaster.Comments.TABLE_NAME, null, values);
        return insert;
    }
    public List viewComments(String movieName){
        SQLiteDatabase db = getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                DatabaseMaster.Comments.COLUMN_3

        };

        // Filter results WHERE "title" = 'My Title'
        String selection = DatabaseMaster.Comments.COLUMN_1 + " = ? ";
        String[] selectionArgs = { movieName };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                BaseColumns._ID + " ASC";

        Cursor cursor = db.query(
                DatabaseMaster.Comments.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        List commentList = new ArrayList<>();
        while (cursor.moveToNext()) {
            String comment = cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseMaster.Comments.COLUMN_3));
            commentList.add(comment);
        }
        cursor.close();
        if(commentList.isEmpty()){
            commentList.add("Example comment");
        }


        return commentList;
    }

    public List viewRatings(String movieName){
        SQLiteDatabase db = getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                DatabaseMaster.Comments.COLUMN_2

        };

        // Filter results WHERE "title" = 'My Title'
        String selection = DatabaseMaster.Comments.COLUMN_1 + " = ? ";
        String[] selectionArgs = { movieName };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                BaseColumns._ID + " ASC";

        Cursor cursor = db.query(
                DatabaseMaster.Comments.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        List ratingList = new ArrayList<Integer>();
        while (cursor.moveToNext()) {

            int rating = cursor.getInt(
                    cursor.getColumnIndexOrThrow(DatabaseMaster.Comments.COLUMN_2));
            ratingList.add(rating);
        }
        cursor.close();

        return ratingList;
    }

}
