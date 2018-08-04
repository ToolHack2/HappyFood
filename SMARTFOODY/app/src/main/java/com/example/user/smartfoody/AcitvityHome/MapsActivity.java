package com.example.user.smartfoody.AcitvityHome;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.smartfoody.Adapter.BillAdapter;
import com.example.user.smartfoody.CartDB.Database;
import com.example.user.smartfoody.GoogleDirection.DirectionFinder;
import com.example.user.smartfoody.GoogleDirection.DirectionFinderListener;
import com.example.user.smartfoody.Model.BillProduct;
import com.example.user.smartfoody.Model.Route;
import com.example.user.smartfoody.Model.User;
import com.example.user.smartfoody.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, DirectionFinderListener {

    private GoogleMap mMap;
    private LatLng khohang;
    private String start = "Lotteria Big C Bình Dương, Đại lộ Bình Dương, Thủ Dầu Một, Bình Dương";
    private String endlocation;

    ProgressDialog progressDialog;
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();

    //
    private TextView time;
    private CountDownTimer countDownTimer;
    private long timedown;
    String temp;
    String[] timetemp;
    private Button nhanhang;
    public List<User> users;
    private String getbill = "https://nasu120696.000webhostapp.com/androidwebservice/getbill.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        time = (TextView)findViewById(R.id.txtTime);
        nhanhang = (Button)findViewById(R.id.btnnhanhang);
        users = new Database(this).getUserPhone();

        //create khohang location
        khohang = new LatLng(10.9870689, 106.6628163);
        Intent intent = getIntent();
        endlocation = intent.getStringExtra("diachi");
        sendRequest(endlocation);

        //
        nhanhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent topay = new Intent(MapsActivity.this, PaymentActivity.class);
                startActivity(topay);
            }
        });

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
//        mMap.addMarker(new MarkerOptions().position(khohang).title("Kho hàng Happy Food"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(khohang, 16));

        originMarkers.add(mMap.addMarker(new MarkerOptions()
                .title("Kho hàng Happy Food")
                .position(khohang)));
    }

    private void sendRequest(String endlocation) {
        try {
            new DirectionFinder( this, start, endlocation).execute();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void onDirectionFinderStart() {
        progressDialog = ProgressDialog.show(this, "Please wait...",
                "Finding direction..!", true);


        if (originMarkers != null) {
            for (Marker marker : originMarkers) {
                marker.remove();
            }
        }

        if (destinationMarkers != null) {
            for (Marker marker : destinationMarkers) {
                marker.remove();
            }
        }

        if (polylinePaths != null) {
            for (Polyline polyline:polylinePaths ) {
                polyline.remove();
            }
        }
    }

    public void onDirectionFinderSuccess(List<Route> routes) {
        progressDialog.dismiss();
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();

        for (Route route : routes) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(khohang, 16));
            temp = route.duration.text;
            timetemp = temp.split("\\s");
            time.setText(timetemp[0]);
            timedown = Long.parseLong(timetemp[0])*60000 + 1500000;
            StartTimer(timedown);

            originMarkers.add(mMap.addMarker(new MarkerOptions()
                    .title("Kho hàng Happy Food")
                    .position(khohang)));
            destinationMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.location_user))
                    .title(route.endAddress)
                    .position(route.endLocation)));

            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.BLUE).
                    width(10);

            for (int i = 0; i < route.points.size(); i++)
                polylineOptions.add(route.points.get(i));

            polylinePaths.add(mMap.addPolyline(polylineOptions));
        }
    }

    public void StartTimer(long Timedown)
    {
        countDownTimer = new CountDownTimer(Timedown, 1000) {
            @Override
            public void onTick(long newtime) {
                timedown = newtime;
                updatecoundown();
            }

            @Override
            public void onFinish() {

            }
        }.start();

    }

    public void updatecoundown()
    {
        int minute = (int) (timedown/1000) / 60;
        int seconds = (int) (timedown / 1000) % 60;

        String timeformat = String.format(Locale.getDefault(), "%02d:%02d", minute, seconds);
        time.setText(timeformat);
    }

    @Override
    public void onBackPressed() {
        showDialog();
    }

    public void showDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Happy Food ");
        builder.setMessage("Vui lòng không thoát để nhận hàng !");
        builder.setCancelable(false);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
}
