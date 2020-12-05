package com.example.restaurantreservation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    public RecyclerViewAdapter(Context mContext, ArrayList rest_id, ArrayList rest_name, ArrayList rest_adress, ArrayList rest_thumbnail) {
        this.mContext = mContext;
        this.rest_id = rest_id;
        this.rest_name = rest_name;
        this.rest_adress = rest_adress;
        this.rest_thumbnail=rest_thumbnail;
    }

    private Context mContext;
    ArrayList rest_id,rest_name,rest_adress, rest_thumbnail;
    private List<RestaurantCard> mData;

//    public RecyclerViewAdapter(Context myContext, List<RestaurantCard> mData) {
//        this.mContext = myContext;
//        this.mData = mData;
//    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater= LayoutInflater.from(mContext);
        view=layoutInflater.inflate(R.layout.cardview_item_restaurant,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        holder.rest_name.setText(String.valueOf(rest_name.get(position)));
        holder.iv_restaurant_image.setImageResource((int) rest_thumbnail.get(position));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return rest_id.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv_restaurant_title,rest_id,rest_name,rest_adress;
        ImageView iv_restaurant_image;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            rest_name=itemView.findViewById(R.id.restaurant_title_id);
            rest_adress=itemView.findViewById(R.id.restaurant_adress_id);

            //tv_restaurant_title=(TextView) itemView.findViewById(R.id.restaurant_title_id);
            iv_restaurant_image=(ImageView) itemView.findViewById(R.id.restaurant_iamge_id);
            cardView=(CardView)itemView.findViewById(R.id.cardViewId);
        }
    }
}
