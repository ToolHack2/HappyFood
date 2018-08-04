package com.example.user.smartfoody.AcitvityHome;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.example.user.smartfoody.Adapter.Money_Adapter;
import com.example.user.smartfoody.Adapter.SP_MoneyAdapter;
import com.example.user.smartfoody.Adapter.TuyChon_Adapter;
import com.example.user.smartfoody.Model.Produces;
import com.example.user.smartfoody.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by User on 20/03/2018.
 */

public class MoneyMenu extends AppCompatActivity {

    Money_Adapter adapter;
    TuyChon_Adapter tuy_chon_adapter;
    ArrayList<Produces> splist, tuy_chon_list;
    RecyclerView viewsp, view_tuy_chon;
    ImageView m_back, m_cart;
    private TextView title;
    private ProgressDialog dialog;
    String value;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.money_menu);

        Intent intent = getIntent();
        value = intent.getStringExtra("value");
        //

        //
        m_back = (ImageView)findViewById(R.id.moneyback);
        m_cart = (ImageView)findViewById(R.id.moneycart);
        title = (TextView)findViewById(R.id.txt_title_money);
        title.setText(value);
        //
        viewsp = (RecyclerView) findViewById(R.id.listchinh);
        view_tuy_chon = (RecyclerView)findViewById(R.id.list_tuychon);
        view_tuy_chon.setHasFixedSize(true);
        view_tuy_chon.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        viewsp.setHasFixedSize(true);
        viewsp.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        splist = new ArrayList<>();
        tuy_chon_list = new ArrayList<>();
        //set up progress dialog
        dialog = new ProgressDialog(this);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setMessage("Please wait...");
        dialog.show();

        if(value.equals("Dưới 100.000"))
        {
            ReadJSON("https://nasu120696.000webhostapp.com/androidwebservice/support-100.php", splist, viewsp);
            getTuyChon("https://nasu120696.000webhostapp.com/androidwebservice/getsalas.php", tuy_chon_list, view_tuy_chon);
        }
        if (value.equals("100.000 - 150.000"))
        {
            ReadJSON("https://nasu120696.000webhostapp.com/androidwebservice/support-100-150.php", splist, viewsp);
            getTuyChon("https://nasu120696.000webhostapp.com/androidwebservice/getsalas.php", tuy_chon_list, view_tuy_chon);
        }
        if (value.equals("Trên 150.000"))
        {
            ReadJSON("https://nasu120696.000webhostapp.com/androidwebservice/support-150.php", splist, viewsp);
            getTuyChon("https://nasu120696.000webhostapp.com/androidwebservice/getsalas.php", tuy_chon_list, view_tuy_chon);
        }

        m_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        m_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tocart = new Intent(MoneyMenu.this, Cart.class);
                startActivity(tocart);
            }
        });

    }

    public void ReadJSON (final String url, final ArrayList<Produces> mlist, final RecyclerView mview)
    {
        //dialog.show();
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
                                mlist.add(new Produces(imgurl, name, money, id, spnew, sale));
                            }
                            dialog.dismiss();
                            adapter = new Money_Adapter(MoneyMenu.this, mlist);
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
                        Toast.makeText(MoneyMenu.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
        int socketTimeout = 3000;//3 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonArrayRequest.setRetryPolicy(policy);
        requestQueue.add(jsonArrayRequest);

    }

    //
    public void getTuyChon (final String url, final ArrayList<Produces> mlist, final RecyclerView mview)
    {
        //dialog.show();
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
                                mlist.add(new Produces(imgurl, name, money, id, spnew, sale));
                            }
                            tuy_chon_adapter = new TuyChon_Adapter(MoneyMenu.this, mlist);
                            mview.setAdapter( tuy_chon_adapter);

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
                        Toast.makeText(MoneyMenu.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
        int socketTimeout = 3000;//3 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonArrayRequest.setRetryPolicy(policy);
        requestQueue.add(jsonArrayRequest);

    }
}
