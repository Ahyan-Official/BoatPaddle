package com.funpaddler.app.az;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.widget.Autocomplete;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, SensorEventListener, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    TextView exit,paddleNow,stopPaddle,submitData;
    ImageView play,pause,power;
    CardView menu_btn;
    RelativeLayout menu;
    private GoogleApiClient mGoogleApiClient;
    private Location mylocation;
    private final static int REQUEST_CHECK_SETTINGS_GPS = 0x1;
    private final static int REQUEST_ID_MULTIPLE_PERMISSIONS = 0x2;
    private static final int REQUEST_CODE_AUTOCOMPLETE = 6;
    TextView tvSearch;
    Context mContext;

    Location loc;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private Sensor mGyroscope;

    TextView countrytv,coordinate,speed;
    private RequestQueue requestQueue;
    private final String url = "https://api.openweathermap.org/data/2.5/weather?q=london&appid=63bd8fd245636c20cf3acc0240a4b520";
    DecimalFormat df = new DecimalFormat("#.##");
    private String appid;
    TextView weatherTemp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appid = getString(R.string.weatherKey);

        setUpGClient();


        String apiKey = getString(R.string.API_Map);
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), apiKey);
        }
        mContext = this;

        exit = findViewById(R.id.exit);
        paddleNow = findViewById(R.id.paddleNow);
        stopPaddle = findViewById(R.id.stopPaddle);
        submitData = findViewById(R.id.submitData);
        tvSearch = findViewById(R.id.coordinate);
        weatherTemp = findViewById(R.id.weatherTemp);

        countrytv = findViewById(R.id.country);

        speed = findViewById(R.id.speed);



        menu_btn = findViewById(R.id.menu_btn);
        menu = findViewById(R.id.menu);

        play = findViewById(R.id.play);
        pause = findViewById(R.id.pause);
        power = findViewById(R.id.power);


        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mGyroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        submitData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, SubmitDataActivity.class);
                startActivity(i);
            }
        });


        menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menu.setVisibility(View.VISIBLE);
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menu.setVisibility(View.GONE);
            }
        });
        stopPaddle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pause.setVisibility(View.GONE);
                power.setVisibility(View.GONE);

                play.setVisibility(View.VISIBLE);

            }
        });

        paddleNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pause.setVisibility(View.VISIBLE);
                power.setVisibility(View.GONE);

                play.setVisibility(View.GONE);



            }
        });


        power.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pause.setVisibility(View.VISIBLE);
                power.setVisibility(View.GONE);

                play.setVisibility(View.GONE);



            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pause.setVisibility(View.GONE);
                power.setVisibility(View.GONE);

                play.setVisibility(View.VISIBLE);



            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pause.setVisibility(View.VISIBLE);
                power.setVisibility(View.GONE);

                play.setVisibility(View.GONE);



            }
        });


        //======================weather=========================

    }

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mGyroscope, SensorManager.SENSOR_DELAY_NORMAL);
    }
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

