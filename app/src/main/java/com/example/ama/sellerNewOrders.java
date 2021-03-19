package com.example.ama;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ama.Model.Cart;
import com.example.ama.Model.admin_orders;
import com.example.ama.Prevalent.Prevalent;
import com.example.ama.ViewHolder.Admin_OrdersViewHolder;
import com.example.ama.ViewHolder.CratViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class sellerNewOrders extends AppCompatActivity {

    private DatabaseReference OrderListRef;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_seller_new_orders);

        recyclerView=findViewById (R.id.seller_ordered_lists);
        recyclerView.setHasFixedSize (true);
        layoutManager=new LinearLayoutManager (this);
        recyclerView.setLayoutManager (layoutManager);

        OrderListRef= FirebaseDatabase.getInstance ().getReference ().child ("Orders");
    }

    protected void onStart() {
        super.onStart ( );
        FirebaseRecyclerOptions<admin_orders> options=new FirebaseRecyclerOptions.Builder<admin_orders> ().setQuery (OrderListRef,admin_orders.class).build ();

        FirebaseRecyclerAdapter<admin_orders, Admin_OrdersViewHolder>adapter=new FirebaseRecyclerAdapter<admin_orders, Admin_OrdersViewHolder> (options) {
            @Override
            protected void onBindViewHolder(@NonNull Admin_OrdersViewHolder holder, final int position, @NonNull admin_orders model) {
                holder.order_name.setText (model.getName ());
                holder.o_phone.setText (model.getPhone ());
                holder.o_address.setText ("Address:"+model.getAddress ());
                holder.o_date.setText ("Date & Time:"+model.getDate ()+model.getTime ());
                holder.o_bill.setText ("Bill:"+model.getTotal_Bill ());
                holder.status.setText (model.getStatus ());

                holder.order_show_btn.setOnClickListener (new View.OnClickListener ( ) {
                    @Override
                    public void onClick(View view) {
                        String Uid=getRef (position).getKey ();
                        Intent intent=new Intent (sellerNewOrders.this,SellerOrderDetails.class);
                        intent.putExtra ("UID",Uid);
                        startActivity (intent);
                    }
                });

            }

            @NonNull
            @Override
            public Admin_OrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from (parent.getContext ()).inflate (R.layout.orders_layout,parent,false);
                Admin_OrdersViewHolder holder=new Admin_OrdersViewHolder (view);
                return  holder;
            }
        };
        recyclerView.setAdapter (adapter);
        adapter.startListening ();

    }


    }