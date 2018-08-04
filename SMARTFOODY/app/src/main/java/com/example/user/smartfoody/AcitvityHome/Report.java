package com.example.user.smartfoody.AcitvityHome;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;


import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.smartfoody.Adapter.BillReportAdapter;
import com.example.user.smartfoody.CartDB.Database;
import com.example.user.smartfoody.Model.BillReport;
import com.example.user.smartfoody.Model.User;
import com.example.user.smartfoody.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Report extends AppCompatActivity {

    private String getbill = "https://nasu120696.000webhostapp.com/androidwebservice/report.php";
    CardView start, end;
    TextView monthstart, yearstart, daystart, monthend, yearend, dayend, rptongtien, title_date, text1, text2;
    Button report, report_again;
    LinearLayout layout,viewrp, layout_date;
    String timeStart = null, timeEnd = null, temp;
    public List<User> users;
    private List<BillReport> reportList;
    private long tongtien;
    private BillReportAdapter adapter;
    private RecyclerView reportview;
    String[] newtien;
    private ImageView img_home_rp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_activity);



        //

        start = (CardView)findViewById(R.id.cardstart);
        end = (CardView)findViewById(R.id.cardend);

        monthstart = (TextView)findViewById(R.id.txtstartmonth);
        yearstart = (TextView)findViewById(R.id.txtstartyear);
        daystart = (TextView)findViewById(R.id.txtstartday);
        monthend = (TextView)findViewById(R.id.txtendmonth);
        yearend = (TextView)findViewById(R.id.txtendyear);
        dayend = (TextView)findViewById(R.id.txtendday);
        rptongtien = (TextView)findViewById(R.id.txtrptien);
        text1 = (TextView)findViewById(R.id.txtstart);
        text2 = (TextView)findViewById(R.id.txtend);

        //
        //text1.setPaintFlags(text1.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        // toolbar
        title_date = (TextView)findViewById(R.id.txt_title_date);
        img_home_rp = (ImageView)findViewById(R.id.img_rp_home);

        //
        report_again = (Button)findViewById(R.id.btn_again);
        report = (Button)findViewById(R.id.btnreport);
        //layout = (LinearLayout)findViewById(R.id.layout_btn);
        viewrp = (LinearLayout)findViewById(R.id.viewreport);
        layout_date = (LinearLayout)findViewById(R.id.layoutdate);
        reportview = (RecyclerView)findViewById(R.id.recl_report);
        reportview.setHasFixedSize(true);
        reportview.setLayoutManager(new GridLayoutManager(this,1));

        final Animation slide_up = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_up);
        final Animation slide_down = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_down);

        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        users = new Database(this).getUserPhone();

        reportList = new ArrayList<>();
        // control click
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               SelectStart();

            }
        });

        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectEnd();
            }
        });

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (timeStart == null && timeEnd == null)
                {
                    showErrorDate();
                }
                else
                {
                    try {
                        Date date1 = simpleDateFormat.parse(timeStart);
                        Date date2 = simpleDateFormat.parse(timeEnd);
                        if (date1.after(date2))
                            showAlertDialog();
                        else
                        {
                            GetBillReport(reportList, reportview);
                            text1.setVisibility(View.GONE);
                            text2.setVisibility(View.GONE);
                            viewrp.startAnimation(slide_down);
                            viewrp.setVisibility(View.VISIBLE);
                            report.setVisibility(View.GONE);

                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        //
        img_home_rp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //
        report_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                reportList.clear();
                reportview.setAdapter(null);
                text1.setVisibility(View.VISIBLE);
                text2.setVisibility(View.VISIBLE);
                report.setAnimation(slide_down);
                report.setVisibility(View.VISIBLE);
                viewrp.startAnimation(slide_up);
                viewrp.setVisibility(View.GONE);
            }
        });

    }


    public void SelectStart()
    {
        final Calendar c = Calendar.getInstance();
        int nam = c.get(Calendar.YEAR);
        int thang = c.get(Calendar.MONTH);
        int ngay = c.get(Calendar.DATE);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                c.set(year,month,day);
                daystart.setText(String.valueOf(day));
                monthstart.setText("Tháng " + String.valueOf(month +1));
                yearstart.setText(String.valueOf(year));

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                timeStart = simpleDateFormat.format(c.getTime());
            }
        }, nam,thang,ngay);
        datePickerDialog.show();

    }

    //
    public void SelectEnd()
    {
        final Calendar c = Calendar.getInstance();
        int nam = c.get(Calendar.YEAR);
        int thang = c.get(Calendar.MONTH);
        int ngay = c.get(Calendar.DATE);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                c.set(year,month,day);
                dayend.setText(String.valueOf(day));
                monthend.setText("Tháng " + String.valueOf(month +1));
                yearend.setText(String.valueOf(year));

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                timeEnd = simpleDateFormat.format(c.getTime());

            }
        }, nam,thang,ngay);
        datePickerDialog.show();
    }

    // get bill from sever with user phone
    public void GetBillReport(final List<BillReport> mbill, final RecyclerView mview )
    {
        //mbill.removeAll(reportList);
        tongtien = 0;
        StringRequest request = new StringRequest(StringRequest.Method.POST, getbill,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                String newngaylap = object.getString("Ngaylap");
                                String newtongtien = object.getString("Tongtien");
                                mbill.add(new BillReport(newngaylap,newtongtien));
                            }

                            for (BillReport bill : mbill) {
                                newtien = bill.getTongTien().toString().split("\\s");
                                temp = newtien[1].replace(".","");
                                tongtien += Integer.parseInt(temp);
                            }
                            Locale locale = new Locale("vi", "VN");
                            NumberFormat format = NumberFormat.getCurrencyInstance(locale);
                            rptongtien.setText(format.format(tongtien));
                            adapter = new BillReportAdapter(Report.this, mbill);
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
                params.put("time_start",timeStart );
                params.put("time_end",timeEnd );
                return params;

            }
        };
        Volley.newRequestQueue(this).add(request);
    }

    public void showAlertDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Happy Food ");
        builder.setMessage("Ngày bắt đầu và ngày kết thúc không hợp lệ");
        builder.setCancelable(false);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
    //
    public void showErrorDate(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Happy Food ");
        builder.setMessage("Vui lòng chọn ngày bắt đầu và ngày kết thúc.");
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
