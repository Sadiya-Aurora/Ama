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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ama.Model.Cart;
import com.example.ama.Prevalent.Prevalent;
import com.example.ama.ViewHolder.CratViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Cart_activity extends AppCompatActivity {
    private TextView totat_price,cart_status;
    private ImageView empty_cart;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private   Button next_process_btn;

    private  int Total_cost=0;

//    private String sid;
//    private String pid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_cart_activity);

        totat_price=findViewById (R.id.total_price);
        next_process_btn=findViewById (R.id.next_process_btn);
        cart_status=findViewById (R.id.cart_state_visiblity);
        empty_cart=findViewById (R.id.emptycart);

        recyclerView=findViewById (R.id.cart_lists);
        recyclerView.setHasFixedSize (true);
        layoutManager=new LinearLayoutManager (this);
        recyclerView.setLayoutManager (layoutManager);



    }

    @Override
   protected void onStart() {
        super.onStart ( );

        checkOrderState();


        final DatabaseReference cartListRef= FirebaseDatabase.getInstance ().getReference ().child ("Cart List");

        FirebaseRecyclerOptions<Cart> options=new FirebaseRecyclerOptions.Builder<Cart> ().setQuery (cartListRef.child ("Buyer View")
        .child (Prevalent.currentOnlineUser.getPhone ()).child ("Products"),Cart.class).build ();


        FirebaseRecyclerAdapter<Cart, CratViewHolder>adapter=new FirebaseRecyclerAdapter<Cart, CratViewHolder> (options) {
            @Override
            protected void onBindViewHolder(@NonNull CratViewHolder holder, int position, @NonNull final Cart model) {

//               sid=model.getSellerID ();
//                pid=model.getPid ();
                next_process_btn.setOnClickListener (new View.OnClickListener ( ) {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent (Cart_activity.this,shipping_page.class);
                        intent.putExtra ("Total Price: ",Total_cost);
                       // intent.putExtra ("SellerId",sid);
                       // intent.putExtra ("pId",pid);
                        startActivity (intent);
                        finish ();
                    }
                });

                holder.product_name_cart.setText (model.getPname ());
                holder.product_price_cart.setText ("Price: " +model.getPrice () + " BDT");
                holder.product_quantity_cart.setText ("Quantity: " + model.getQuantity ());

                int singleProductCost=(Integer.valueOf (model.getPrice ()))*(Integer.valueOf (model.getQuantity ()));
                Total_cost=Total_cost+singleProductCost;
                totat_price.setText ("Total price= "+ Total_cost);

                holder.itemView.setOnClickListener (new View.OnClickListener ( ) {
                    @Override
                    public void onClick(View view) {
                        CharSequence options[]=new CharSequence[]
                                {
                                        "Edit",
                                        "Remove"

                                };
                        AlertDialog.Builder builder= new AlertDialog.Builder (Cart_activity.this);
                        builder.setTitle ("Cart Options: ");
                        builder.setItems (options, new DialogInterface.OnClickListener ( ) {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(i==0)
                                {
                                    Intent intent = new Intent(Cart_activity.this, product_details.class);
                                    intent.putExtra ("pid",model.getPid ());
                                    startActivity(intent);
                                }
                                if(i==1){
                                    cartListRef.child ("Buyer View").child (Prevalent.currentOnlineUser.getPhone ()).
                                            child ("Products").child (model.getPid ()).
                                            removeValue ().addOnCompleteListener (new OnCompleteListener<Void> ( ) {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful ()){
                                                Toast.makeText (Cart_activity.this,"Items removed",Toast.LENGTH_SHORT).show ();
                                                Intent intent = new Intent(Cart_activity.this,Cart_activity.class);
                                                startActivity(intent);

                                            }
                                        }
                                    });

                                }

                            }
                        });

builder.show ();




                    }
                });


            }

            @NonNull
            @Override
            public CratViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view= LayoutInflater.from (parent.getContext ()).inflate (R.layout.cart_item_layout,parent,false);
                CratViewHolder holder=new CratViewHolder (view);
                return  holder;

          }
        };

        recyclerView.setAdapter (adapter);
        adapter.startListening ();


    }

    private void checkOrderState() {
        DatabaseReference ordersRef = FirebaseDatabase.getInstance ( ).getReference ( ).child ("Orders").child (Prevalent.currentOnlineUser.getPhone ( ));
                ordersRef.addValueEventListener (new ValueEventListener ( ) {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists ())
                        {
                            String shipped_state=snapshot.child ("Status").getValue ().toString ();

                            if(shipped_state.equals ("shipped")){
                                totat_price.setVisibility (View.GONE);
                                next_process_btn.setVisibility (View.GONE);
                                cart_status.setVisibility (View.VISIBLE);
                                empty_cart.setVisibility (View.VISIBLE);
                            }
                          else   if(shipped_state.equals ("Not shipped")){
                                totat_price.setVisibility (View.VISIBLE);
                                next_process_btn.setVisibility (View.VISIBLE);
                                cart_status.setVisibility (View.GONE);
                                empty_cart.setVisibility (View.GONE);
                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


    }


}