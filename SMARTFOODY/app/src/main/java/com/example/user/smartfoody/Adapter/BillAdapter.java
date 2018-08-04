package com.example.user.smartfoody.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.user.smartfoody.Model.BillProduct;
import com.example.user.smartfoody.R;
import com.squareup.picasso.Picasso;


import java.util.List;


public class BillAdapter extends RecyclerView.Adapter<BillAdapter.ViewHolder> {
    private List<BillProduct> listbillprod;
    private Context context;

    // constructor
    public BillAdapter(Context mcontext, List<BillProduct> list)
    {
        this.context = mcontext;
        this.listbillprod = list;

    }
    // function oncreateviewholder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bill_prod_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final BillProduct bill = listbillprod.get(position);
        String img_url = bill.getHinh();
        Picasso.with(context).load(img_url).into(holder.bill_prod_img);
        holder.bill_prod_name.setText(bill.getTenSP());
        holder.bill_prod_sl.setText(bill.getSoLuong());
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return listbillprod.size();
    }



    // Class ViewHolder -> setup for all palette in view
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView bill_prod_img; // create produce image
        public TextView bill_prod_name, bill_prod_sl; // create produce name and value

        public ViewHolder(final View itemView) {
            super(itemView);
            bill_prod_img = (ImageView)itemView.findViewById(R.id.bill_prod_img);
            bill_prod_name = (TextView)itemView.findViewById(R.id.bill_prod_name);
            bill_prod_sl = (TextView)itemView.findViewById(R.id.txtSL);

        }
    }
}


