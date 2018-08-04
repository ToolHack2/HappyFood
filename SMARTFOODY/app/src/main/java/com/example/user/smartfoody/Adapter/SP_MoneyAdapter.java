package com.example.user.smartfoody.Adapter;

import android.content.ClipData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.smartfoody.CartDB.Database;
import com.example.user.smartfoody.Model.Oder;
import com.example.user.smartfoody.Model.Produces;
import com.example.user.smartfoody.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 16/03/2018.
 */

public class SP_MoneyAdapter extends ArrayAdapter<Produces> {

    ArrayList<Produces> imgadapter;
    Context context;
    LayoutInflater inflater;

    public SP_MoneyAdapter( ArrayList<Produces> list, Context mcontext) {
        super(mcontext, R.layout.card_sp_item);
        this.imgadapter = list;
        this.context = mcontext;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @NonNull
    @Override
    public View getView(final int position, @Nullable View view, @NonNull ViewGroup parent) {
        Produces produces = imgadapter.get(position);


        final ViewHolder holder;
        if (view == null)
        {
            view = inflater.inflate(R.layout.card_sp_item, null);
            holder = new ViewHolder();

            holder.imageView = (ImageView)view.findViewById(R.id.sp_image);
            holder.txt_gia = (TextView)view.findViewById(R.id.sp_gia);
            holder.txt_ten = (TextView)view.findViewById(R.id.sp_name);
            holder.layout = (LinearLayout)view.findViewById(R.id.lo_cart);
            holder.unlayout = (LinearLayout)view.findViewById(R.id.unlo_cart);
            holder.btnmua = (Button)view.findViewById(R.id.btncart);
            holder.btnhided = (Button)view.findViewById(R.id.btnhide);
            holder.down = (Button)view.findViewById(R.id.btndown);
            holder.up = (Button)view.findViewById(R.id.btnup);
            holder.quantity = (TextView)view.findViewById(R.id.weatherquanti);
            holder.addcart = (Button)view.findViewById(R.id.btnaddbag);



            holder.btnhided.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.layout.setVisibility(View.GONE);
                    holder.unlayout.setVisibility(View.VISIBLE);
                }
            });

            holder.up.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String temp_quanti = holder.quantity.getText().toString();
                    int new_sum = Integer.parseInt(temp_quanti)+1;
                    holder.quantity.setText(String.valueOf(new_sum));
                }
            });

            holder.down.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String temp_quanti = holder.quantity.getText().toString();
                    int new_sum = Integer.parseInt(temp_quanti);
                    if (new_sum>0)
                    {
                        new_sum -= 1;
                        holder.quantity.setText(String.valueOf(new_sum));
                    }
                }
            });

            holder.btnmua.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.unlayout.setVisibility(View.GONE);
                    holder.layout.setVisibility(View.VISIBLE);

                }
            });
            holder.addcart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AddTempCart(imgadapter, position, holder.quantity.getText().toString());
                    Toast.makeText(getContext(), "Thêm thành công!", Toast.LENGTH_SHORT).show();
                }
            });

            view.setTag(holder);
        }
        else {
            holder = (ViewHolder) view.getTag();
        }

        Picasso.with(context).load(produces.getImage()).into(holder.imageView);
        holder.txt_gia.setText(produces.getPrice());
        holder.txt_ten.setText(produces.getName());

        return view;
    }

    public int getCount(){
        return imgadapter.size();
    }

    static class ViewHolder{
        ImageView imageView;
        TextView txt_gia,txt_ten, quantity;
        Button btnmua, btnxemtruoc, btnhided, down, up, addcart;
        LinearLayout layout, unlayout;
    }

    //
    // add temp cart on smartphone
    public void AddTempCart(List<Produces> templist, int i, String hodel )
    {
        String temp_img = templist.get(i).getImage();
        String temp_name = templist.get(i).getName();
        String temp_price = templist.get(i).getPrice();
        String temp_id = templist.get(i).getId();
        new Database(getContext()).AddToCart(new Oder(temp_id,temp_img,temp_name,temp_price,hodel));

    }
}
