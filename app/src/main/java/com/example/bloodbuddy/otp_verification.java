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

    EditText otp;
    TextView resendotp = findViewById(R.id.resendotp);
    final Button verifyOtp =  findViewById(R.id.idverifyotpbutton);;
   final ProgressBar progressBar = findViewById(R.id.idprogressbar_otp);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);
        otp = findViewById(R.id.idotp);


        String firebaseotp = getIntent().getStringExtra("otp");
        verifyOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String OTP = otp.getText().toString().trim();
                if (!(otp.getText().toString().trim().isEmpty())) {


                    if(firebaseotp != null){
                        progressBar.setVisibility(view.VISIBLE);
                        verifyOtp.setVisibility(view.INVISIBLE);
                        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(firebaseotp,OTP);
                        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override

                            public void onComplete(@NonNull Task<AuthResult> task) {
                           progressBar.setVisibility(view.GONE);
                           verifyOtp.setVisibility(view.VISIBLE);

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
                        Toast.makeText(otp_verification.this,"Ehter correct OTP",Toast.LENGTH_SHORT).show();

                    }


                } else {
                    Toast.makeText(otp_verification.this,"OTP verified Successfully",Toast.LENGTH_SHORT);
                }
            }
        });

        resendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}