package com.example.qthjen.mynote.Activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.eightbitlab.supportrenderscriptblur.SupportRenderScriptBlur;
import com.example.qthjen.mynote.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import eightbitlab.com.blurview.BlurView;

public class Weather extends AppCompatActivity {

    Spinner spinner;
    ViewGroup layout_weather;
    BlurView blur_weather;
    TextView tv_country, tv_status, tv_temperature, tv_humidity;
    ImageView iv_status, iv_next;

    String city;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        FindViewWeather();
        CreateSpinner();
        ReadData();

    }

    private void CreateSpinner() {
        String array[] = new String[]{"Ha Noi", "Phu Ly", "Tp Ho Chi Minh", "Hai Phong", "Da Nang"
                , "Paris", "Washington", "London", "Tokyo", "Add"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, array);
        spinner.setAdapter(adapter);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice); // tạo radio button cho spinner

    }

    private void SetupBlurView() {

        Drawable windowBackground = getWindow().getDecorView().getBackground();

        blur_weather.setupWith(layout_weather)
                .windowBackground(windowBackground)
                .blurAlgorithm(new SupportRenderScriptBlur(this))
                .blurRadius(20);

    }

    private void ReadData() {

        city = spinner.getSelectedItem().toString();

        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&units=metric&appid=4e32d6027c86aaa2574c9c329ee801d9";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray jsonArrayRequest = jsonObject.getJSONArray("weather");
                    JSONObject jsonObjectWeather = jsonArrayRequest.getJSONObject(0);
                    String status = jsonObjectWeather.getString("main");
                    String icon = jsonObjectWeather.getString("icon");

                    Picasso.with(Weather.this).load("http://openweathermap.org/img/w/" + icon + ".png").into(iv_status);
                    tv_status.setText("Status: " + status);

                    JSONObject jsonObjectMain = jsonObject.getJSONObject("main");
                    String temperature = jsonObjectMain.getString("temp");
                    String humidity = jsonObjectMain.getString("humidity");

                    Double temp = Double.valueOf(temperature);
                    String Temperature = String.valueOf(temp.intValue());

                    tv_temperature.setText("Temperature: " + Temperature + "°C"); // dùng character map của windows để viết °(alt+0176)
                    tv_humidity.setText("Humidity: " + humidity + "%");

                    JSONObject jsonObjectSys = jsonObject.getJSONObject("sys");
                    String country = jsonObjectSys.getString("country");
                    tv_country.setText("Country: " + country);

                } catch (JSONException e) {
                    Toast.makeText(Weather.this, "Error: " + e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error: " + error.toString(), Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(Weather.this);
        requestQueue.add(stringRequest);

    }

    private void FindViewWeather() {

        spinner = (Spinner) findViewById(R.id.spinner);
        layout_weather = (ViewGroup) findViewById(R.id.layout_weather);
        blur_weather = (BlurView) findViewById(R.id.blur_weather);
        tv_country = (TextView) findViewById(R.id.tv_country);
        tv_status = (TextView) findViewById(R.id.tv_status);
        tv_temperature = (TextView) findViewById(R.id.tv_temperature);
        tv_humidity = (TextView) findViewById(R.id.tv_humidity);
        iv_next = (ImageView) findViewById(R.id.iv_next);
        iv_status = (ImageView) findViewById(R.id.iv_status);

        iv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

}
