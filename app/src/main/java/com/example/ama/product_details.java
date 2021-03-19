package com.example.ama;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.ama.Model.Products;
import com.example.ama.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.security.PrivateKey;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class product_details extends AppCompatActivity {
    private TextView product_name_details,product_description_details,product_price_details;
    private ImageView product_image_details;
    private ElegantNumberButton elegantNumberButton;
    private Button add_to_cart;
    private String productID="";
    private String sellerID,sellerEmail,sellerPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_product_details);

        product_name_details= findViewById (R.id.product_name_details);
        product_description_details= findViewById (R.id.product_description_details);
        product_price_details= findViewById (R.id.product_price_details);
        product_image_details= findViewById (R.id.product_image_details);
        elegantNumberButton=findViewById (R.id.elegant_btn);

        productID=getIntent ().getStringExtra ("pid");
        sellerID=getIntent ().getStringExtra ("SellerID");
        sellerEmail=getIntent ().getStringExtra ("SellerEmail");
        sellerPhone=getIntent ().getStringExtra ("SellerPhone");

        add_to_cart=findViewById (R.id.add_cart);

        getProductDetals(productID);

add_to_cart.setOnClickListener (new View.OnClickListener ( ) {
    @Override
    public void onClick(View view) {
        adding_cart_list();
    }
});


    }

    private void adding_cart_list() {
        String saveCurrentTime,saveCurrentDate;

        Calendar calendar=Calendar.getInstance ();


        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

       final DatabaseReference cartRef= FirebaseDatabase.getInstance ().getReference ().child ("Cart List");

      final  HashMap<String, Object> CartMap = new HashMap<> ();
        CartMap.put("pid", productID);
        CartMap.put("SellerID", sellerID);
        CartMap.put("SellerEmail", sellerEmail);
        CartMap.put("SellerPhone", sellerPhone);
        CartMap.put("date", saveCurrentDate);
        CartMap.put("time", saveCurrentTime);
        CartMap.put("pname", product_name_details.getText ().toString ());
        CartMap.put("price", product_price_details.getText ().toString ());
        CartMap.put("Quantity",elegantNumberButton.getNumber ());

        cartRef.child ("Buyer View").child (Prevalent.currentOnlineUser.getPhone ()).child ("Products").child (productID).
                updateChildren (CartMap).addOnCompleteListener (new OnCompleteListener<Void> ( ) {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful ()){
                    cartRef.child ("Seller View").child (Prevalent.currentOnlineUser.getPhone ()).child ("Products").child (productID).
                            updateChildren (CartMap).addOnCompleteListener (new OnCompleteListener<Void> ( ) {
                        @Override
                       public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful ()){
                                Toast.makeText(product_details.this, "Added to cart..", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent (product_details.this,UserpageActivity.class);
                                startActivity (intent);
                            }
                        }
                    });
               }
          }
        });


    }

    private void getProductDetals(String productID) {
        DatabaseReference productsRef= FirebaseDatabase.getInstance ().getReference ().child ("Products");
        productsRef.child (productID).addValueEventListener (new ValueEventListener ( ) {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
              if (snapshot.exists ()){
                  Products products=snapshot.getValue (Products.class);
                  product_name_details.setText (products.getPname ());
                  product_description_details.setText (products.getDescription ());
                  product_price_details.setText (products.getPrice ());
                  Picasso.get ().load (products.getImage ()).into ( product_image_details);
              }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}