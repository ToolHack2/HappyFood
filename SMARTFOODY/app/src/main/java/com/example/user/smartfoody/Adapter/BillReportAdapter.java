package com.example.user.smartfoody.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.smartfoody.Model.BillProduct;
import com.example.user.smartfoody.Model.BillReport;
import com.example.user.smartfoody.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BillReportAdapter extends RecyclerView.Adapter<BillReportAdapter.ViewHolder> {
    private List<BillReport> listbillrp;
    private Context context;

    // constructor
    public BillReportAdapter(Context mcontext, List<BillReport> list)
    {
        this.context = mcontext;
        this.listbillrp = list;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bill_report_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final BillReport billReport = listbillrp.get(position);
        holder.ngaylapbill.setText(billReport.getNgayMua());
        holder.tongtienbill.setText(billReport.getTongTien());
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return listbillrp.size();
    }


    // Class ViewHolder -> setup for all palette in view
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView ngaylapbill, tongtienbill; // create produce name and value

        public ViewHolder(final View itemView) {
            super(itemView);
            ngaylapbill = (TextView)itemView.findViewById(R.id.txtngaylap);
            tongtienbill = (TextView)itemView.findViewById(R.id.txttongtien);

        }
    }
}