package com.example.ama;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ama.Model.Products;
import com.example.ama.ViewHolder.productView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SellerHome extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private RecyclerView seller_all_pro_list;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference all_proRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_seller_home);
        BottomNavigationView navView = findViewById (R.id.nav_view);
        navView.setOnNavigationItemSelectedListener (this);

        all_proRef= FirebaseDatabase.getInstance().getReference().child("Products");
       seller_all_pro_list = findViewById(R.id.all_pro_lists);
        seller_all_pro_list.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager (this);
        seller_all_pro_list.setLayoutManager(layoutManager);




    }
    @Override
    protected void onStart()
    {
        super.onStart();

        FirebaseRecyclerOptions<Products> options =
                new FirebaseRecyclerOptions.Builder<Products>()
                        .setQuery(all_proRef.orderByChild ("SellerID")
                                .equalTo (FirebaseAuth.getInstance ().getCurrentUser ().getUid ()), Products.class)
                        .build();


        FirebaseRecyclerAdapter<Products, productView> adapter =
                new FirebaseRecyclerAdapter<Products, productView>(options) {

                    @Override
                    protected void onBindViewHolder(@NonNull productView holder, final int position, @NonNull final Products model)
                    {
                        holder.txtProductName.setText(model.getPname());
                        holder.txtProductDescription.setText(model.getDescription());
                        holder.txtProductPrice.setText("Price = " + model.getPrice() + "$");
                        holder.txtproductStatus.setText(model.getProductStatus ());
                        Picasso.get().load(model.getImage()).into(holder.imageView);



                        holder.itemView.setOnClickListener (new View.OnClickListener ( ) {
                            @Override
                            public void onClick(final View view) {
                                final String productID=model.getPid ();
                                CharSequence options[] = new CharSequence[]
                                        {
                                                "Edit",
                                                "Delete"
                                        };
                                final AlertDialog.Builder builder=new AlertDialog.Builder (SellerHome.this);
                                builder.setTitle ("You may modify or delete the product");
                                builder.setItems (options, new DialogInterface.OnClickListener ( ) {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if(i==0)
                                        {
                                            Intent intent=new Intent (SellerHome.this, seller_maintain_product.class);
                                            intent.putExtra ("pid",model.getPid ());
                                            startActivity (intent);

                                        }
                                        if(i==1)
                                        {
                                        Delete(productID);    

                                    };
                                        }
                                });
                                builder.show ();
                            }
                        });

                    }

                    @NonNull
                    @Override
                    public productView onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                    {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.seller_item_view, parent, false);
                        productView holder = new productView(view);
                        return holder;
                    }
                };
        seller_all_pro_list.setAdapter(adapter);
        adapter.startListening();
    }

    private void Delete(String productID) {
        all_proRef.child (productID).removeValue ().addOnCompleteListener (new OnCompleteListener<Void> ( ) {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText (SellerHome.this, "Product info deleted successfully...", Toast.LENGTH_SHORT).show ( );
                Intent intent = new Intent(SellerHome.this, SellerHome.class);
                startActivity(intent);
                finish ( );
            }
        });
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.navigation_home)
        {
            return true;
        }
       else if (id == R.id.navigation_dashboard)
        {
            Intent intent = new Intent(SellerHome.this, Seller_category_activity.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.navigation_Order)
        {
            Intent intent = new Intent(SellerHome.this, sellerNewOrders.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.navigation_notifications)
        {
            final FirebaseAuth mAuth;
            mAuth=FirebaseAuth.getInstance ();
            mAuth.signOut ();
            Toast.makeText(SellerHome.this, "Logging Out...", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SellerHome.this, MainActivity.class);
            intent.addFlags (Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish ();
            return true;


        }
        return false;
    }








}