package com.example.user.smartfoody.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.smartfoody.AcitvityHome.Cart;
import com.example.user.smartfoody.Adapter.CartAdapter;
import com.example.user.smartfoody.Adapter.ProducesAdapter;
import com.example.user.smartfoody.CartDB.Database;
import com.example.user.smartfoody.Interface.ShopInterface;
import com.example.user.smartfoody.MainActivity;
import com.example.user.smartfoody.Model.Oder;
import com.example.user.smartfoody.Model.Produces;
import com.example.user.smartfoody.R;
import com.example.user.smartfoody.View.AnimationTab;
import com.example.user.smartfoody.View.Video;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLConnection;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

/**
 * Created by User on 11/27/2017.
 */

public class Shop extends Fragment {

    private  RecyclerView list1, list2,list3,list4;
    private  ProducesAdapter adapter1;
    private  List<Produces> listp1,listp2, listp3, listp4;
    public List<Oder> listcart;
    private TextView bag_count;
    private ImageView bag_cart;
    int count ;
    TabHost tabHost;



    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.shop, null);
        tabHost = (TabHost)view.findViewById(R.id.tabhost);
        tabHost.setup();


        //
        bag_count = (TextView)view.findViewById(R.id.cart_badge);

        bag_cart = (ImageView)view.findViewById(R.id.imgbag);



        listcart = new Database(getContext()).getCart();
        if (listcart.size() > 0)
        {
            bag_count.setText(String.valueOf(listcart.size()));
            bag_count.setVisibility(View.VISIBLE);
        }
        count = listcart.size();


        bag_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tocart = new Intent(getContext(), Cart.class);
                startActivity(tocart);
            }
        });





        // list product tab 1
        list1 = (RecyclerView) view.findViewById(R.id.listitem1);
        list1.setHasFixedSize(false);
        list1.setLayoutManager(new GridLayoutManager(this.getContext(), 2));

        // list product tab 2
        list2 = (RecyclerView)view.findViewById(R.id.listitem2);
        list2.setHasFixedSize(false);
        list2.setLayoutManager(new GridLayoutManager(this.getContext(), 2));

        // list product tab3
        list3 = (RecyclerView) view.findViewById(R.id.listitem3);
        list3.setHasFixedSize(false);
        list3.setLayoutManager(new GridLayoutManager(this.getContext(), 2));

        // list product tab4
        list4 = (RecyclerView) view.findViewById(R.id.listitem4);
        list4.setHasFixedSize(false);
        list4.setLayoutManager(new GridLayoutManager(this.getContext(), 2));

        //
        listp1 = new ArrayList<>();
        listp2 = new ArrayList<>();
        listp3 = new ArrayList<>();
        listp4 = new ArrayList<>();


        //tab1
        TabHost.TabSpec spec1 = tabHost.newTabSpec(getResources().getString(R.string.tab1));
        spec1.setContent(R.id.tab1);
        spec1.setIndicator(getResources().getString(R.string.tab1));
        tabHost.addTab(spec1);
        //tab2
        TabHost.TabSpec spec2 = tabHost.newTabSpec(getResources().getString(R.string.tab2));
        spec2.setContent(R.id.tab2);
        spec2.setIndicator(getResources().getString(R.string.tab2));
        tabHost.addTab(spec2);
        //tab3
        TabHost.TabSpec spec3 = tabHost.newTabSpec(getResources().getString(R.string.tab3));
        spec3.setContent(R.id.tab3);
        spec3.setIndicator(getResources().getString(R.string.tab3));
        tabHost.addTab(spec3);
        //tab4
        TabHost.TabSpec spec4 = tabHost.newTabSpec(getResources().getString(R.string.tab4));
        spec4.setContent(R.id.tab4);
        spec4.setIndicator(getResources().getString(R.string.tab4));
        tabHost.addTab(spec4);
        //call animation when tab change
        tabHost.setOnTabChangedListener(new AnimationTab(this.getContext(), tabHost));
        ReadJSON("https://nasu120696.000webhostapp.com/androidwebservice/getxao.php", listp1, list1);
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                if (s == getResources().getString(R.string.tab1))
                {
                    ReadJSON("https://nasu120696.000webhostapp.com/androidwebservice/getxao.php", listp1, list1);
                }
                if (s == getResources().getString(R.string.tab2))
                {

                    ReadJSON("https://nasu120696.000webhostapp.com/androidwebservice/getkho.php", listp2, list2);
                }
                if (s == getResources().getString(R.string.tab3))
                {
                    ReadJSON("https://nasu120696.000webhostapp.com/androidwebservice/getcanh.php", listp3, list3);
                }
                if (s == getResources().getString(R.string.tab4))
                {
                    ReadJSON("https://nasu120696.000webhostapp.com/androidwebservice/getsalas.php", listp4, list4);
                }
            }
        });

        return view;

    }

    public void ReadJSON (final String url, final List<Produces> mlist, final RecyclerView mview)
    {

        final List<Produces> temlist = null;
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try{
                            if (mlist.size() == 0) {
                                for (int i = 0; i < response.length(); i++) {

                                    JSONObject object = response.getJSONObject(i);
                                    String imgurl = object.getString("Hinh");
                                    String name = object.getString("Ten");
                                    String money = object.getString("Gia");
                                    String id = object.getString("ID");
                                    String spnew = object.getString("New");
                                    String sale = object.getString("Sale");
                                    mlist.add(new Produces(imgurl, name, money, id, spnew, sale));

                                }
                            }
                            adapter1 = new ProducesAdapter(getContext(),mlist);
                            mview.setAdapter(adapter1);
                            adapter1.setOnItemClickListener(new ProducesAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(int position) {
                                    count++;
                                    AddTempCart(mlist,position);
                                    bag_count.setText(String.valueOf(count));
                                    bag_count.setVisibility(View.VISIBLE);
                                    Toast.makeText(getContext(),mlist.get(position).getId(), Toast.LENGTH_SHORT).show();
                                }
                            });
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
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
        int socketTimeout = 3000;//3 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonArrayRequest.setRetryPolicy(policy);
        requestQueue.add(jsonArrayRequest);

    }

    // add temp cart on smartphone
    public void AddTempCart(List<Produces> templist, int i )
    {
        String temp_img = templist.get(i).getImage();
        String temp_name = templist.get(i).getName();
        String temp_price;
        if (templist.get(i).getSale().equals("0"))
        {
            temp_price = templist.get(i).getPrice();

        }
        else {
            int sale_money = Integer.parseInt(templist.get(i).getPrice()) - (Integer.parseInt(templist.get(i).getPrice())*(Integer.parseInt(templist.get(i).getSale())) / 100 );
            temp_price = String.valueOf(sale_money);
        }

        String temp_id = templist.get(i).getId();
        String temp_quantity = "1";
        new Database(getContext()).AddToCart(new Oder(temp_id,temp_img,temp_name,temp_price,temp_quantity));

    }

}
