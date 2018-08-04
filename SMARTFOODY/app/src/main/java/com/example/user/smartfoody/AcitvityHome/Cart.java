package com.example.user.smartfoody.AcitvityHome;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.smartfoody.Adapter.CartAdapter;
import com.example.user.smartfoody.Adapter.ProducesAdapter;
import com.example.user.smartfoody.CartDB.Database;
import com.example.user.smartfoody.Fragment.Shop;
import com.example.user.smartfoody.Login.LogIn;
import com.example.user.smartfoody.Login.Register;
import com.example.user.smartfoody.MainActivity;
import com.example.user.smartfoody.Model.Oder;
import com.example.user.smartfoody.Model.Produces;
import com.example.user.smartfoody.Model.User;
import com.example.user.smartfoody.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Cart extends AppCompatActivity {

    public CartAdapter adapter;
    public RecyclerView list_cart;
    public List<Oder> templist;
    public List<User> users;
    public TextView sum_money;
    Button cartback, trahang,dathang;
    String addcarturl = "https://nasu120696.000webhostapp.com/androidwebservice/add-chi-tiet.php";
    String addhoadonurl = "https://nasu120696.000webhostapp.com/androidwebservice/add-cart.php";
    Database database;
    EditText diachi;
    private String[] array, temp_point;
    private String point;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);


        list_cart = (RecyclerView)findViewById(R.id.list_cart);
        list_cart.setHasFixedSize(false);
        list_cart.setLayoutManager(new GridLayoutManager(this, 1));
        sum_money = (TextView)findViewById(R.id.txtsummon);
        cartback = (Button)findViewById(R.id.btncartback);
        trahang = (Button)findViewById(R.id.btntrahang);
        dathang = (Button)findViewById(R.id.btndathang);


        database = new Database(Cart.this);
        // get list produces from intent
        LoadListCart();
        users = new Database(this).getUserPhone();


        // cart back
        cartback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        dathang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog();
            }
        });
        trahang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.CleanCart();
                finish();
            }
        });
    }

    public void LoadListCart()
    {
        templist = new Database(this).getCart();
        adapter = new CartAdapter(this,templist);
        list_cart.setAdapter(adapter);
        int total = 0;
        for (Oder item:templist) {
            total += Integer.parseInt(item.getProducePrice()) * Integer.parseInt(item.getProduceQuantity());
        }
        Locale locale = new Locale("vi", "VN");
        NumberFormat format = NumberFormat.getCurrencyInstance(locale);
        sum_money.setText(format.format(total));
        point = String.valueOf(total);
    }

    //
    public void showAlertDialog(){
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.dialog_custom, null);
        diachi = alertLayout.findViewById(R.id.edtdiachi);
        //
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Happy Food ");
        builder.setMessage("Nhập địa chỉ:");
        builder.setView(alertLayout);
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AddHoaDon(diachi.getText().toString());
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    // add item in cart to database
    public void AddtoMysql(final int i)
    {

        StringRequest requesthd = new StringRequest(StringRequest.Method.POST, addcarturl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            String status = jsonObject.getString("status");

                            if (status.equals("ok"))
                            {
                            }
                            else {
                                Toast.makeText(Cart.this, "Đặt hàng thất bại!", Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
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

                params.put("user_phone",users.get(0).getUserphone() );
                params.put("product_id", templist.get(i).getProduceId());
                params.put("product_name", templist.get(i).getProduceName());
                params.put("product_price", templist.get(i).getProducePrice());
                params.put("product_quantity", templist.get(i).getProduceQuantity());
                params.put("product_image", templist.get(i).getProduceImage());
                return params;

            }
        };
        Volley.newRequestQueue(this).add(requesthd);
    }

    //add bill to mysql
    public void AddHoaDon(final String diachi)
    {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
        final String strDate = mdformat.format(calendar.getTime());
        StringRequest request = new StringRequest(StringRequest.Method.POST, addhoadonurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try
                        {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            String status = jsonObject.getString("status");

                            if (status.equals("ok"))
                            {
                                for (int id = 0; id<templist.size(); id++)
                                {
                                    AddtoMysql(id);
                                }
                                Toast.makeText(Cart.this, "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
                                database.CleanCart();
                                //go to map when add to mysql success
                                Intent tomap = new Intent(Cart.this, MapsActivity.class);
                                tomap.putExtra("diachi", diachi);
                                startActivity(tomap);

                            }
                            else {
                                Toast.makeText(Cart.this, "Đặt hàng thất bại!", Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
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
                params.put("user_phone",users.get(0).getUserphone() );
                params.put("diachi", diachi);
                params.put("ngaylap", strDate);
                params.put("ngaythanhtoan", "0");
                params.put("tongtien", sum_money.getText().toString());
                params.put("point", point);
                return params;

            }
        };
        Volley.newRequestQueue(this).add(request);
    }

}
