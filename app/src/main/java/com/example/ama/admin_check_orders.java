package com.example.ama;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ama.Model.Products;
import com.example.ama.Model.admin_orders;
import com.example.ama.Prevalent.Prevalent;
import com.example.ama.ViewHolder.Admin_OrdersViewHolder;
import com.example.ama.ViewHolder.CratViewHolder;
import com.example.ama.ViewHolder.ItemClickListner;
import com.example.ama.ViewHolder.productView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class admin_check_orders extends AppCompatActivity {

    private RecyclerView new_order_lists;
    private DatabaseReference UnverifiedRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_admin_check_orders);


        UnverifiedRef= FirebaseDatabase.getInstance ().getReference ().child ("Products");
        new_order_lists=findViewById (R.id.order_checking_lists);
        new_order_lists.setLayoutManager (new LinearLayoutManager (this));



    }
    @Override
    protected void onStart() {
        super.onStart ( );

        FirebaseRecyclerOptions<Products> options=new FirebaseRecyclerOptions.Builder<Products> ().setQuery (UnverifiedRef.orderByChild ("ProductStatus")
                .equalTo ("Not Approved"),Products.class).build ();

        FirebaseRecyclerAdapter<Products, productView>adapter=new FirebaseRecyclerAdapter<Products, productView> (options) {
            @Override
            protected void onBindViewHolder(@NonNull productView holder, int position, @NonNull final Products model) {
                holder.txtProductName.setText(model.getPname());
                holder.txtProductDescription.setText(model.getDescription());
                holder.txtProductPrice.setText("Price = " + model.getPrice() + "$");
                Picasso.get().load(model.getImage()).into(holder.imageView);



                holder.itemView.setOnClickListener (new View.OnClickListener ( ) {
                    @Override
                    public void onClick(View view) {
                        final String productID = model.getPid ( );
                        CharSequence options[] = new CharSequence[]
                                {
                                        "Yes",
                                        "No"
                                };
                        AlertDialog.Builder builder = new AlertDialog.Builder (admin_check_orders.this);
                        builder.setTitle ("Do You Want To Approve this Product?");
                        builder.setItems (options, new DialogInterface.OnClickListener ( ) {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(i==0){
                                    changedProduct_state(productID);
                                }
                                if(i==1){

                                }

                            }
                        });
                        builder.show ();
                    }
                });

            }

            @NonNull
            @Override
            public productView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.products_layout, parent, false);
                productView holder = new productView(view);
                return holder;
            }
        };
        new_order_lists.setAdapter(adapter);
        adapter.startListening();

    }

    private void changedProduct_state(String productID) {
        UnverifiedRef.child (productID).child ("ProductStatus")
                .setValue ("Approved").addOnCompleteListener (new OnCompleteListener<Void> ( ) {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(admin_check_orders.this, "Product is approved successfully..", Toast.LENGTH_SHORT).show();

            }
        });
    }

}