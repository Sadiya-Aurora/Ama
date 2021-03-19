package com.example.ama;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class seller_maintain_product extends AppCompatActivity {
    private Button apply_changes_btn,delete_btn;
    private EditText maint_productname,maint_productDes,maint_productPrice;
    private ImageView maint_productImage;

    private String productID="";
    private DatabaseReference productRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_seller_maintain_product);

        apply_changes_btn=findViewById (R.id.apply_changes_btn);
        delete_btn=findViewById (R.id.delete_btn);
        maint_productname=findViewById (R.id.maint_product_name);
        maint_productDes=findViewById (R.id.maint_product_description);
        maint_productPrice=findViewById (R.id.maint_product_price);
        maint_productImage=findViewById (R.id.maint_product_image);


        productID=getIntent ().getStringExtra ("pid");
        productRef= FirebaseDatabase.getInstance ().getReference ().child ("Products").child (productID);


        displayProductInfo();


        apply_changes_btn.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {
                applychanges();
            }
        });

        delete_btn.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {
                deleteProductInfo();
            }
        });

    }

    private void deleteProductInfo() {
        productRef.removeValue ().addOnCompleteListener (new OnCompleteListener<Void> ( ) {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(seller_maintain_product.this, "Product info deleted successfully...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(seller_maintain_product.this, SellerHome.class);
                startActivity(intent);
                finish ();
            }
        });
    }

    private void applychanges() {

        String Pname=maint_productname.getText ().toString ();
        String Pdes=maint_productDes.getText ().toString ();
        String pPrice=maint_productPrice.getText ().toString ();


        if(Pname.equals ("")){
            Toast.makeText(this, "Write product name...", Toast.LENGTH_SHORT).show();
        }
        else if(Pdes.equals ("")){
            Toast.makeText(this, "Write product description...", Toast.LENGTH_SHORT).show();
        }
        else if(pPrice.equals ("")){
            Toast.makeText(this, "Write product Price...", Toast.LENGTH_SHORT).show();
        }
        else {
            HashMap<String, Object> productMap = new HashMap<> ();
            productMap.put("pid", productID);
            productMap.put("description", Pdes);
            productMap.put("price", pPrice);
            productMap.put("pname", Pname);

            productRef.updateChildren (productMap).addOnCompleteListener (new OnCompleteListener<Void> ( ) {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if (task.isSuccessful())
                    {
                        Toast.makeText(seller_maintain_product.this, "Data updated successfully..", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(seller_maintain_product.this, SellerHome.class);
                        startActivity(intent);
                        finish ();

                    }
                }
            });
        }


    }

    private void displayProductInfo() {

        productRef.addValueEventListener (new ValueEventListener ( ) {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists ()){
                    String productname=snapshot.child ("pname").getValue ().toString ();
                    String productDes=snapshot.child ("description").getValue ().toString ();
                    String productprice=snapshot.child ("price").getValue ().toString ();
                    String productimage=snapshot.child ("image").getValue ().toString ();

                    maint_productname.setText (productname);
                    maint_productDes.setText (productDes);
                    maint_productPrice.setText (productprice);
                    Picasso.get ().load (productimage).into (maint_productImage);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}