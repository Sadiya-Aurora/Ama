package com.example.ama;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.ama.Model.Products;
import com.example.ama.Prevalent.Prevalent;
import com.example.ama.ViewHolder.productView;
import com.example.ama.ui.cart.CartFragment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.paperdb.Paper;

import static com.example.ama.Prevalent.Prevalent.*;
import static java.lang.System.load;

public class UserpageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DatabaseReference ProductsRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private String userType;

   

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userpage);

       //Intent intent=getIntent ();
       //Bundle bundle=intent.getExtras ();
       //if(bundle!=null){
        //    userType=getIntent ().getExtras ().get ("Admin").toString ();



        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");


        Paper.init(this);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent intent = new Intent(UserpageActivity.this, Cart_activity.class);
                    startActivity(intent);


            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Get profile user name on header
        View headerview = navigationView.getHeaderView (0);
        TextView nav_username=headerview.findViewById (R.id.nav_head_username);
        ImageView nav_head_imageView=headerview.findViewById (R.id.nav_head_imageView);

        nav_username.setText (currentOnlineUser.getName ());
        Picasso.get ().load (currentOnlineUser.getImage ()).placeholder (R.drawable.profile).into(nav_head_imageView);


        recyclerView = findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }
    @Override
    protected void onStart()
    {
        super.onStart();

        FirebaseRecyclerOptions<Products> options =
                new FirebaseRecyclerOptions.Builder<Products>()
                        .setQuery(ProductsRef.orderByChild ("ProductStatus")
                                .equalTo ("Approved"), Products.class)
                        .build();


        FirebaseRecyclerAdapter<Products, productView> adapter =
                new FirebaseRecyclerAdapter<Products, productView>(options) {

                    @Override
                    protected void onBindViewHolder(@NonNull productView holder, int position, @NonNull final Products model)
                    {
                        holder.txtProductName.setText(model.getPname());
                        holder.txtProductDescription.setText(model.getDescription());
                        holder.txtProductPrice.setText("Price = " + model.getPrice() + "$");
                        Picasso.get().load(model.getImage()).into(holder.imageView);

                        holder.itemView.setOnClickListener (new View.OnClickListener ( ) {
                            @Override
                            public void onClick(View view) {

                                // if(userType.equals ("Admin")){
                                //    Intent intent=new Intent (UserpageActivity.this,seller_maintain_product.class);
                                //   intent.putExtra ("pid",model.getPid ());
                                //  startActivity (intent);
                                //}
                                // else{
                                Intent intent=new Intent (UserpageActivity.this,product_details.class);
                                intent.putExtra ("pid",model.getPid ());
                                intent.putExtra ("SellerID",model.getSellerID ());
                                intent.putExtra ("SellerEmail",model.getSellerEmail ());
                                intent.putExtra ("SellerPhone",model.getSellerPhone ());
                                startActivity (intent);
                                //}
                         }
                        });

                    }

                    @NonNull
                    @Override
                    public productView onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                    {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.products_layout, parent, false);
                        productView holder = new productView(view);
                        return holder;
                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.userpage, menu);
        return true;


    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home)
        {

        }

        if (id == R.id.nav_cart)
        {

                Intent intent = new Intent(UserpageActivity.this, Cart_activity.class);
                startActivity(intent);
            
        }

        else if (id == R.id.nav_Search)
        {

                Intent intent = new Intent(UserpageActivity.this, Searchproduct.class);
                startActivity(intent);

        }
        else if (id == R.id.nav_categories)
        {

        }
        else if (id == R.id.nav_settings)
        {

                Intent intent = new Intent (UserpageActivity.this, Settings_user.class);
                startActivity (intent);

        }
        else if (id == R.id.nav_logout)
        {

               Paper.book ().destroy ();
                Intent intent = new Intent(UserpageActivity.this, MainActivity.class);
                intent.addFlags (Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish ();


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }
}