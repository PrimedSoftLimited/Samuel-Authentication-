package com.example.teachableauth;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.teachableauth.data.DatabaseHelper;
import com.example.teachableauth.data.TeachableProvider;

public class LoginActivity extends AppCompatActivity {

    public android.widget.EditText email, password;
    public Button loginbtn;
    DatabaseHelper myDbHelper = new DatabaseHelper(this);

    public static final String LOG_TAG = TeachableProvider.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        onLogin();

    }

    public void onLogin(){

        SQLiteDatabase db = myDbHelper.getReadableDatabase();

        try {

            email = findViewById(R.id.email);

            password = findViewById(R.id.password);

            loginbtn = findViewById(R.id.login);

            loginbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String Email = email.getText().toString().trim();
                    String Password = password.getText().toString().trim();

                    String pass = myDbHelper.searchPass(Email);

                    if (Password.equals(pass)){
                        Toast.makeText(LoginActivity.this, "Successfully logged in", Toast.LENGTH_SHORT).show();

                    } else{
                        Toast.makeText(LoginActivity.this, "Email and Password don't match", Toast.LENGTH_SHORT).show();
                    }
//                    String[] mProjection = {TeachableContract.TeachableEntry.COLUMN_EMAIL, TeachableContract.TeachableEntry.COLUMN_PASSWORD};
//                    String mSelection = TeachableContract.TeachableEntry.COLUMN_EMAIL + "=?" + " and " + TeachableContract.TeachableEntry.COLUMN_PASSWORD +  "=?";
//                    String[] mSelectionArgs = { "Email", "Password" };
//                    Cursor restrict = getContentResolver().query(TeachableContract.TeachableEntry.CONTENT_URI, mProjection, mSelection, mSelectionArgs, null);
//
//                    if (restrict != -1) {
//                        Toast.makeText(LoginActivity.this, "Successfully logged in", Toast.LENGTH_SHORT).show();
//
//                    } else {
//                        Toast.makeText(LoginActivity.this, "Login Error", Toast.LENGTH_SHORT).show();
//                    }
                }
            });


//            return getContentResolver().query(TeachableContract.TeachableEntry.CONTENT_URI, mProjection, null, null);

        } catch (Exception e) {
            Log.e(LOG_TAG, "Failed to load data");
            e.printStackTrace();
        }

//        String[] projection = {TeachableContract.TeachableEntry.UID, TeachableContract.TeachableEntry.COLUMN_EMAIL, TeachableContract.TeachableEntry.COLUMN_PASSWORD};
//
//        String selection = TeachableContract.TeachableEntry.COLUMN_EMAIL + "m?" + " and " + TeachableContract.TeachableEntry.COLUMN_PASSWORD + "m?";
//        Cursor cursor = db.query(TeachableContract.TeachableEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);
//
//        email = findViewById(R.id.email);
//        password = findViewById(R.id.password);
//        loginbtn = findViewById(R.id.login);
//
//        loginbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String Email = email.getText().toString().trim();
//                String Password = password.getText().toString().trim();
//                boolean restrict = database.checkUser(Email, Password);
//
//                if (restrict == true){
//                    Toast.makeText(LoginActivity.this, "Successfully logged in", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(LoginActivity.this, "Login Error", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }


}
