package com.example.ama.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ama.R;

public class CratViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView product_name_cart,product_quantity_cart,product_price_cart;
    private ItemClickListner itemClickListner;

    public CratViewHolder(@NonNull View itemView) {
        super (itemView);
        product_name_cart=itemView.findViewById (R.id.product_name_cart);
        product_quantity_cart=itemView.findViewById (R.id.product_quantity_cart);
        product_price_cart=itemView.findViewById (R.id.product_price_cart);
    }

    @Override
    public void onClick(View view) {
        itemClickListner.onClick (view,getAdapterPosition (),false);
    }
    public void setItemClickListner(ItemClickListner itemClickListner){
        this.itemClickListner=itemClickListner;
    }
}
