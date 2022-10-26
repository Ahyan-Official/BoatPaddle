package com.funpaddler.app.az;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {


    Button register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register = findViewById(R.id.register);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegisterActivity.this, CongratsActivity.class);
                startActivity(i);
                finish();
            }
        });


        //sendWorkPostRequest();

        //sendWorkPostRequestSubmit();

        sendWorkPostRequestSubmitData();
    }
    private void sendWorkPostRequest() {

        try {
            String URL = "https://app.funpaddler.com/api/contactpost/";
            JSONObject jsonBody = new JSONObject();

            jsonBody.put("tel", "+923450853");
            jsonBody.put("name", "Ahyan45");
            jsonBody.put("email", "Ahyan@gmail.com");


            JsonObjectRequest jsonOblect = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    Toast.makeText(getApplicationContext(), "Response:  " + response.toString(), Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {


                    Toast.makeText(getApplicationContext(), "Response:  " + error.toString(), Toast.LENGTH_SHORT).show();

                    // onBackPressed();

                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    final Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type","application/x-www-form-urlencoded");
                   // headers.put("Content-Type", "application/json");
                   // headers.put("Authorization", "Basic " + "c2FnYXJAa2FydHBheS5jb206cnMwM2UxQUp5RnQzNkQ5NDBxbjNmUDgzNVE3STAyNzI=");//put your token here
                    return headers;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(jsonOblect);
            //VolleySingleton.getmInstance()..getInstance().addToRequestQueue(jsonOblect);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_LONG).show();

    }


    private void sendWorkPostRequestSubmit() {

        try {
            String URL = "https://app.funpaddler.com/api/playpost/";
            JSONObject jsonBody = new JSONObject();

            jsonBody.put("ps", "ht435");
            jsonBody.put("code", "435534");
            jsonBody.put("pc", "4");
            jsonBody.put("rm", "5464564");


            JsonObjectRequest jsonOblect = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    Toast.makeText(getApplicationContext(), "Response:  " + response.toString(), Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    onBackPressed();

                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    final Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json");
                    // headers.put("Authorization", "Basic " + "c2FnYXJAa2FydHBheS5jb206cnMwM2UxQUp5RnQzNkQ5NDBxbjNmUDgzNVE3STAyNzI=");//put your token here
                    return headers;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(jsonOblect);
            //VolleySingleton.getmInstance()..getInstance().addToRequestQueue(jsonOblect);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_LONG).show();

    }


    //this needs to test
    private void sendWorkPostRequestSubmitData() {

        try {
            String URL = "https://app.funpaddler.com/api/boardmovepost/";

            JSONObject jsonObject = new JSONObject();

//            jsonBody.put("email", "bk90");
//            jsonBody.put("tel", "kjmi");
//            jsonBody.put("name", "2");
//            jsonBody.put("hardwareID", "uiioouioi");
//            jsonBody.put("timestamp", "uiioouioi");
//
//            jsonBody.put("gryoX", "324");
//            jsonBody.put("gryoY", "234");
//            jsonBody.put("gryoZ", "56");
//
//            jsonBody.put("accX", "24");
//            jsonBody.put("accY", "657");
//            jsonBody.put("accZ", "35");
//
//            jsonBody.put("gps", "67");
//            jsonBody.put("lon", "234");
//            jsonBody.put("lat", "56");

            JSONObject data1 = new JSONObject();
            try {
                data1.put("email", "bk90");
                data1.put("tel", "kjmi");
                data1.put("name", "2");
                data1.put("hardwareID", "uiioouioi");
                data1.put("timestamp", "uiioouioi");

                data1.put("gryoX", "324");
                data1.put("gryoY", "234");
                data1.put("gryoZ", "56");

                data1.put("accX", "24");
                data1.put("accY", "657");
                data1.put("accZ", "35");

                data1.put("gps", "67");
                data1.put("lon", "234");
                data1.put("lat", "56");
                data1.put("comments", "56");

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            JSONObject data2 = new JSONObject();
            try {
                data2.put("email", "bk90");
                data2.put("tel", "kjmi");
                data2.put("name", "2");
                data2.put("hardwareID", "uiioouioi");
                data2.put("timestamp", "uiioouioi");

                data2.put("gryoX", "324");
                data2.put("gryoY", "234");
                data2.put("gryoZ", "56");

                data2.put("accX", "24");
                data2.put("accY", "657");
                data2.put("accZ", "35");

                data2.put("gps", "67");
                data2.put("lon", "234");
                data2.put("lat", "56");
                data2.put("comments", "56");

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            JSONObject data2Object = new JSONObject();
            data2Object.put("data",data2);

            JSONObject data1Object = new JSONObject();
            data1Object.put("data",data1);

            JSONArray jsonArray = new JSONArray();

            jsonArray.put(data2Object);
            jsonArray.put(data1Object);

            jsonObject.put("datas",jsonArray);

            Log.e("Datas", "sendWorkPostRequestSubmitData: "+jsonArray.toString() );

            JsonObjectRequest jsonOblect = new JsonObjectRequest(Request.Method.POST, URL, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    Toast.makeText(getApplicationContext(), "Response:  " + response.toString(), Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Log.e("poowq", "onErrorResponse: "+error.getMessage() );
                    Toast.makeText(getApplicationContext(), "Response:  " + error.getMessage(), Toast.LENGTH_SHORT).show();

                    //onBackPressed();

                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    final Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json");
                    // headers.put("Authorization", "Basic " + "c2FnYXJAa2FydHBheS5jb206cnMwM2UxQUp5RnQzNkQ5NDBxbjNmUDgzNVE3STAyNzI=");//put your token here
                    return headers;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(jsonOblect);
            //VolleySingleton.getmInstance()..getInstance().addToRequestQueue(jsonOblect);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_LONG).show();

    }
}