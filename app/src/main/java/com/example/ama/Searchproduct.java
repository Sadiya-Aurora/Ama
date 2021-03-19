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
import android.widget.EditText;

import com.example.ama.Model.Products;
import com.example.ama.ViewHolder.productView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Searchproduct extends AppCompatActivity {
    private Button serach_go_btn;
    private EditText search_field;
    private  RecyclerView searchlists;
    private RecyclerView.LayoutManager layoutManager;
    private String searchInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_searchproduct);

        serach_go_btn=findViewById (R.id.search_go_btn);
        search_field=findViewById (R.id.search_product_field);
        searchlists=findViewById (R.id.search_lists);
        layoutManager=new LinearLayoutManager (this);
        searchlists.setLayoutManager (layoutManager);


        serach_go_btn.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {
                searchInput=search_field.getText ().toString ();
                go_search();
            }
        });

    }

    private void go_search() {
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("Products");

        FirebaseRecyclerOptions<Products> options =
                new FirebaseRecyclerOptions.Builder<Products>()
                        .setQuery(reference.orderByChild ("pname").startAt (searchInput).endAt (searchInput+"\uf8ff"), Products.class)
                        .build();

        FirebaseRecyclerAdapter<Products, productView> adapter =
                new FirebaseRecyclerAdapter<Products, productView>(options) {

                    @NonNull
                    @Override
                    public productView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.products_layout, parent, false);
                        productView holder = new productView(view);
                        return holder;
                    }

                    @Override
                    protected void onBindViewHolder(@NonNull productView holder, int position, @NonNull final Products model) {
                        holder.txtProductName.setText(model.getPname());
                        holder.txtProductDescription.setText(model.getDescription());
                        holder.txtProductPrice.setText("Price = " + model.getPrice() + "$");
                        Picasso.get().load(model.getImage()).into(holder.imageView);

                        holder.itemView.setOnClickListener (new View.OnClickListener ( ) {
                            @Override
                            public void onClick(View view) {
                                Intent intent=new Intent (Searchproduct.this,product_details.class);
                                intent.putExtra ("pid",model.getPid ());
                                startActivity (intent);
                            }
                        });

                    }
                };
        searchlists.setAdapter(adapter);
        adapter.startListening();
                }


    }
