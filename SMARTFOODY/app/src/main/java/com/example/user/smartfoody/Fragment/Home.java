package com.example.user.smartfoody.Fragment;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.Toast;

import com.example.user.smartfoody.AcitvityHome.Personal_Info;
import com.example.user.smartfoody.AcitvityHome.Report;
import com.example.user.smartfoody.AcitvityHome.Scan;
import com.example.user.smartfoody.Login.Register;
import com.example.user.smartfoody.R;
import com.example.user.smartfoody.View.ViewVideo;


/**
 * Created by User on 11/27/2017.
 */

public class Home extends Fragment {


    CardView scan, report, account, smile;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home, container, false);


        scan = (CardView)view.findViewById(R.id.cardScan);
        report = (CardView)view.findViewById(R.id.cardReport);
        account = (CardView)view.findViewById(R.id.cardUser);
        smile = (CardView)view.findViewById(R.id.cardsmile);

        // control click
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toscan = new Intent(getContext(), Scan.class);
                startActivity(toscan);
            }
        });

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toreport = new Intent(getContext(), Report.class);
                startActivity(toreport);
            }
        });

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toaccount = new Intent(getContext(), Personal_Info.class);
                startActivity(toaccount);
            }
        });
        smile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Smile", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }


}
