package com.example.ama;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class sellerLogin extends AppCompatActivity {
    private EditText seller_login_email,seller_log_pass;
    private Button seller_login_btn;

    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_seller_login);

        seller_login_email=findViewById (R.id.S_loginEmail);
        seller_log_pass=findViewById (R.id.S_loginPass);
        seller_login_btn=findViewById (R.id.S_login_btn);

        mAuth=FirebaseAuth.getInstance ();
        loadingBar = new ProgressDialog(this);

        seller_login_btn.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {
                sellerlogin();
            }
        });
    }

    private void sellerlogin() {
        final String s_email = seller_login_email.getText().toString();
        final String s_pass = seller_log_pass.getText().toString();

        if(!s_email.equals ("")  && !s_pass.equals ("")){
            loadingBar.setTitle("Login Seller Account");
            loadingBar.setMessage("Please wait....");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            mAuth.signInWithEmailAndPassword (s_email,s_pass).addOnCompleteListener (sellerLogin.this, new OnCompleteListener<AuthResult> ( ) {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful ( )) {
                        Intent intent = new Intent (sellerLogin.this, SellerHome.class);
                        intent.addFlags (Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish ();

                    }

                }
            });




        }
        else{
            Toast.makeText(this, "Please fill up the form", Toast.LENGTH_SHORT).show();
        }

    }
}