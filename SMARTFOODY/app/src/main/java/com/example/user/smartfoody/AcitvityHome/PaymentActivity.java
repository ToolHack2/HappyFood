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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.example.user.smartfoody.Adapter.BillAdapter;
import com.example.user.smartfoody.Adapter.ProducesAdapter;
import com.example.user.smartfoody.CartDB.Database;
import com.example.user.smartfoody.Model.BillProduct;
import com.example.user.smartfoody.Model.Produces;
import com.example.user.smartfoody.Model.User;
import com.example.user.smartfoody.R;
import com.lib.vtcpay.sdk.ICallBackPayment;
import com.lib.vtcpay.sdk.InitModel;
import com.lib.vtcpay.sdk.PaymentModel;
import com.lib.vtcpay.sdk.VTCPaySDK;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaymentActivity extends AppCompatActivity{
    private String getbill = "https://nasu120696.000webhostapp.com/androidwebservice/getbill.php";
    private String add_cmt = "https://nasu120696.000webhostapp.com/androidwebservice/add-comment.php";
    private String updatecart = "https://nasu120696.000webhostapp.com/androidwebservice/update-cart.php";
    private List<BillProduct> bill_list;
    public List<User> users;
    private BillAdapter adapter;
    private RecyclerView billview;
    private TextView bill_Id, bill_tien, bill_TenKH, bill_SDT;
    private String billid, sdt, tenkh, tien;
    private Button thanhtoan;
    String[] new_tien;

    // VTC PAY
    private InitModel initModel;

    //dialog custom
    RadioButton RD1, RD2, RD3,RD4,RD5,RD6,RD7;
    String ship_cmt ="null", product_cmt="null";
    RadioGroup ship_group, prod_group;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        users = new Database(this).getUserPhone();

        //VTC PAY
        //init model
        initModel = new InitModel();

        //
        bill_Id = (TextView)findViewById(R.id.bill_id);
        bill_tien = (TextView)findViewById(R.id.bill_total);
        bill_TenKH = (TextView)findViewById(R.id.bill_tenkh);
        bill_SDT = (TextView)findViewById(R.id.bill_sdt);
        thanhtoan = (Button)findViewById(R.id.btnthanhtoan);
        //
        bill_list = new ArrayList<>();

        billview = (RecyclerView)findViewById(R.id.bill_prod);
        billview.setHasFixedSize(true);
        billview.setLayoutManager(new GridLayoutManager(this, 1));
        GetBill(bill_list, billview);

        // pay
        thanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog();
            }
        });


    }

    // get bill from sever with user phone
    public void GetBill(final List<BillProduct> mbill, final RecyclerView mview )
    {
        StringRequest request = new StringRequest(StringRequest.Method.POST, getbill,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                                JSONArray array = new JSONArray(response);
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject object = array.getJSONObject(i);
                                    billid = object.getString("ID");
                                    sdt = object.getString("SDT");
                                    tenkh = object.getString("TenKH");
                                    tien = object.getString("TongTien");
                                    String hinh = object.getString("Hinh");
                                    String tenSP = object.getString("TenSP");
                                    String soLuong = object.getString("SoLuong");
                                    mbill.add(new BillProduct(hinh, tenSP, soLuong));
                                }
                                new_tien = tien.split("\\s");
                                tien = new_tien[1].replace(".","");
                                bill_Id.setText(billid);
                                bill_tien.setText(tien);
                                bill_SDT.setText(sdt);
                                bill_TenKH.setText(tenkh);
                                adapter = new BillAdapter(PaymentActivity.this, mbill);
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
                return params;

            }
        };
        Volley.newRequestQueue(this).add(request);
    }

    public void Payment()
    {
        initModel.setSandbox(true);//[Required] set enviroment test, default is true
        initModel.setCurrency(VTCPaySDK.VND);// set VND for bill
        initModel.setAmount(Long.parseLong(bill_tien.getText().toString())); //[Required] your amount
        initModel.setOrderCode(bill_Id.getText().toString());//[Required] your order code
        initModel.setAppID(Long.parseLong("500002593")); //[Required] your AppID that registered with VTC
        initModel.setSecretKey("trantheho1206996"); //[Required] your secret key that registered with VTC
        initModel.setReceiverAccount("01658562648");//[Required] your account
        initModel.setDescription("No description"); //[Option] description
        //initModel.setCurrency(VTCPaySDK.VND);//[Option] set currency, default is VND
        initModel.setDrawableLogoMerchant(R.drawable.logo_vtcpay); //[Option] Your logo
//        initModel.setHiddenForeignBank(cbIsHiddenPaymentForeignBank.isChecked());//[Option] hidden foreign bank
//        initModel.setHiddenPayVTC(cbIsHiddenPaymentVTCPay.isChecked());//[Option] hidden pay vtc
//        initModel.setHiddenDomesticBank(cbIsHiddenPaymentDomesticBank.isChecked());//[Option] hidden domestic bank
        VTCPaySDK.getInstance().setInitModel(initModel); //init model

        VTCPaySDK.getInstance().payment(PaymentActivity.this,
                new ICallBackPayment() {
                    @Override
                    public void onPaymentSuccess(PaymentModel paymentModel) {
                        Toast.makeText(
                                PaymentActivity.this,
                                "payment success transctionID "
                                        + paymentModel.getTransactionID(),
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPaymentError(int errorCode, String errorMessage, String bankName) {
                        Toast.makeText(PaymentActivity.this,
                                "Payment error " + errorMessage, Toast.LENGTH_SHORT)
                                .show();
                    }

                    @Override
                    public void onPaymentCancel() {
                        // TODO Auto-generated method stub
                        Toast.makeText(PaymentActivity.this, "Payment cancel ",
                                Toast.LENGTH_SHORT).show();
                    }

//					@Override
//					public void onPaymentError(String error) {
//						// TODO Auto-generated method stub
//						Toast.makeText(MainActivity.this,
//								"Payment error " + error, Toast.LENGTH_SHORT)
//								.show();
//					}
                });
    }

    // Dialog customer comment
    public void showAlertDialog(){
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.received_prod_dialog, null);

        //init
        ship_group = (RadioGroup)alertLayout.findViewById(R.id.ship_gr);
        prod_group = (RadioGroup)alertLayout.findViewById(R.id.prod_gr);

        RD1 = (RadioButton)alertLayout.findViewById(R.id.cb1);
        RD2 = (RadioButton)alertLayout.findViewById(R.id.cb2);
        RD3 = (RadioButton)alertLayout.findViewById(R.id.cb3);
        RD4 = (RadioButton)alertLayout.findViewById(R.id.cb4);
        RD5 = (RadioButton)alertLayout.findViewById(R.id.cb5);
        RD6 = (RadioButton)alertLayout.findViewById(R.id.cb6);
        RD7 = (RadioButton)alertLayout.findViewById(R.id.cb7);
        //control
        ship_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (RD1.isChecked())
                {
                    ship_cmt = RD1.getText().toString();
                }
                if (RD2.isChecked())
                {
                    ship_cmt = RD2.getText().toString();
                }
                if (RD3.isChecked())
                {
                    ship_cmt = RD3.getText().toString();
                }
            }
        });

        //
        prod_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (RD4.isChecked())
                {
                    product_cmt = RD4.getText().toString();
                }
                if (RD5.isChecked())
                {
                    product_cmt = RD5.getText().toString();
                }
                if (RD6.isChecked())
                {
                    product_cmt = RD6.getText().toString();
                }
                if (RD7.isChecked())
                {
                    product_cmt = RD7.getText().toString();
                }
            }
        });


        //
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Happy Food ");
        builder.setMessage("Đánh giá nhận hàng:");
        builder.setView(alertLayout);
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                PostComment(ship_cmt, product_cmt);
                Payment();
                UpdateCart();
                //Toast.makeText(PaymentActivity.this,ship_cmt, Toast.LENGTH_SHORT).show();
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

    // post comment of customer for bill to sever with user phone
    public void PostComment(final String ship_cment, final String prod_cment)
    {
        StringRequest request = new StringRequest(StringRequest.Method.POST, add_cmt,
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
                                Toast.makeText(PaymentActivity.this, "Đánh giá thất bại!", Toast.LENGTH_SHORT).show();
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
                params.put("bill_id", bill_Id.getText().toString());
                params.put("phone",users.get(0).getUserphone());
                params.put("TenKH", bill_TenKH.getText().toString());
                params.put("ship_cmt", ship_cment);
                params.put("prod_cmt", prod_cment);
                return params;
            }
        };
        Volley.newRequestQueue(this).add(request);
    }

    //update-card
    public void UpdateCart()
    {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("dd-MM-yyyy");
        final String end_date = mdformat.format(calendar.getTime());
        StringRequest request = new StringRequest(StringRequest.Method.POST, updatecart,
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
                                Toast.makeText(PaymentActivity.this, "Lỗi kết nối Internet !", Toast.LENGTH_SHORT).show();
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
                params.put("phone",users.get(0).getUserphone());
                params.put("ngay_thanh_toan", end_date);
                return params;
            }
        };
        Volley.newRequestQueue(this).add(request);
    }


}
