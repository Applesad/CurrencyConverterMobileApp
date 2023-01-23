package com.example.currencyconverterapp;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class ExchangeActivity extends AppCompatActivity {

    FusedLocationProviderClient fusedLocationProviderClient;
    private final static int REQUEST_CODE = 100;
    Spinner spinnerFrom, spinnerTo;
    EditText enterAmount;
    Button exchangeButton;
    SensorManager sensorManager;
    TextView country;
    private int shake_count = 0;
    private float acceleration = 0f;
    private float currentAcceleration = 0f;
    private float lastAcceleration = 0f;

    int changed = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();

        country = findViewById(R.id.country);
        enterAmount = findViewById(R.id.txtAmount);
        spinnerFrom = findViewById(R.id.spFrom);
        spinnerTo = findViewById(R.id.spTo);
        exchangeButton = findViewById(R.id.exchangeButton);

        ArrayAdapter adapterFrom = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,countryP);
        spinnerFrom.setAdapter(adapterFrom);

        ArrayAdapter adapterTo = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,countries);
        spinnerTo.setAdapter(adapterTo);

        exchangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String amount = enterAmount.getText().toString();
                try {
                    Convert(spinnerFrom.getSelectedItem().toString(),spinnerTo.getSelectedItem().toString(),amount);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor sensorShake = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(sensorEventListener, sensorShake, SensorManager.SENSOR_DELAY_NORMAL);

        acceleration = 10f;
        currentAcceleration = SensorManager.GRAVITY_EARTH;
        lastAcceleration = SensorManager.GRAVITY_EARTH;

    }
    private final SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            if (sensorEvent != null) {

                float x_accl = sensorEvent.values[0];
                float y_accl = sensorEvent.values[1];
                float z_accl = sensorEvent.values[2];
                lastAcceleration = currentAcceleration;

                currentAcceleration = (float)Math.sqrt(x_accl * x_accl * y_accl * y_accl * 1);

                float delta = currentAcceleration - lastAcceleration;
                acceleration = acceleration * 0.9f + delta;

                if (shake_count > 5) {
                    shake_count = 0;

                    Log.d("shake","shake complete");
                    Random random = new Random();
                    int from = random.nextInt(5);
                    int to = random.nextInt(5);
                    String fromString = countries[from];
                    String toString = countries[to];
                    try {
                        ConvertToText(fromString,toString,"1");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
                if (acceleration > 25) {
                    shake_count++;
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {
        }
    };
    public void ConvertToText(String convertFrom, String convertTo, String amount) throws IOException {

        String url_str = "https://api.exchangerate.host/convert?from="+convertFrom+"&to="+convertTo+"&amount="+amount;
        URL url = new URL(url_str);
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.connect();

        JsonParser jp = new JsonParser();
        JsonElement root = jp.parse(new InputStreamReader((InputStream)request.getContent()));
        JsonObject jsonObj = root.getAsJsonObject();

        String reqReseult = jsonObj.get("result").getAsString();

        Log.i("json",reqReseult);
        Toast.makeText(getApplicationContext(),"Conversion rate from " + convertFrom + " to " + convertTo+" is " + reqReseult.toString(),Toast.LENGTH_LONG).show();

    }

    public void Convert(String convertFrom, String convertTo, String amount) throws IOException {

        String url_str = "https://api.exchangerate.host/convert?from="+convertFrom+"&to="+convertTo+"&amount="+amount;
        URL url = new URL(url_str);
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.connect();

        JsonParser jp = new JsonParser();
        JsonElement root = jp.parse(new InputStreamReader((InputStream)request.getContent()));
        JsonObject jsonObj = root.getAsJsonObject();

        String reqReseult = jsonObj.get("result").getAsString();

        Log.i("json",reqReseult);
        Toast.makeText(getApplicationContext(),reqReseult.toString(),Toast.LENGTH_LONG).show();

    }
    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(sensorEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        sensorManager.unregisterListener(sensorEventListener);
        super.onPause();
    }
    private void getLastLocation(){

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){

            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            Geocoder geocoder= new Geocoder(ExchangeActivity.this, Locale.getDefault());
                            List<Address> addresses = null;
                            try {

                                addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                                country.setText(addresses.get(0).getCountryName());
                                changed = 1;

                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    });
        } else {
            askPermision();
        }
    }
    private void askPermision(){
        ActivityCompat.requestPermissions(ExchangeActivity.this,new String[]
                {Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode==REQUEST_CODE){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getLastLocation();;
            }
        }else{
            Toast.makeText(this,"No permission",Toast.LENGTH_SHORT).show();
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }











    String[] countries = {"AED","ALL","USD","PLN","EUR","AUD"};
    String[] countryP = {"PLN","AED","ALL","USD","PLN","EUR","AUD"};
}
