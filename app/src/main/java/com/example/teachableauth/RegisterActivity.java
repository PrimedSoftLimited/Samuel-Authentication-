package com.example.teachableauth;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.teachableauth.data.DatabaseHelper;
import com.example.teachableauth.data.TeachableContract;

public class RegisterActivity extends AppCompatActivity {
    EditText firstName, lastName, username, email, password, cnfPassword;
    //Integer.parseInt("1");
    private SQLiteDatabase db;

    private DatabaseHelper myDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button btn = (Button) findViewById(R.id.btn_register);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAddtask(v);
            }
        });
    }

    public void onClickAddtask(View view){

        firstName = (EditText) findViewById(R.id.firstname);
        lastName = (EditText) findViewById(R.id.lastname);
        username = (EditText) findViewById(R.id.username);
        email = (EditText) findViewById(R.id.email_edit);
        password = (EditText) findViewById(R.id.password_edit);
        cnfPassword = (EditText) findViewById(R.id.confirm_edit);

        String firstNameString = firstName.getText().toString().trim();
        String lastNameString = lastName.getText().toString().trim();
        String usernameString = username.getText().toString().trim();
        String emailString = email.getText().toString().trim();
        String passwordString = password.getText().toString().trim();
        String cnfPasswordString = cnfPassword.getText().toString().trim();

//        User user = new User();
//        user.setFirstName(firstNameString);
//        user.setLastName(lastNameString);
//        user.setUsername(usernameString);
//        user.setEmail(emailString);
//        user.setPassword(passwordString);

//        String query = "select * from TutorTable";
//        Cursor cursor = db.rawQuery(query, null);
//        int count = cursor.getCount();

        if (passwordString.equals(cnfPasswordString)){
            ContentValues values = new ContentValues();

//            values.put(TeachableContract.TeachableEntry.UID, count);

            values.put(TeachableContract.TeachableEntry.COLUMN_FIRSTNAME, firstNameString);
            values.put(TeachableContract.TeachableEntry.COLUMN_LASTNAME, lastNameString);
            values.put(TeachableContract.TeachableEntry.COLUMN_USERNAME, usernameString);
            values.put(TeachableContract.TeachableEntry.COLUMN_EMAIL, emailString);
            values.put(TeachableContract.TeachableEntry.COLUMN_PASSWORD, passwordString);

            Uri uri = getContentResolver().insert(TeachableContract.TeachableEntry.CONTENT_URI, values);

            if (uri != null){
                Toast.makeText(RegisterActivity.this, " Registered Successfully", Toast.LENGTH_SHORT).show();
                Intent moveToLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(moveToLogin);
            }

            finish();
        } else {
            Toast.makeText(RegisterActivity.this, " Unmatched Password", Toast.LENGTH_SHORT).show();
        }


    }
}