//    public void onSensorChanged(SensorEvent sensorEvent) {
//        String sensorName = sensorEvent.sensor.getName();
//        Log.d("asd",sensorName + ": X: " + sensorEvent.values[0] + "; Y: " + sensorEvent.values[1] + "; Z: " + sensorEvent.values[2] + ";");
//
//    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        String sensorName = sensorEvent.sensor.getName();
        Log.e("asd",sensorName + ": X: " + sensorEvent.values[0] + "; Y: " + sensorEvent.values[1] + "; Z: " + sensorEvent.values[2] + ";");

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
    private synchronized void setUpGClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(MainActivity.this, 0, this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    private void getMyLocation() {
        if (mGoogleApiClient != null) {
            if (mGoogleApiClient.isConnected()) {
                int permissionLocation = ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION);
                if (permissionLocation == PackageManager.PERMISSION_GRANTED) {

                    mylocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                    LocationRequest locationRequest = new LocationRequest();
                    locationRequest.setInterval(3000);
                    locationRequest.setFastestInterval(3000);
                    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                    LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                            .addLocationRequest(locationRequest);
                    builder.setAlwaysShow(true);
                    LocationServices.FusedLocationApi
                            .requestLocationUpdates(mGoogleApiClient, locationRequest, this);
                    PendingResult<LocationSettingsResult> result =
                            LocationServices.SettingsApi
                                    .checkLocationSettings(mGoogleApiClient, builder.build());
                    result.setResultCallback(new ResultCallback<LocationSettingsResult>() {

                        @Override
                        public void onResult(LocationSettingsResult result) {
                            final Status status = result.getStatus();
                            switch (status.getStatusCode()) {
                                case LocationSettingsStatusCodes.SUCCESS:
                                    // All location settings are satisfied.
                                    // You can initialize location requests here.


                                    int permissionLocation = ContextCompat
                                            .checkSelfPermission(MainActivity.this,
                                                    Manifest.permission.ACCESS_FINE_LOCATION);
                                    if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
                                        mylocation = LocationServices.FusedLocationApi
                                                .getLastLocation(mGoogleApiClient);
                                    }

                                    Log.e("opo", "onResult: "+ mylocation.getLatitude());
                                    Geocoder geocoder;
                                    List<Address> addresses;
                                    geocoder = new Geocoder(MainActivity.this, Locale.getDefault());

                                    try {

                                        addresses = geocoder.getFromLocation(mylocation.getLatitude(), mylocation.getLatitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                                        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                                        String city = addresses.get(0).getLocality();
                                        String state = addresses.get(0).getAdminArea();
                                        String country = addresses.get(0).getCountryName();
                                        String postalCode = addresses.get(0).getPostalCode();
                                        String knownName = addresses.get(0).getFeatureName();

                                        Log.e("address ", "onActivityResult: "+address+" "+state+" "+city+" "+country );
                                        mylocation.getSpeed();
                                        tvSearch.setText(mylocation.getLatitude() +" "+mylocation.getLatitude());
                                        speed.setText(mylocation.getSpeed()+" km/h");

                                        countrytv.setText(country);

                                        getWeatherDetails(city,country);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }


                                    break;
                                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                    // Location settings are not satisfied.
                                    // But  could be fixed by showing the user a dialog.
                                    try {
                                        // Show the dialog by calling startResolutionForResult(),
                                        // and check the result in onActivityResult().
                                        // Ask to turn on GPS automatically
                                        status.startResolutionForResult(MainActivity.this,
                                                REQUEST_CHECK_SETTINGS_GPS);
                                    } catch (IntentSender.SendIntentException e) {
                                        // Ignore the error.
                                    }
                                    break;
                                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                    // Location settings are not satisfied.
                                    // However, we have no way
                                    // to fix the
                                    // settings so we won't show the dialog.
                                    // finish();
                                    break;
                            }
                        }
                    });
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS_GPS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        getMyLocation();
                        break;
                    case Activity.RESULT_CANCELED:
                        //finish();
                        break;
                }
                break;
        }


        if (requestCode == REQUEST_CODE_AUTOCOMPLETE) {
            if (resultCode == RESULT_OK) {
                // Get the user's selected place from the Intent.
                //Place place = PlaceAutocomplete.getPlace(mContext, data);
                com.google.android.libraries.places.api.model.Place place = Autocomplete.getPlaceFromIntent(data);

                tvSearch.setText(place.getName().toString());
                // TODO call location based filter


                LatLng latLong;


                latLong = place.getLatLng();

                loc.setLatitude(latLong.latitude);
                loc.setLongitude(latLong.longitude);

                //mLocationText.setText(place.getName() + "");

                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(latLong).zoom(15f).tilt(0).build();

                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }


                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(this, Locale.getDefault());

                try {

                    addresses = geocoder.getFromLocation(latLong.latitude, latLong.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                    String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    String city = addresses.get(0).getLocality();
                    String state = addresses.get(0).getAdminArea();
                    String country = addresses.get(0).getCountryName();
                    String postalCode = addresses.get(0).getPostalCode();
                    String knownName = addresses.get(0).getFeatureName();


                    Log.e("address ", "onActivityResult: "+address+" "+state+" "+city+" "+country );

                    // tvSearch.setText(address+" "+state+" "+city+" "+country);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }


        } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
            Status status = PlaceAutocomplete.getStatus(mContext, data);
        } else if (resultCode == RESULT_CANCELED) {
            // Indicates that the activity closed before a selection was made. For example if
            // the user pressed the back button.
        }
    }

    //===========checking location permission===========
    private void checkPermissions() {
        int permissionLocation = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (permissionLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(this,
                        listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            }
        } else {
            getMyLocation();
        }

    }

    // =====when permission is granted==========
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        int permissionLocation = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionLocation == PackageManager.PERMISSION_GRANTED) {

            getMyLocation();

        }
    }

    public static boolean isLocationEnabled(Context context) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return true;
        } else {

            return false;
        }
    }



    @Override
    protected void onStart() {
        super.onStart();
        try {
            mGoogleApiClient.connect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {

        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        checkPermissions();
    }


    // when location changes
    @Override
    public void onLocationChanged(Location location) {
        try {
            if (location != null)

                Log.e("hittt", "onLocationChanged: "+location.getLatitude() );
                //changeMap(location);
            LocationServices.FusedLocationApi.removeLocationUpdates(
                    mGoogleApiClient, this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public void getWeatherDetails(String city,String country) {
        String tempUrl = "";
        if(city.equals("")){
        }else{
            if(!country.equals("")){
                tempUrl = url + "?q=" + city + "," + country + "&appid=" + appid;
            }else{
                tempUrl = url + "?q=" + city + "&appid=" + appid;
            }

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    String output = "";
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        JSONArray jsonArray = jsonResponse.getJSONArray("weather");
                        JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
                        String description = jsonObjectWeather.getString("description");

                        String icon = jsonObjectWeather.getString("icon");


                        JSONObject jsonObjectMain = jsonResponse.getJSONObject("main");
                        double temp = jsonObjectMain.getDouble("temp") - 273.15;
                        double feelsLike = jsonObjectMain.getDouble("feels_like") - 273.15;
                        float pressure = jsonObjectMain.getInt("pressure");
                        int humidity = jsonObjectMain.getInt("humidity");
                        JSONObject jsonObjectWind = jsonResponse.getJSONObject("wind");
                        String wind = jsonObjectWind.getString("speed");
                        JSONObject jsonObjectClouds = jsonResponse.getJSONObject("clouds");
                        String clouds = jsonObjectClouds.getString("all");
                        JSONObject jsonObjectSys = jsonResponse.getJSONObject("sys");
                        String countryName = jsonObjectSys.getString("country");

                        weatherTemp.setText(df.format(temp)+" °C");
                        String cityName = jsonResponse.getString("name");
//                        tvResult.setTextColor(Color.rgb(68,134,199));
//                        output += "Current weather of " + cityName + " (" + countryName + ")"
//                                + "\n Temp: " + df.format(temp) + " °C"
//                                + "\n Feels Like: " + df.format(feelsLike) + " °C"
//                                + "\n Humidity: " + humidity + "%"
//                                + "\n Description: " + description
//                                + "\n Wind Speed: " + wind + "m/s (meters per second)"
//                                + "\n Cloudiness: " + clouds + "%"
//                                + "\n Pressure: " + pressure + " hPa";


                        // Including the weather image created from the icon - use glide for this
//                        Glide.with(MainActivity.this).load("http://openweathermap.org/img/w/" + icon + ".png")
//                                .placeholder(R.mipmap.ic_launcher)
//                                .error(R.mipmap.ic_launcher)
//                                .into(image);

//                        tvResult.setText(output);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener(){

                @Override
                public void onErrorResponse(VolleyError error) {

                    Log.e("fuc", "onErrorResponse: "+ error.getMessage() );
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }
    }



}