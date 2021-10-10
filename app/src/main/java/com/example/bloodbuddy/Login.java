package com.example.bloodbuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Login extends Activity {

    private static final String TAG = "Login";

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

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


        mAuth = FirebaseAuth.getInstance();



        requestOtpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mobileno.getText().toString().trim().isEmpty()){
                    if(mobileno.getText().toString().trim().length() == 10){

                       requestOtpButton.setVisibility(View.INVISIBLE);
                       progressBar.setVisibility(View.VISIBLE);
                        String phoneNumber = mobileno.getText().toString().trim();

                        PhoneAuthProvider.getInstance().verifyPhoneNumber("+91" +phoneNumber, 60, TimeUnit.SECONDS, Login.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                requestOtpButton.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                requestOtpButton.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(Login.this,e.getMessage(),Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String otp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                requestOtpButton.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                                String phoneNumber = mobileno.getText().toString().trim();
                                Intent i = new Intent(Login.this,otp_verification.class);
                                i.putExtra("mobileno",phoneNumber);
                                Toast.makeText(Login.this,"Inside oncode sent",Toast.LENGTH_SHORT).show();
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