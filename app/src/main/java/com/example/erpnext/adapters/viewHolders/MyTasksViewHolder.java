package com.example.erpnext.adapters.viewHolders;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.callbacks.CCCallback;


public class MyTasksViewHolder extends RecyclerView.ViewHolder {

    public TextView email, required;
    public ImageView cross;
    public View parent;
    Context context;

    public MyTasksViewHolder(@NonNull View itemView) {
        super(itemView);
        parent = itemView;
        email = itemView.findViewById(R.id.email);
        cross = itemView.findViewById(R.id.cross);


    }


    public void setData(Context context, String item, MyTasksViewHolder holder, int position, CCCallback callback) {
        email.setText(item);
        cross.setOnClickListener(view -> {
//                    callback.onDeleteCC(item, holder, position)
                }
        );
        parent.setOnLongClickListener(v->{
            callback.onLongClick(item,position);
            return false;
        });
    }
}
