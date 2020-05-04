package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.SharedPreferences.Editor;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {
    Button mButton;
    EditText mEditUser;
    EditText mEditEmail;
    EditText mEditMajor;
    EditText mEditDOB;
    TextView mText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mEditUser   = (EditText)findViewById(R.id.editText1);
        mEditEmail = (EditText)findViewById(R.id.editText2);
        mEditMajor = (EditText)findViewById(R.id.editText3);
        mEditDOB = (EditText)findViewById(R.id.editText4);
        mText = (TextView)findViewById(R.id.textView1);
        mButton = (Button) findViewById(R.id.button1);
        mButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SharedPreferences savedInfo = getSharedPreferences("UserData", 0);
                SharedPreferences.Editor editor = savedInfo.edit();
                editor.putString("Name", mEditUser.getText().toString());
                editor.putString("Email", mEditEmail.getText().toString());
                editor.putString("Major", mEditMajor.getText().toString());
                editor.putString("DOB", mEditDOB.getText().toString());
                editor.putBoolean("firstTime", false);
                editor.apply();
                showAlertDialog("Welcome "+mEditUser.getText().toString()+"!");
            }
        });
    }
    private void showAlertDialog(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setCancelable(true);

        builder.setPositiveButton(
                "SCAN CODE",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
