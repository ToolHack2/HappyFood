package com.example.user.smartfoody.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.smartfoody.Model.Produces;
import com.example.user.smartfoody.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TuyChon_Adapter extends RecyclerView.Adapter<TuyChon_Adapter.ViewHoder> {
    private List<Produces> money_sp_list;
    private Context context;

    public TuyChon_Adapter(Context mcontext, List<Produces> mlist) {
        this.context = mcontext;
        this.money_sp_list = mlist;
    }

    @Override
    public ViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tuy_chon, parent, false);
        return new ViewHoder(view);
    }

    @Override
    public void onBindViewHolder(ViewHoder holder, int position) {
        final Produces produces = money_sp_list.get(position);
        holder.ten_tuychon.setText(produces.getName());
        String img = produces.getImage();
        Picasso.with(context).load(img).into(holder.img_tuychon);
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

        public ImageView img_tuychon;
        public TextView ten_tuychon;

        public ViewHoder(View itemView) {
            super(itemView);

            img_tuychon = (ImageView)itemView.findViewById(R.id.img_tuy_chon);
            ten_tuychon = (TextView)itemView.findViewById(R.id.txt_tuychon);
        }
    }
}
