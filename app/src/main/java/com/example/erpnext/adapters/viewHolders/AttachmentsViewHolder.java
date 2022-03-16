package com.example.erpnext.adapters.viewHolders;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.callbacks.AssigneeCallback;
import com.example.erpnext.models.Attachment;


public class AttachmentsViewHolder extends RecyclerView.ViewHolder {

    public TextView email, required;
    public ImageView cross;
    public View parent;
    Context context;

    public AttachmentsViewHolder(@NonNull View itemView) {
        super(itemView);
        parent = itemView;
        email = itemView.findViewById(R.id.email);
        cross = itemView.findViewById(R.id.cross);


    }

    public void setData(Context context, Attachment item, AttachmentsViewHolder holder, int position, AssigneeCallback callback) {
        email.setText(item.getFileName());
        cross.setOnClickListener(view -> callback.onDeleteAttachmentClick(item, holder, position));
        parent.setOnClickListener(view -> callback.onAttachmentClick(item, holder, position));

    }
}
