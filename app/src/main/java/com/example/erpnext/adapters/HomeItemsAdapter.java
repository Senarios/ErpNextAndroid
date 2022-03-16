package com.example.erpnext.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.models.HomeItemsModel;

import java.util.ArrayList;

public class HomeItemsAdapter extends RecyclerView.Adapter<HomeItemsAdapter.ViewHolder> {
    private ArrayList<HomeItemsModel> homeItemsModelArrayList;
    private Context context;
    private OnItemClickListener mListener;

    public HomeItemsAdapter(ArrayList<HomeItemsModel> homeItemsModelArrayList, Context context) {
        this.homeItemsModelArrayList = homeItemsModelArrayList;
        this.context = context;
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_item_layout, parent, false);
        return new ViewHolder(view,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemImage.setImageResource(homeItemsModelArrayList.get(position).getImage());
        holder.itemName.setText(homeItemsModelArrayList.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return homeItemsModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;
        ImageView itemImage;
        public ViewHolder(@NonNull View itemView,OnItemClickListener listener) {
            super(itemView);
            itemName = itemView.findViewById(R.id.idTextView);
            itemImage = itemView.findViewById(R.id.idImageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
