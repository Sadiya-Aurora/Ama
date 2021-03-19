package com.example.ama;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class admin_home extends AppCompatActivity {
     private Button check_orders_btn,admin_logout_btn,maintain_products_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_admin_home);


        check_orders_btn=findViewById (R.id.check_new_pro);
        admin_logout_btn=findViewById (R.id.admin_logout_btn);
       // maintain_products_btn=findViewById (R.id.maintain_products_btn);


        admin_logout_btn.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (admin_home.this, MainActivity.class);
                intent.addFlags (Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish ();
            }
        });

        check_orders_btn.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (admin_home.this, admin_check_orders.class);
                startActivity(intent);
            }
        });


    }

}