package com.example.teachableauth.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class TeachableContract {

    private TeachableContract(){}

    public static final String PATH_TUTOR = "tutorTable";

    public static final class TeachableEntry implements BaseColumns {

        public static final String TABLE_NAME = "TutorTable";   // Table Name

        public static final String UID = BaseColumns._ID;     // Column I (Primary Key)
        public static final String COLUMN_FIRSTNAME = "Firstname";    //Column II
        public static final String COLUMN_LASTNAME = "Lastname"; // Column III
        public static final String COLUMN_USERNAME = "Username"; // Column IV
        public static final String COLUMN_PHONENO = "PhoneNo"; // Column V
        public static final String COLUMN_EMAIL = "Email"; // Column V
        public static final String COLUMN_PASSWORD = "Password"; // Column VI
        public static final String COLUMN_CONFIRMPASS = "Confirm Password"; // Column V


        private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

        public static final String CONTENT_AUTHORITY = "com.example.teachableauth";
        public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_TUTOR);
    }
}
