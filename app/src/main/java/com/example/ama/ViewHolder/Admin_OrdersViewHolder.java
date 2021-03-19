package com.example.ama.ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ama.R;

public class Admin_OrdersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private ItemClickListner itemClickListner;
    public TextView order_name,o_address,o_phone,o_date,o_bill,status;
   public Button order_show_btn;

    public Admin_OrdersViewHolder(@NonNull View itemView) {
        super (itemView);
        order_name=itemView.findViewById (R.id.orders_name);
        o_address=itemView.findViewById (R.id.orders_address_city);
        o_phone=itemView.findViewById (R.id.orders_phone);
        o_date=itemView.findViewById (R.id.orders_dateNtime);
        o_bill=itemView.findViewById (R.id.orders_total_bill);
        order_show_btn=itemView.findViewById (R.id.orders_show_product_btn);
       status=itemView.findViewById (R.id.shipping_status_label);

    }

    @Override
    public void onClick(View view) {
        itemClickListner.onClick (view,getAdapterPosition (),false);
    }
}
