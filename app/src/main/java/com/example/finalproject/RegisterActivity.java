package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
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
        mButton = (Button)findViewById(R.id.button1);

        mButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                mEditUser   = (EditText)findViewById(R.id.editText1);
                mEditEmail = (EditText)findViewById(R.id.editText2);
                mEditMajor = (EditText)findViewById(R.id.editText3);
                mEditDOB = (EditText)findViewById(R.id.editText4);
                mText = (TextView)findViewById(R.id.textView1);
                mText.setText("Welcome "+mEditUser.getText().toString()+"!");
            }
        });
    }
}
