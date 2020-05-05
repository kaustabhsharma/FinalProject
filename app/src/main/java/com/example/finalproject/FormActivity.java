package com.example.finalproject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FormActivity extends AppCompatActivity {

    private Map<String, String> param;
    private JSONObject form;
    private SharedPreferences savedInfo;
    private SharedPreferences.Editor editor;
    private EditText ask1, ask2, ask3, ask4, ask5, ask6, ask7;
    private TextView field1, field2, field3, field4, field5, field6, field7;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        setUpTable();
        Button submit = (Button) findViewById(R.id.buttonX);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                compile();
            }
        });
        savedInfo = getSharedPreferences("UserData", MODE_PRIVATE);
        editor = savedInfo.edit();
        Intent intent = getIntent();
        String json = intent.getStringExtra("Json");
        try {
            form = new JSONObject(json);
        } catch (Exception e) {
            showAlertDialog("Error,Try Again");
        }
        param = new HashMap<>();
        read(form);
    }

    public void read(JSONObject input) {
        try {
            JSONArray info = form.getJSONArray("Form");
            for (int i = 0; i < info.length(); i++) {
                String field = info.getString(i);
                getField(i).setText(field);
                fill(field, i);
            }
        } catch (Exception e) {
            showAlertDialog("Form too Big");
        }
    }

    public void fill(String input, int a) {
        if (savedInfo.contains(input)) {
            getAsk(a).setHint(savedInfo.getString(input, "Default"));
        } else {
            // create a Edit Text in the Layout to get Data from the user.
            getAsk(a).setHint("");
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

    public void setUpTable() {
        ask1 = (EditText) findViewById(R.id.ask1);
        ask1.setVisibility(View.GONE);
        ask2 = (EditText) findViewById(R.id.ask2);
        ask2.setVisibility(View.GONE);
        ask3 = (EditText) findViewById(R.id.ask3);
        ask3.setVisibility(View.GONE);
        ask4 = (EditText) findViewById(R.id.ask4);
        ask4.setVisibility(View.GONE);
        ask5 = (EditText) findViewById(R.id.ask5);
        ask5.setVisibility(View.GONE);
        ask6 = (EditText) findViewById(R.id.ask6);
        ask6.setVisibility(View.GONE);
        ask7 = (EditText) findViewById(R.id.ask7);
        ask7.setVisibility(View.GONE);

        field1 = (TextView) findViewById(R.id.field1);
        field1.setVisibility(View.GONE);
        field2 = (TextView) findViewById(R.id.field2);
        field2.setVisibility(View.GONE);
        field3 = (TextView) findViewById(R.id.field3);
        field3.setVisibility(View.GONE);
        field4 = (TextView) findViewById(R.id.field4);
        field4.setVisibility(View.GONE);
        field5 = (TextView) findViewById(R.id.field5);
        field5.setVisibility(View.GONE);
        field6 = (TextView) findViewById(R.id.field6);
        field6.setVisibility(View.GONE);
        field7 = (TextView) findViewById(R.id.field7);
        field7.setVisibility(View.GONE);
    }

    public EditText getAsk(int a) {
        switch (a) {
        case 1:
            ask1.setVisibility(View.VISIBLE);
            return ask1;
        case 2:
            ask2.setVisibility(View.VISIBLE);
            return ask2;
        case 3:
            ask3.setVisibility(View.VISIBLE);
            return ask3;
        case 4:
            ask4.setVisibility(View.VISIBLE);
            return ask4;
        case 5:
            ask5.setVisibility(View.VISIBLE);
            return ask5;
        case 6:
            ask6.setVisibility(View.VISIBLE);
            return ask6;
        default:
            ask7.setVisibility(View.VISIBLE);
            return ask7;
        }
    }

    public TextView getField(int a) {
        switch (a) {
        case 1:
            field1.setVisibility(View.VISIBLE);
            return field1;
        case 2:
            field2.setVisibility(View.VISIBLE);
            return field2;
        case 3:
            field3.setVisibility(View.VISIBLE);
            return field3;
        case 4:
            field4.setVisibility(View.VISIBLE);
            return field4;
        case 5:
            field5.setVisibility(View.VISIBLE);
            return field5;
        case 6:
            field6.setVisibility(View.VISIBLE);
            return field6;
        default:
            field7.setVisibility(View.VISIBLE);
            return field7;
        }
    }

    public void compile() {
        try {
            int size = form.getJSONArray("Form").length();
            for (int i = 0; i < size; i++) {
                if (getAsk(i).getText().toString().isEmpty()) {
                    param.put(getField(i).getText().toString(), getAsk(i).getHint().toString());
                } else {
                    param.put(getField(i).getText().toString(), getAsk(i).getText().toString());
                    editor.putString(getField(i).getText().toString(), getAsk(i).getText().toString());
                }
            }
            editor.apply();
        } catch (Exception e) {
            showAlertDialog("Oops!");
        }
        addItemToSheet();
    }
    private void   addItemToSheet() {

        final ProgressDialog loading = ProgressDialog.show(this,"Adding Item","Please wait");


        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbwODUB-mSCxS32ILlzfDrWtdhie1Ganf-8KPrryXLi8tivJ6u2t/exec",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        Toast.makeText(FormActivity.this, response,Toast.LENGTH_LONG).show();
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                param.put("action", "addItem");
                return param;
            }
        };

        int socketTimeOut = 50000;// u can change this .. here it is 50 seconds

        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);

        RequestQueue queue = Volley.newRequestQueue(this);

        queue.add(stringRequest);

    }

}
