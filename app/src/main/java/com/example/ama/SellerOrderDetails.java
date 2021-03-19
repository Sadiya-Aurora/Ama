package com.example.ama;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ama.Model.Cart;
import com.example.ama.Prevalent.Prevalent;
import com.example.ama.ViewHolder.CratViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SellerOrderDetails extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private DatabaseReference cartListRef;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_seller_order_details);

        recyclerView=findViewById (R.id.seller_prolist_details);
        recyclerView.setHasFixedSize (true);
        layoutManager=new LinearLayoutManager (this);
        recyclerView.setLayoutManager (layoutManager);
        userId=getIntent ().getStringExtra ("UID");

        cartListRef= FirebaseDatabase.getInstance ().getReference ().child ("Cart List").child ("Seller View").child (userId).child ("Products");

    }



    protected void onStart() {
        super.onStart ( );

        FirebaseRecyclerOptions<Cart> options=new FirebaseRecyclerOptions.Builder<Cart> ().setQuery (cartListRef,Cart.class).build ();

        FirebaseRecyclerAdapter<Cart, CratViewHolder> adapter=new FirebaseRecyclerAdapter<Cart, CratViewHolder> (options) {

            @NonNull
            @Override
            public CratViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from (parent.getContext ()).inflate (R.layout.cart_item_layout,parent,false);
                CratViewHolder holder=new CratViewHolder (view);
                return  holder;
            }

            @Override
            protected void onBindViewHolder(@NonNull CratViewHolder holder, int position, @NonNull Cart model) {
                holder.product_name_cart.setText (model.getPname ());
                holder.product_price_cart.setText ("Price: " +model.getPrice () + " BDT");
                holder.product_quantity_cart.setText ("Quantity: " + model.getQuantity ());


            }
        };

        recyclerView.setAdapter (adapter);
        adapter.startListening ();

    }
}