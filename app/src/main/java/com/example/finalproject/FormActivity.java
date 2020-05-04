package com.example.finalproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

public class FormActivity extends AppCompatActivity {

    private String response = "";
    private JSONObject form;
    private SharedPreferences savedInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        savedInfo = getSharedPreferences("UserData", MODE_PRIVATE);
        SharedPreferences.Editor editor = savedInfo.edit();
        Intent intent = getIntent();
        String json = intent.getStringExtra("Json");
        try {
            form = new JSONObject(json);
        } catch (Exception e) {
            showAlertDialog("Error,Try Again");
        }
        read(form);
    }

    public void read(JSONObject input) {
        try {
            JSONArray info = form.getJSONArray("form");
            for (int i = 0; i < info.length(); i++) {
                String field = info.getString(i);
                fill(field);
            }
        } catch (Exception e) {
            showAlertDialog("Error, Try Again");
        }
    }

    public void fill(String input) {
        if (savedInfo.contains(input)) {
            response = response + savedInfo.getString(input, "Default") + ", ";
        } else {
            // create a Edit Text in the Layout to get Data from the user.
        }
    }

    private void showAlertDialog(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.setNegativeButton(
                "CANCEL",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
