package com.example.user.smartfoody.AcitvityHome;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.TabLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.smartfoody.Adapter.BillReportAdapter;
import com.example.user.smartfoody.Adapter.History_Bill_Adapter;
import com.example.user.smartfoody.CartDB.Database;
import com.example.user.smartfoody.Model.BillReport;
import com.example.user.smartfoody.Model.HistoryBill;
import com.example.user.smartfoody.Model.User;
import com.example.user.smartfoody.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class History_buy extends AppCompatActivity {

    public List<User> users;
    private ImageView img;
    private List<HistoryBill> list_history;
    private RecyclerView his_view;
    private History_Bill_Adapter adapter;
    TabLayout tabLayout;
    private String gethistory = "https://nasu120696.000webhostapp.com/androidwebservice/get-history-bill.php";
    private ProgressDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_buy_activity);

        img = (ImageView)findViewById(R.id.img_his);
        tabLayout = (TabLayout)findViewById(R.id.tab_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //just set title when CollapsingToolbarLayout is fully collapsed
        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collap_layout);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    img.setVisibility(View.INVISIBLE);
                    collapsingToolbarLayout.setTitle("Lịch sử mua hàng");
                    isShow = true;
                } else if(isShow) {
                    img.setVisibility(View.VISIBLE);
                    collapsingToolbarLayout.setTitle(" ");//carefull there should a space between double quote otherwise it wont work
                    isShow = false;
                }
            }
        });

        users = new Database(this).getUserPhone();
        his_view = (RecyclerView)findViewById(R.id.his_recycler);
        his_view.setHasFixedSize(false);
        his_view.setLayoutManager(new GridLayoutManager(this, 1));
        list_history = new ArrayList<>();


        //set up progress dialog
        dialog = new ProgressDialog(this);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setMessage("Please wait...");


        initTab();
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#03A9F4"));
        tabLayout.setTabTextColors(Color.parseColor("#727272"), Color.parseColor("#03A9F4"));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                switch (tab.getPosition())
                {
                    case 0:
                        list_history.clear();
                        GetHistoryBill(list_history, his_view, "1");
                        break;
                    case 1:
                        list_history.clear();
                        GetHistoryBill(list_history, his_view, "2");
                        break;
                    case 2:
                        list_history.clear();
                        GetHistoryBill(list_history, his_view, "3");
                        break;
                    case 3:
                        list_history.clear();
                        GetHistoryBill(list_history, his_view, "4");
                        break;
                    case 4:
                        list_history.clear();
                        GetHistoryBill(list_history, his_view, "5");
                        break;
                    case 5:
                        list_history.clear();
                        GetHistoryBill(list_history, his_view, "6");
                        break;
                    case 6:
                        list_history.clear();
                        GetHistoryBill(list_history, his_view, "7");
                        break;
                    case 7:
                        list_history.clear();
                        GetHistoryBill(list_history, his_view, "8");
                        break;
                    case 8:
                        list_history.clear();
                        GetHistoryBill(list_history, his_view, "9");
                        break;
                    case 9:
                        list_history.clear();
                        GetHistoryBill(list_history, his_view, "10");
                        break;
                    case 10:
                        list_history.clear();
                        GetHistoryBill(list_history, his_view, "11");
                        break;
                    case 11:
                        list_history.clear();
                        GetHistoryBill(list_history, his_view, "12");
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                Intent intent = new Intent(History_buy.this, Personal_Info.class);
                startActivity(intent);
                overridePendingTransition(R.anim.in_left, R.anim.out_right);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void initTab() {

        TabLayout.Tab tab1 = tabLayout.newTab();
        tab1.setText("tháng 1");
        tabLayout.addTab(tab1);
        TabLayout.Tab tab2 = tabLayout.newTab();
        tab2.setText("tháng 2");
        tabLayout.addTab(tab2);
        TabLayout.Tab tab3 = tabLayout.newTab();
        tab3.setText("tháng 3");
        tabLayout.addTab(tab3);
        TabLayout.Tab tab4 = tabLayout.newTab();
        tab4.setText("tháng 4");
        tabLayout.addTab(tab4);
        TabLayout.Tab tab5 = tabLayout.newTab();
        tab5.setText("tháng 5");
        tabLayout.addTab(tab5);
        TabLayout.Tab tab6 = tabLayout.newTab();
        tab6.setText("tháng 6");
        tabLayout.addTab(tab6);
        TabLayout.Tab tab7 = tabLayout.newTab();
        tab7.setText("tháng 7");
        tabLayout.addTab(tab7);
        TabLayout.Tab tab8 = tabLayout.newTab();
        tab8.setText("tháng 8");
        tabLayout.addTab(tab8);
        android.support.design.widget.TabLayout.Tab tab9 = tabLayout.newTab();
        tab9.setText("tháng 9");
        tabLayout.addTab(tab9);
        android.support.design.widget.TabLayout.Tab tab10 = tabLayout.newTab();
        tab10.setText("tháng 10");
        tabLayout.addTab(tab10);
        TabLayout.Tab tab11 = tabLayout.newTab();
        tab11.setText("tháng 11");
        tabLayout.addTab(tab11);
        TabLayout.Tab tab12 = tabLayout.newTab();
        tab12.setText("tháng 12");
        tabLayout.addTab(tab12);
    }

    // get bill from sever with user phone
    public void GetHistoryBill(final List<HistoryBill> mbill, final RecyclerView mview, final String thang)
    {
        dialog.show();
        //mbill.removeAll(reportList);
        StringRequest request = new StringRequest(StringRequest.Method.POST, gethistory,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                String his_ngay = object.getString("Ngay");
                                String his_id = object.getString("ID");
                                String his_tongtien = object.getString("Tongtien");
                                String his_noidung = object.getString("NoiDung");
                                mbill.add(new HistoryBill(his_ngay,his_id,his_tongtien,his_noidung));
                            }
                            dialog.dismiss();
                            adapter = new History_Bill_Adapter(History_buy.this, mbill);
                            mview.setAdapter(adapter);
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                //put user
                params.put("phone",users.get(0).getUserphone() );
                params.put("thang",thang );
                return params;

            }
        };
        Volley.newRequestQueue(this).add(request);

    }


}
