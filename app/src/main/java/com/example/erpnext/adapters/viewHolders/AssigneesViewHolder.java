package com.example.erpnext.adapters.viewHolders;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.callbacks.AssigneeCallback;
import com.example.erpnext.models.Assignment;


public class AssigneesViewHolder extends RecyclerView.ViewHolder {

    public TextView email, required;
    public ImageView cross;
    public View parent;
    Context context;

    public AssigneesViewHolder(@NonNull View itemView) {
        super(itemView);
        parent = itemView;
        email = itemView.findViewById(R.id.email);
        cross = itemView.findViewById(R.id.cross);


    }

    public void setData(Context context, Assignment item, AssigneesViewHolder holder, int position, AssigneeCallback callback) {
        email.setText(item.getOwner());
        cross.setOnClickListener(view -> callback.onDeleteAssigneeClick(item, holder, position));

    }
}
