package com.example.erpnext.adapters.viewHolders;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.callbacks.CCCallback;


public class CCViewHolder extends RecyclerView.ViewHolder {

    public TextView email, required;
    public ImageView cross;
    public View parent;
    Context context;

    public CCViewHolder(@NonNull View itemView) {
        super(itemView);
        parent = itemView;
        email = itemView.findViewById(R.id.email);
        cross = itemView.findViewById(R.id.cross);


    }


    public void setData(Context context, String item, CCViewHolder holder, int position, CCCallback callback) {
        email.setText(item);
        cross.setOnClickListener(view ->
                callback.onDeleteCC(item, holder, position));
    }
}
