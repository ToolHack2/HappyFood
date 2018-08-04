package com.example.user.smartfoody.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.Toast;

import com.example.user.smartfoody.AcitvityHome.MoneyMenu;
import com.example.user.smartfoody.AcitvityHome.Scan;
import com.example.user.smartfoody.AcitvityHome.SupportMoney;
import com.example.user.smartfoody.AcitvityHome.WeatherMoney;
import com.example.user.smartfoody.R;

/**
 * Created by User on 11/27/2017.
 */

public class FoodEDay extends Fragment {

    CardView money, weather, kid;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.foodeday, null);


        money = (CardView)view.findViewById(R.id.cardmoney);
        weather = (CardView)view.findViewById(R.id.cardweather);
        kid = (CardView)view.findViewById(R.id.cardkid);

        // control click
        money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tomoney = new Intent(getContext(), SupportMoney.class);
                startActivity(tomoney);
            }
        });

        weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toweather = new Intent(getContext(), WeatherMoney.class);
                startActivity(toweather);
            }
        });

        kid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Kid Food", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }


}
