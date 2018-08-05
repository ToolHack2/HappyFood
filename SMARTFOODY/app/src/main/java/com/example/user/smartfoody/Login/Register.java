package com.example.user.smartfoody.Login;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.smartfoody.CartDB.Database;
import com.example.user.smartfoody.MainActivity;
import com.example.user.smartfoody.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Register extends AppCompatActivity {

    private static final String TAG = "Message";
    // firebase
    private String phoneverifyId;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks verificationcallback;
    private PhoneAuthProvider.ForceResendingToken resendingToken;
    private FirebaseAuth auth;

    EditText firstname, lastname,regisuser,regispass, regisconfirmpass, otpcode;
    Button register2,checked;
    LinearLayout checklayout, regislayout;

    String message;
    private static final String registerurl = "https://nasu120696.000webhostapp.com/androidwebservice/register.php";




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);


        //declare
        firstname = (EditText)findViewById(R.id.edtfirstname);
        lastname = (EditText)findViewById(R.id.edtlastname);
        regisuser = (EditText)findViewById(R.id.edtphone);
        regispass = (EditText)findViewById(R.id.edtregispass);
        regisconfirmpass = (EditText)findViewById(R.id.edtconfirmpass);
        otpcode = (EditText)findViewById(R.id.edtcode);
        //button
        register2 = (Button)findViewById(R.id.btnregister2);
        checked = (Button)findViewById(R.id.btnchecked);
        //layout
        checklayout = (LinearLayout)findViewById(R.id.layoutcheck);
        regislayout = (LinearLayout)findViewById(R.id.layoutregis);

        //
        auth = FirebaseAuth.getInstance();

        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
//        mUser.getIdToken(true)
//                .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
//                    public void onComplete(@NonNull Task<GetTokenResult> task) {
//                        if (task.isSuccessful()) {
//                            String idToken = task.getResult().getToken();
//                            // Send token to your backend via HTTPS
//                            // ...
//                        } else {
//                            // Handle error -> task.getException();
//                        }
//                    }
//                });


        //
        final Animation slide_down = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_down);
        //control
        register2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendCode(view);

                String pass = regispass.getText().toString();
                if (regisconfirmpass.getText().toString().equals(pass))
                {
                    regislayout.setVisibility(View.GONE);
                    checklayout.setVisibility(View.VISIBLE);
                    checklayout.startAnimation(slide_down);

                }
                else 
                {
                    regisconfirmpass.setHighlightColor(Color.RED);
                    Toast.makeText(Register.this, "Mật khẩu không trùng khớp!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        checked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyCode(view);
            }
        });

    }

    public void Registrasion()
    {
        StringRequest request = new StringRequest(StringRequest.Method.POST, registerurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Register.this, response, Toast.LENGTH_SHORT).show();
                        try
                        {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            String status = jsonObject.getString("status");

                            if (status.equals("ok"))
                            {
                                Toast.makeText(Register.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                                Intent tomain = new Intent(Register.this, LogIn.class);
                                startActivity(tomain);
                            }
                            else {
                                Toast.makeText(Register.this, "Tài khoản đã tồn tại!", Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                
                params.put("first_name", firstname.getText().toString());
                params.put("last_name", lastname.getText().toString());
                params.put("phone", regisuser.getText().toString());
                params.put("password", regispass.getText().toString());
                return params;
            }
        };
        Volley.newRequestQueue(this).add(request);
    }

    // send sms code
    public void SendCode(View view)
    {
        String phone_number = "+84"+regisuser.getText().toString();

        setUpVerificationCallback();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone_number,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                verificationcallback);        // OnVerificationStateChangedCallbacks

    }

    private void setUpVerificationCallback() {
        verificationcallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                checked.setEnabled(true);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

                if (e instanceof FirebaseAuthInvalidCredentialsException)
                {
                    Log.d(TAG,"Invalid Credential: " + e.getLocalizedMessage());
                }
                else if (e instanceof FirebaseTooManyRequestsException)
                {
                    Log.d(TAG,"SMS exceeded: " + e);
                }
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                phoneverifyId = s;
                resendingToken = forceResendingToken;
            }
        };
    }

    public void verifyCode(View view)
    {
        String code = otpcode.getText().toString();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(phoneverifyId, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful())
                        {
                            Log.d(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            Toast.makeText(Register.this, "Xác nhận thành công!", Toast.LENGTH_SHORT).show();
                            Registrasion();
                        }
                        else {
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {

                            }
                        }
                    }
                });
    }

}
