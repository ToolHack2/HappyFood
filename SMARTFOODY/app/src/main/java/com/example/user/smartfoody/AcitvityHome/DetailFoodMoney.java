package com.example.user.smartfoody.AcitvityHome;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.smartfoody.Adapter.History_Bill_Adapter;
import com.example.user.smartfoody.CartDB.Database;
import com.example.user.smartfoody.Model.HistoryBill;
import com.example.user.smartfoody.Model.Oder;
import com.example.user.smartfoody.Model.Produces;
import com.example.user.smartfoody.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailFoodMoney extends AppCompatActivity {

    private ImageView hinh, tang, giam, back;
    private TextView ten,gia, soluong;
    private Button mua;
    private String value, js_img,js_name, js_price, js_id ;
    private char first;
    private String detail = "https://nasu120696.000webhostapp.com/androidwebservice/get-prod-detail.php";
    private ProgressDialog dialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_money);

        //declare
        hinh = (ImageView)findViewById(R.id.img_detail);
        tang = (ImageView)findViewById(R.id.img_tang);
        giam = (ImageView)findViewById(R.id.img_giam);
        ten = (TextView)findViewById(R.id.txt_tensp_detail);
        gia = (TextView)findViewById(R.id.txt_giasp_detail);
        soluong = (TextView)findViewById(R.id.txt_sl_detail);
        mua = (Button)findViewById(R.id.btn_themsp);
        back = (ImageView) findViewById(R.id.btn_detail_back);


        Intent intent = getIntent();
        value = intent.getStringExtra("ID");
        first = value.charAt(0);
        //Toast.makeText(this, value, Toast.LENGTH_SHORT).show();

        //set up progress dialog
        dialog = new ProgressDialog(this);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setMessage("Please wait...");
        GetProduct(this,String.valueOf(first), value, detail);
        tang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SoLuong(true);
            }
        });

        giam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SoLuong(false);
            }
        });
        mua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddTempCart(js_id, js_img, js_name,js_price, soluong.getText().toString());
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void GetProduct(final Context context, final String ftchar, final String id, String url)
    {
        dialog.show();
        StringRequest request = new StringRequest(StringRequest.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONArray array = new JSONArray(response);

                                JSONObject object = array.getJSONObject(0);
                                js_img = object.getString("Hinh");
                                js_name = object.getString("Ten");
                                js_price = object.getString("Gia");
                                js_id = object.getString("ID");
                                String spnew = object.getString("New");
                                String sale = object.getString("Sale");
                                Picasso.with(context).load(js_img).into(hinh);
                                ten.setText(js_name);
                                gia.setText(js_price);
                            dialog.dismiss();

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
                params.put("first_char",ftchar );
                params.put("prod_id",id );
                return params;

            }
        };
        Volley.newRequestQueue(this).add(request);
    }

    public void SoLuong(boolean b)
    {
        if (b == true)
        {
            String tem = soluong.getText().toString();
            int new_sl = Integer.parseInt(tem) +1;
            soluong.setText(String.valueOf(new_sl));
        }
        else
        {

            String tem = soluong.getText().toString();
            int new_sl = Integer.parseInt(tem);
            if (new_sl >0)
                new_sl -= 1;
            soluong.setText(String.valueOf(new_sl));
        }
    }

    //
    public void AddTempCart(String sp_id, String sp_img, String sp_name, String sp_price, String sp_sl)
    {
        new Database(this).AddToCart(new Oder(sp_id,sp_img,sp_name,sp_price,sp_sl));
        Toast.makeText(this, "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
    }
}
