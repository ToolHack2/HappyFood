package com.example.user.smartfoody.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.smartfoody.AcitvityHome.DetailFoodMoney;
import com.example.user.smartfoody.AcitvityHome.MoneyMenu;
import com.example.user.smartfoody.Model.Produces;
import com.example.user.smartfoody.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Money_Adapter extends RecyclerView.Adapter<Money_Adapter.ViewHoder> {

    private List<Produces> money_sp_list;
    private Context context;

    public Money_Adapter(Context mcontext, List<Produces> mlist) {
        this.context = mcontext;
        this.money_sp_list = mlist;
    }

    @Override
    public ViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chinh_sp, parent, false);
        return new ViewHoder(view);
    }

    @Override
    public void onBindViewHolder(ViewHoder holder, int position) {
        final Produces produces = money_sp_list.get(position);
        holder.ten_chinh.setText(produces.getName());
        String img = produces.getImage();
        Picasso.with(context).load(img).into(holder.img_chinh);

        holder.img_chinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent todetail = new Intent(context, DetailFoodMoney.class);
                todetail.putExtra("ID", produces.getId());
                context.startActivity(todetail);
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return money_sp_list.size();
    }

    public class ViewHoder extends RecyclerView.ViewHolder {

        public ImageView img_chinh;
        public TextView ten_chinh;

        public ViewHoder(View itemView) {
            super(itemView);

            img_chinh = (ImageView)itemView.findViewById(R.id.item_imgchinh);
            ten_chinh = (TextView)itemView.findViewById(R.id.txt_chinh);
        }
    }

}
