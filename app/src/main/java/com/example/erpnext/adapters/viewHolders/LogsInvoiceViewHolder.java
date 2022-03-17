package com.example.erpnext.adapters.viewHolders;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.network.serializers.requestbody.CompleteOrderRequestBody;

public class LogsInvoiceViewHolder extends RecyclerView.ViewHolder{
    public TextView name, name2, daysAgo, noOfLikes, noOfReply, status, grandTotal;

    public View parent;

    public LogsInvoiceViewHolder(@NonNull View itemView) {
        super(itemView);
        parent = itemView;
        name = itemView.findViewById(R.id.name);
        name2 = itemView.findViewById(R.id.name2);
        daysAgo = itemView.findViewById(R.id.daysAgo);
        noOfLikes = itemView.findViewById(R.id.noOfLikes);
        noOfReply = itemView.findViewById(R.id.noOfReplys);
        grandTotal = itemView.findViewById(R.id.total);
        status = itemView.findViewById(R.id.enabled);
    }

    public void setData(Context context, CompleteOrderRequestBody order){
//        name.setText(order.getName());
        noOfReply.setVisibility(View.GONE);
        status.setVisibility(View.GONE);
        name2.setText(context.getString(R.string.customer)+": "+order.getCustomer());
        daysAgo.setText(order.getPostingDate());
    }
}
