package com.example.examtestapp;

import android.provider.BaseColumns;

public class DatabaseMaster {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private DatabaseMaster() {}

    /* Inner class that defines the table contents */
    public static class Users implements BaseColumns {
        public static final String TABLE_NAME = "users";
        public static final String COLUMN_1 = "userName";
        public static final String COLUMN_2 = "password";
        public static final String COLUMN_3 = "userType";
    }

    public static class Movies implements BaseColumns {
        public static final String TABLE_NAME = "movies";
        public static final String COLUMN_1 = "movieName";
        public static final String COLUMN_2 = "movieYear";

    }

    public static class Comments implements BaseColumns {
        public static final String TABLE_NAME = "comments";
        public static final String COLUMN_1 = "movieName";
        public static final String COLUMN_2 = "movieRating";
        public static final String COLUMN_3 = "movieComments";
    }
}
