package com.example.erpnext.adapters.viewHolders;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.callbacks.BCCCallback;


public class BCCViewHolder extends RecyclerView.ViewHolder {

    public TextView email, required;
    public ImageView cross;
    public View parent;
    Context context;

    public BCCViewHolder(@NonNull View itemView) {
        super(itemView);
        parent = itemView;
        email = itemView.findViewById(R.id.email);
        cross = itemView.findViewById(R.id.cross);


    }


    public void setData(Context context, String item, BCCViewHolder holder, int position, BCCCallback callback) {
        email.setText(item);
        cross.setOnClickListener(view ->
                callback.onDeleteBCC(item, holder, position));
    }
}
