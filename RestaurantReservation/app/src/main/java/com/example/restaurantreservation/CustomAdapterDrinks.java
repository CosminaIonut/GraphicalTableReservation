package com.example.restaurantreservation;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapterDrinks extends RecyclerView.Adapter<CustomAdapterDrinks.MyViewHolderDrinks> {

    Context context;
    ArrayList item_id,item_name, item_desc, item_price,item_sortiment,item_quantity;
    CustomAdapterDrinks(Context context, ArrayList item_id,
                  ArrayList item_name, ArrayList item_desc,
                  ArrayList item_price, ArrayList item_quantity,ArrayList item_sortiment){

        this.context=context;
        this.item_id=item_id;
        this.item_name=item_name;
        this.item_desc=item_desc;
        this.item_price=item_price;
        this.item_quantity=item_quantity;
        this.item_sortiment=item_sortiment;
    }
    @NonNull
    @Override
    public MyViewHolderDrinks onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view =inflater.inflate(R.layout.menu_item_row_drinks,parent,false);
        return new MyViewHolderDrinks(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderDrinks holder, int position) {
        holder.item_name.setText(String.valueOf(item_name.get(position)));
        holder.item_desc.setText(String.valueOf(item_desc.get(position)));
        holder.item_price.setText(String.valueOf(item_price.get(position)));
        holder.item_quantity.setText(String.valueOf(item_quantity.get(position)));
    }


    @Override
    public int getItemCount(){
        return item_id.size();
    }

    public class MyViewHolderDrinks extends RecyclerView.ViewHolder{
        TextView item_id, item_name, item_desc, item_price, item_quantity;
        public MyViewHolderDrinks(@NonNull View itemView) {
            super(itemView);
            item_quantity=itemView.findViewById(R.id.item_qunantity);
            item_name=itemView.findViewById(R.id.itemName);
            item_desc=itemView.findViewById(R.id.item_desc);
            item_price=itemView.findViewById(R.id.item_price);
        }
    }

}
