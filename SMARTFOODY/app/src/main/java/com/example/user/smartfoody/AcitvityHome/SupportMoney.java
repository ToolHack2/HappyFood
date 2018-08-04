package com.example.user.smartfoody.AcitvityHome;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.user.smartfoody.R;

/**
 * Created by User on 14/03/2018.
 */

public class SupportMoney extends AppCompatActivity {

    CardView card1, card2, card3;
    ImageView imgback;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sp_money);


        imgback = (ImageView)findViewById(R.id.img_money_home);

        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        card1 = (CardView)findViewById(R.id.scan1);
        card2 = (CardView)findViewById(R.id.scan2);
        card3 = (CardView)findViewById(R.id.scan3);

        // control click
        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tomoney = new Intent(SupportMoney.this, MoneyMenu.class);
                tomoney.putExtra("value", "Dưới 100.000");
                startActivity(tomoney);
            }
        });

        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tomoney = new Intent(SupportMoney.this, MoneyMenu.class);
                tomoney.putExtra("value","100.000 - 150.000");
                startActivity(tomoney);
            }
        });

        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toweather = new Intent(SupportMoney.this, MoneyMenu.class);
                toweather.putExtra("value","Trên 150.000");
                startActivity(toweather);
            }
        });


    }


}
