package com.example.ama;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ama.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class shipping_page extends AppCompatActivity {
    private EditText shipping_user,shipping_phone,shipping_address,shipping_city;
    private Button shipping_confirm_btn;
    private String Total_Amount;
//    private String sid;
    //private String pid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_shipping_page);

        //Total_Amount=getIntent ().getStringExtra ("Total Price");
        Total_Amount = getIntent().getExtras().get("Total Price: ").toString();
//        sid = getIntent().getExtras().get("SellerId").toString();
       // pid = getIntent().getExtras().get("pId").toString();

        shipping_user=findViewById (R.id.shipping_user_name);
        shipping_phone=findViewById (R.id.shipping_user_phone);
        shipping_address=findViewById (R.id.shipping_user_address);
        shipping_city=findViewById (R.id.shipping_user_cityName);
        shipping_confirm_btn=findViewById (R.id.shipping_confirm_btn);


        shipping_confirm_btn.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {
                check();
            }
        });




    }

    private void check() {
        if(TextUtils.isEmpty (shipping_user.getText ().toString ()))
        {
            Toast.makeText (this,"Provide your name please",Toast.LENGTH_SHORT).show ();
        }

        else if(TextUtils.isEmpty (shipping_address.getText ().toString ()))
        {
            Toast.makeText (this,"Provide your address please",Toast.LENGTH_SHORT).show ();
        }

       else if(TextUtils.isEmpty (shipping_phone.getText ().toString ()))
        {
            Toast.makeText (this,"Provide your phone please",Toast.LENGTH_SHORT).show ();
        }

       else if(TextUtils.isEmpty (shipping_city.getText ().toString ()))
        {
            Toast.makeText (this,"Provide your City please",Toast.LENGTH_SHORT).show ();
        }

        else
        {
            confirmOrder();
        }





    }

    private void confirmOrder() {

        String saveCurrentTime,saveCurrentDate;
        Calendar calendar=Calendar.getInstance ();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        final DatabaseReference OrderRef= FirebaseDatabase.getInstance ().getReference ().child ("Orders");




        final HashMap<String, Object> OrdersMap = new HashMap<> ();
        OrdersMap.put("Total_Bill", Total_Amount);
        OrdersMap.put("date", saveCurrentDate);
        OrdersMap.put("time", saveCurrentTime);
        OrdersMap.put("Name", shipping_user.getText ().toString ());
        OrdersMap.put("Phone", shipping_phone.getText ().toString ());
        OrdersMap.put("Address", shipping_address.getText ().toString ());
        OrdersMap.put("City", shipping_city.getText ().toString ());
        OrdersMap.put("Status", "Not shipped");
//        OrdersMap.put("SellerID", sid);
//        OrdersMap.put("ProductID", pid);



        OrderRef.child (Prevalent.currentOnlineUser.getPhone ()).updateChildren (OrdersMap).addOnCompleteListener (new OnCompleteListener<Void> ( ) {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful ()){
                    FirebaseDatabase.getInstance ().getReference ().child ("Cart List")
                            .child ("Buyer View")
                            .child (Prevalent.currentOnlineUser.getPhone ())
                            .removeValue ()
                            .addOnCompleteListener (new OnCompleteListener<Void> ( ) {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful ()){

                                        FirebaseDatabase.getInstance ().getReference ().child ("Cart List")
                                                .child ("Seller View")
                                                .child (Prevalent.currentOnlineUser.getPhone ()).child ("Products").child ("Order_Status").setValue ("Confirmed").
                                                addOnCompleteListener (new OnCompleteListener<Void> ( ) {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful ()) {
                                                            Toast.makeText (shipping_page.this, "Yours Order is done successfully", Toast.LENGTH_SHORT).show ( );
                                                            Intent intent = new Intent (shipping_page.this, UserpageActivity.class);
                                                            intent.addFlags (Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                            startActivity (intent);
                                                            finish ( );
                                                        }
                                                    }
                                                });
                                    }
                                }
                            });

                }

            }
        });


    }
}