package com.example.user.smartfoody.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.smartfoody.AcitvityHome.Cart;
import com.example.user.smartfoody.CartDB.Database;
import com.example.user.smartfoody.Fragment.Shop;
import com.example.user.smartfoody.Interface.ShopInterface;
import com.example.user.smartfoody.Model.Oder;
import com.example.user.smartfoody.Model.Produces;
import com.example.user.smartfoody.R;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private List<Oder> listproduce;
    private Cart cart;
    private Shop shop;
    int temp_money = 0;

    // constructor
    public CartAdapter(Cart cart, List<Oder> list)
    {
        this.cart = cart;
        this.listproduce = list;

    }
    // function oncreateviewholder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_product_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Oder produces = listproduce.get(position);
        String img_url = produces.getProduceImage();
        Picasso.with(cart).load(img_url).into(holder.cart_image);
        holder.cart_name.setText(produces.getProduceName());
        holder.cart_price.setText(produces.getProducePrice());
        holder.sum.setText(produces.getProduceQuantity());
        //control
        // update total money
        holder.up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tempsum = holder.sum.getText().toString();
                int new_sum = Integer.parseInt(tempsum) +1;
                Oder oder = listproduce.get(position);
                oder.setProduceQuantity(String.valueOf(new_sum));
                new Database(cart).UpdateCart(oder);
                holder.sum.setText(String.valueOf(new_sum));
                temp_money =0; // set temp_money = 0
                for (Oder item : listproduce)
                {
                    String price = item.getProducePrice(); // get item price
                    String sl = item.getProduceQuantity(); // get item quantity
                    temp_money += (Integer.parseInt(sl) * Integer.parseInt(price)); // sum money
                }
                Locale locale = new Locale("vi", "VN");
                NumberFormat format = NumberFormat.getCurrencyInstance(locale);
                cart.sum_money.setText(format.format(temp_money));
            }
        });

        holder.down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tempsum = holder.sum.getText().toString();
                int new_sum = Integer.parseInt(tempsum)-1;
                if (new_sum>0)
                {
                    holder.sum.setText(String.valueOf(new_sum));
                    Oder oder = listproduce.get(position);
                    oder.setProduceQuantity(String.valueOf(new_sum));
                    new Database(cart).UpdateCart(oder);
                    holder.sum.setText(String.valueOf(new_sum));
                    temp_money =0;
                    for (Oder item : listproduce)
                    {
                        String price = item.getProducePrice();
                        String sl = item.getProduceQuantity();
                        temp_money += (Integer.parseInt(sl) * Integer.parseInt(price));
                    }
                    Locale locale = new Locale("vi", "VN");
                    NumberFormat format = NumberFormat.getCurrencyInstance(locale);
                    cart.sum_money.setText(format.format(temp_money));
                }
            }
        });

        // cancel click
        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Oder oder = listproduce.get(position);
                int value = oder.getID();
                new Database(cart).DeleteItem(value);
                listproduce.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,listproduce.size());
                temp_money =0;
                for (Oder item : listproduce)
                {
                        String price = item.getProducePrice();
                        String sl = item.getProduceQuantity();
                        temp_money += (Integer.parseInt(sl) * Integer.parseInt(price));
                }
                Locale locale = new Locale("vi", "VN");
                NumberFormat format = NumberFormat.getCurrencyInstance(locale);
                cart.sum_money.setText(format.format(temp_money));
                if (listproduce.size() == 0)
                {
                    new Database(cart).CleanCart();
                }
            }
        });
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return listproduce.size();
    }

    // Class ViewHolder -> setup for all palette in view
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView cart_image, up, down,cancel; // create produce image
        public TextView cart_name, cart_price, sum; // create produce name and value

        public ViewHolder(final View itemView) {
            super(itemView);
            cart_image = (ImageView)itemView.findViewById(R.id.cart_img);
            up = (ImageView)itemView.findViewById(R.id.img_up);
            down = (ImageView)itemView.findViewById(R.id.img_down);
            cancel = (ImageView)itemView.findViewById(R.id.img_cancel);
            cart_name = (TextView)itemView.findViewById(R.id.cart_name);
            cart_price = (TextView)itemView.findViewById(R.id.cart_price);
            sum = (TextView)itemView.findViewById(R.id.txtSum);
        }
    }
}


