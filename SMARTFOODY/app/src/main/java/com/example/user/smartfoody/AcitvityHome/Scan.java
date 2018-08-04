package com.example.user.smartfoody.AcitvityHome;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.user.smartfoody.View.ViewVideo;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

/**
 * Created by User on 26/02/2018.
 */

public class Scan extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Activity activity = this;
        IntentIntegrator integrator = new IntentIntegrator(activity);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("Please scan QR code to view more");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(false);
        integrator.initiateScan();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null)
        {
            if (result.getContents() == null)
            {
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
            }
            else {
                String value = result.getContents();
                Toast.makeText(this, value, Toast.LENGTH_SHORT).show();
                Intent toview = new Intent(Scan.this, ViewVideo.class);
                //toview.putExtra("value", value);
                startActivity(toview);
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }
}
