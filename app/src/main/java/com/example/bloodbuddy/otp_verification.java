package com.example.bloodbuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class otp_verification extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);
       EditText otp = findViewById(R.id.idotp);

        final Button verifyOtp =  findViewById(R.id.idverifyotpbutton);;
        final ProgressBar progressBar = findViewById(R.id.idprogressbar_otp);

        String phoneno = getIntent().getStringExtra("mobileno");
        verifyOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String OTP = otp.getText().toString().trim();
                if (!(otp.getText().toString().trim().isEmpty())) {

                    String firebaseotp = getIntent().getStringExtra("otp");

                    if(firebaseotp != null){
                        progressBar.setVisibility(View.VISIBLE);
                        verifyOtp.setVisibility(View.INVISIBLE);
                        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(firebaseotp,OTP);
                        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override

                            public void onComplete(@NonNull Task<AuthResult> task) {
                           progressBar.setVisibility(View.GONE);
                           verifyOtp.setVisibility(View.VISIBLE);

                           if(task.isSuccessful()){
                               Intent i = new Intent(getApplicationContext(),MainActivity.class);
                               i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                               startActivity(i);
                           }
                           else{
                               Toast.makeText(otp_verification.this,"Please enter the correct OTP",Toast.LENGTH_SHORT).show();
                           }
                            }
                        });
                    }
                    else{
                        Toast.makeText(otp_verification.this,"Enter correct OTP",Toast.LENGTH_SHORT).show();

                    }


                } else {
                    Toast.makeText(otp_verification.this,"Enter OTP Successfully",Toast.LENGTH_SHORT).show();
                }
            }
        });



    }






}