package com.example.user.smartfoody.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.smartfoody.Model.BillProduct;
import com.example.user.smartfoody.Model.HistoryBill;
import com.example.user.smartfoody.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class History_Bill_Adapter extends RecyclerView.Adapter<History_Bill_Adapter.ViewHolder>{

    private List<HistoryBill> his_list;
    private Context context;


    //Contructor
    public History_Bill_Adapter(Context his_context, List<HistoryBill> historyBills)
    {
        this.context = his_context;
        this.his_list = historyBills;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_custom_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final HistoryBill historyBill = his_list.get(position);
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        final DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = inputFormat.parse(historyBill.getNgay());
            String outdate = simpleDateFormat.format(date);
            holder.ngay.setText(outdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.hd_id.setText("Hóa đơn "+ historyBill.getId());
        holder.noidung.setText(historyBill.getNoiDung());
        holder.his_tien.setText(historyBill.getTongTien());
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return his_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView ngay, noidung, hd_id, his_tien;

        public ViewHolder(View itemView) {
            super(itemView);

            ngay = (TextView)itemView.findViewById(R.id.txt_ngay);
            hd_id = (TextView)itemView.findViewById(R.id.txt_his_id);
            noidung = (TextView)itemView.findViewById(R.id.txt_noidung);
            his_tien = (TextView)itemView.findViewById(R.id.tong_tien_history);
        }
    }
}
