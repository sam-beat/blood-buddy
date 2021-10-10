package com.example.bloodbuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Login extends AppCompatActivity {

    EditText mobileno;
    Button requestOtpButton;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mobileno = findViewById(R.id.idmobilenumber);
        requestOtpButton = findViewById(R.id.idrequestotpbutton);
        progressBar = findViewById(R.id.idprogressbar_mobile);
        requestOtpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mobileno.getText().toString().trim().isEmpty()){
                    if(mobileno.getText().toString().trim().length() == 10){

                       requestOtpButton.setVisibility(view.INVISIBLE);
                       progressBar.setVisibility(view.VISIBLE);

                        PhoneAuthProvider.getInstance().verifyPhoneNumber("+91" + mobileno.getText().toString().trim(), 60, TimeUnit.SECONDS, Login.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                requestOtpButton.setVisibility(view.VISIBLE);
                                progressBar.setVisibility(view.GONE);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                requestOtpButton.setVisibility(view.VISIBLE);
                                progressBar.setVisibility(view.GONE);
                                Toast.makeText(Login.this,e.getMessage(),Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String otp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                requestOtpButton.setVisibility(view.VISIBLE);
                                progressBar.setVisibility(view.GONE);
                                Intent i = new Intent(Login.this,otp_verification.class);
                                i.putExtra("mobileno",mobileno.getText().toString().trim());
                                i.putExtra("otp",otp);
                                startActivity(i);
                            }
                        });


                    }
                    else{
                        Toast.makeText(Login.this,"Please enter a valid mobile number",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(Login.this,"Enter mobile number",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}