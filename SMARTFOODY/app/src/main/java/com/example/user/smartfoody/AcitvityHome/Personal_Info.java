package com.example.user.smartfoody.AcitvityHome;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.smartfoody.CartDB.Database;
import com.example.user.smartfoody.MainActivity;
import com.example.user.smartfoody.Model.User;
import com.example.user.smartfoody.R;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Personal_Info extends AppCompatActivity{

    private CardView hoten, lichsu_mh, doi_diem;
    private TextView ten1, ten2, diem_tich_luy, call;
    private EditText new_name;
    private String update_name = "https://nasu120696.000webhostapp.com/androidwebservice/update-name.php";
    private String getinfo = "https://nasu120696.000webhostapp.com/androidwebservice/show-user.php";
    public List<User> users;
    ImageView perback;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_info_activity);



        hoten = (CardView)findViewById(R.id.cv_ho_ten);
        lichsu_mh = (CardView)findViewById(R.id.cv_lich_su_mh);
        doi_diem = (CardView)findViewById(R.id.cv_doi_diem);
        ten1 = (TextView)findViewById(R.id.txtper_name1);
        ten2 = (TextView)findViewById(R.id.txt_per_name2);
        diem_tich_luy = (TextView)findViewById(R.id.txt_diem_tich_luy);
        call = (TextView)findViewById(R.id.txtper_call);
        perback = (ImageView)findViewById(R.id.img_per_back);


        perback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gohome = new Intent(Personal_Info.this, MainActivity.class);
                startActivity(gohome);
            }
        });

        users = new Database(this).getUserPhone();

        Getinfo();
        hoten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog();
            }
        });

        lichsu_mh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tohistory = new Intent(Personal_Info.this, History_buy.class);
                startActivity(tohistory);
                overridePendingTransition(R.anim.in_right, R.anim.out_left);
            }
        });

    }

    //
    public void showAlertDialog(){
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.change_name_dialog, null);
        new_name = alertLayout.findViewById(R.id.edt_new_name);
        //
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Happy Food ");
        builder.setMessage("Nhập họ tên mới:");
        builder.setView(alertLayout);
        builder.setCancelable(false);
        builder.setPositiveButton("Lưu", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Toast.makeText(Personal_Info.this, new_name.getText().toString(), Toast.LENGTH_SHORT).show();
                UpdateName(new_name.getText().toString());
                ten1.setText(new_name.getText().toString());
                ten2.setText(new_name.getText().toString());
            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    //update new name to mysql
    public void UpdateName(final String newname)
    {
        StringRequest request = new StringRequest(StringRequest.Method.POST, update_name,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            JSONObject object = array.getJSONObject(0);
                            String status = object.getString("status");
                            if (status.equals("ok"))
                            {
                                Toast.makeText(Personal_Info.this, "Lưu thành công", Toast.LENGTH_SHORT).show();
                            }
                            else
                                Toast.makeText(Personal_Info.this, "Kiểm tra lại kết nối Internet", Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
               Map<String, String> params = new HashMap<>();
               params.put("phone", users.get(0).getUserphone());
               params.put("new_name", newname);
               return params;
            }
        };

        Volley.newRequestQueue(this).add(request);
    }

    //update new name to mysql
    public void Getinfo()
    {
        StringRequest request = new StringRequest(StringRequest.Method.POST, getinfo,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //Toast.makeText(Personal_Info.this, response, Toast.LENGTH_SHORT).show();
                            JSONArray array = new JSONArray(response);
                            for (int i=0; i<array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                String name = object.getString("Name");
                                String sdt = object.getString("Phone");
                                String diem = object.getString("Point");
                                ten1.setText(name);
                                ten2.setText(name);
                                diem_tich_luy.setText("Điểm tích lũy: " + diem);
                                call.setText(sdt);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("phone", users.get(0).getUserphone());
                return params;
            }
        };

        Volley.newRequestQueue(this).add(request);
    }
}
