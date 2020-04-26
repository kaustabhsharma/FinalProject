package com.example.finalproject;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class FormActivity extends AppCompatActivity {

    private String url;
    public FormActivity(String setURL) {
        url = setURL;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        // I started working with FORMs but there is no direct API to access these forms without doing a lot of Work.
        // Instead we can work with Google Spreadsheets since they Have an API and Can serve the same purpose.
        // Search up a bit if you want but I think spread sheets will be much easier than Forms.
    }
}
