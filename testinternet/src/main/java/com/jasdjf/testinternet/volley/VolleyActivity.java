package com.jasdjf.testinternet.volley;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.jasdjf.testinternet.R;

import org.json.JSONObject;

public class VolleyActivity extends AppCompatActivity {

    private Button mBtnVolleyGetStringRequest;
    private Button mBtnVolleyGetJsonRequest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley);
        initView();
    }

    private void initView() {
        mBtnVolleyGetStringRequest = (Button) findViewById(R.id.btn_volley_get_string_request);
        mBtnVolleyGetJsonRequest = (Button) findViewById(R.id.btn_volley_get_json_request);
        mBtnVolleyGetStringRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectVolleyByStringRequest();
            }
        });
        mBtnVolleyGetJsonRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectVolleyByJsonRequest();
            }
        });
    }

    private void connectVolleyByStringRequest() {
        String url = "http://www.wanandroid.com/project/tree/json";
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("jasdjf", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("jasdjf", error.getMessage());
            }
        });
        queue.add(stringRequest);
    }

    private void connectVolleyByJsonRequest(){
        String url = "http://www.wanandroid.com/project/tree/json";
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                VolleyJsonClassification classification = new Gson().fromJson(response.toString(),VolleyJsonClassification.class);
                if(classification!=null){
                    for(VolleyJsonData data : classification.getData()){
                        Log.d("jasdjf",data.getName());
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("jasdjf","Error!");
            }
        });
        queue.add(jsonObjectRequest);
    }
}
