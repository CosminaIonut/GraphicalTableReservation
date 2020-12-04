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

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private List<RestaurantCard> mData;

    public RecyclerViewAdapter(Context myContext, List<RestaurantCard> mData) {
        this.mContext = myContext;
        this.mData = mData;
    }

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

        holder.tv_restaurant_title.setText(mData.get(position).getTitle());
        holder.iv_restaurant_image.setImageResource(mData.get(position).getThumbnail());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv_restaurant_title;
        ImageView iv_restaurant_image;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_restaurant_title=(TextView) itemView.findViewById(R.id.restaurant_title_id);
            iv_restaurant_image=(ImageView) itemView.findViewById(R.id.restaurant_iamge_id);
            cardView=(CardView)itemView.findViewById(R.id.cardViewId);
        }
    }
}
