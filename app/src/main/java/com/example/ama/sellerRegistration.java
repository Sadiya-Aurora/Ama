package com.example.ama;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class sellerRegistration extends AppCompatActivity {
    private   EditText shop_name,seller_phon,seller_email,shop_address,seller_pass;
    private Button seller_Registration_btn;
    private TextView s_already_account;

    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_seller_registration);

        shop_name=findViewById (R.id.shop_name);
        seller_phon=findViewById (R.id.seller_phone);
        seller_email=findViewById (R.id.seller_email);
        shop_address=findViewById (R.id.seller_address);
        seller_pass=findViewById (R.id.seller_password);
        seller_Registration_btn=findViewById (R.id.seller_regi_btn);
        s_already_account=findViewById (R.id.seller_already_account);

        mAuth=FirebaseAuth.getInstance ();
        loadingBar = new ProgressDialog(this);

        s_already_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(sellerRegistration.this, sellerLogin.class);
                startActivity(intent);
            }
        });

        seller_Registration_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
               RegisterSeller();
            }
        });



    }


    private void RegisterSeller() {
            final String s_name = shop_name.getText().toString();
            final String s_phone = seller_phon.getText().toString();
            final String s_address = shop_address.getText().toString();
            final String email = seller_email.getText().toString();
            final String password = seller_pass.getText().toString();


        if(!s_name.equals ("") && !s_phone.equals ("") && !s_address.equals ("") && !email.equals ("")  && !password.equals ("")){

            loadingBar.setTitle("Creating Seller Account");
            loadingBar.setMessage("Please wait....");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(sellerRegistration.this, new OnCompleteListener<AuthResult> () {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful ( )) {
                                final DatabaseReference rootRef;
                                rootRef= FirebaseDatabase.getInstance ().getReference ();

                                String sid=mAuth.getCurrentUser ().getUid ();
                                FirebaseUser user = mAuth.getCurrentUser();


                                HashMap<String, Object> sellerMap = new HashMap<> ( );
                                sellerMap.put ("sId", sid);
                                sellerMap.put ("Shop Name", s_name);
                                sellerMap.put ("Email", email);
                                sellerMap.put ("Phone", s_phone);
                                sellerMap.put ("Address", s_address);
                                sellerMap.put ("password", password);

                                rootRef.child ("Seller").child (sid).updateChildren (sellerMap).addOnCompleteListener (new OnCompleteListener<Void> ( ) {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        loadingBar.dismiss();
                                        Toast.makeText(sellerRegistration.this, "You are registered Successfully", Toast.LENGTH_SHORT).show();
                                        finish ();

                                    }
                                });

                            } else {
                                loadingBar.dismiss();
                                Toast.makeText(sellerRegistration.this, "Network Error Or Invalid Data,Please try again...", Toast.LENGTH_SHORT).show();

                            }

                        }
                    });
        }
        else{
            Toast.makeText(this, "Please complete the Registration", Toast.LENGTH_SHORT).show();
        }




    }
    }

