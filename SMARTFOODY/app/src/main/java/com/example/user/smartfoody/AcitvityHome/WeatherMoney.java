package com.example.user.smartfoody.AcitvityHome;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.smartfoody.Adapter.SP_MoneyAdapter;
import com.example.user.smartfoody.CartDB.Database;
import com.example.user.smartfoody.Model.Oder;
import com.example.user.smartfoody.Model.Produces;
import com.example.user.smartfoody.ModelWeather.OpenWeatherMap;
import com.example.user.smartfoody.R;
import com.example.user.smartfoody.Weather.Common;
import com.example.user.smartfoody.Weather.Helper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class WeatherMoney extends AppCompatActivity implements LocationListener {

    TextView city, update, description, celsius;
    ImageView imageView;
    Button w_back, w_cart;

    LocationManager locationManager;
    String provider;
    static double lat, lon;
    OpenWeatherMap openWeatherMap = new OpenWeatherMap();
    int MY_PERMISSION = 0;
    String des;
    SP_MoneyAdapter adapter;
    ArrayList<Produces> splist;
    ListView viewsp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_money);

        // Control

        city = (TextView) findViewById(R.id.txtcity);
        description = (TextView) findViewById(R.id.txtdescription);
        celsius = (TextView) findViewById(R.id.txtCelsius);

        imageView = (ImageView) findViewById(R.id.image);
        w_back = (Button)findViewById(R.id.weatherback);
        w_cart = (Button)findViewById(R.id.weathercart);




        //get coordinate

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), false);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(WeatherMoney.this, new String[]{
                    Manifest.permission.INTERNET,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.SYSTEM_ALERT_WINDOW,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE

            }, MY_PERMISSION);
        }
        Location location = locationManager.getLastKnownLocation(provider);

        if (location == null) {
            Log.e("TAG", "No location");
        }

        viewsp = (ListView)findViewById(R.id.listmenu);
        splist = new ArrayList<>();

        ReadJSON("https://nasu120696.000webhostapp.com/androidwebservice/getsalas.php", splist, viewsp);

        //
        w_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tocart = new Intent(WeatherMoney.this, Cart.class);
                startActivity(tocart);
            }
        });

        w_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {


            ActivityCompat.requestPermissions(WeatherMoney.this, new String[]{
                    Manifest.permission.INTERNET,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.SYSTEM_ALERT_WINDOW,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE

            }, MY_PERMISSION);
        }
        locationManager.removeUpdates(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(WeatherMoney.this, new String[]{
                    Manifest.permission.INTERNET,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.SYSTEM_ALERT_WINDOW,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE

            }, MY_PERMISSION);
        }
        locationManager.requestLocationUpdates(provider, 400, 1, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        lat = location.getLatitude();
        lon = location.getLongitude();

        new GetWeather().execute(Common.apiRequest(String.valueOf(lat),String.valueOf(lon)));
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    private class GetWeather extends AsyncTask<String, Void, String>
    {

        ProgressDialog pd = new ProgressDialog(WeatherMoney.this);



        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }



        @Override
        protected String doInBackground(String... params) {

            String stream = null;
            String urlString = params[0];
            Helper http = new Helper();
            stream = http.getHTTPdata(urlString);

            return stream;


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.contains("Error: Not found city"))
            {
                pd.dismiss();
                return;
            }

            Gson gson = new Gson();
            Type mtype = new TypeToken<OpenWeatherMap>(){}.getType();

            openWeatherMap = gson.fromJson(s,mtype);
            pd.dismiss();

            city.setText(String.format("%s", openWeatherMap.getName()));
            try {
                des = openWeatherMap.getWeather().get(0).getDescription();
                if (des.equals("light rain"))
                {
                    description.setText("Mưa rào");
                    imageView.setBackgroundResource(R.drawable.rain_icon);

                }
                if (des.equals("scattered clouds"))
                {
                    description.setText("Trời khô nóng, cần bổ sung vitamin C và trái cây");
                    imageView.setBackgroundResource(R.drawable.cloudy_icon);
                } 
                if (des.equals("broken clouds"))
                {
                    description.setText("Trời nhiều mây, se lạnh, nên ăn nhẹ ít trái cây");
                    imageView.setBackgroundResource(R.drawable.cloud_night_icon);
                }


//                if (des.equals("light rain"))
//                {
//                    description.setText("Mưa rào");
//                }

            }catch (NullPointerException e)
            {
                return;
            }


            celsius.setText(String.format("%.0f °C", openWeatherMap.getMain().getTemp() -273));

            //Picasso.with(WeatherMoney.this).load(Common.getImage(openWeatherMap.getWeather().get(0).getIcon())).into(imageView);

        }
    }

    public void ReadJSON (final String url, final ArrayList<Produces> mlist, final ListView mview)
    {


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try{
                            for (int i=0; i<response.length(); i++)
                            {
                                JSONObject object = response.getJSONObject(i);
                                String imgurl = object.getString("Hinh");
                                String name = object.getString("Ten");
                                String money = object.getString("Gia");
                                String id = object.getString("ID");
                                String spnew = object.getString("New");
                                String sale = object.getString("Sale");
                                mlist.add(new Produces(imgurl, name, money, id, spnew,sale));
                            }
                            adapter = new SP_MoneyAdapter(mlist,WeatherMoney.this);
                            mview.setAdapter( adapter);


                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(WeatherMoney.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
        int socketTimeout = 3000;//3 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonArrayRequest.setRetryPolicy(policy);
        requestQueue.add(jsonArrayRequest);

    }

}
