package com.example.erpnext.adapters.viewHolders;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.callbacks.ProfilesCallback;
import com.example.erpnext.utils.DateTime;

import java.util.Date;
import java.util.List;


public class PosProfileViewHolder extends RecyclerView.ViewHolder {

    public TextView name, name2, daysAgo, noOfLikes, noOfReply, status, grandTotal;

    public View parent;

    public PosProfileViewHolder(@NonNull View itemView) {
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

    public static int getDaysDifference(Date fromDate) {
        if (fromDate == null || new Date() == null)
            return 0;

        return (int) ((new Date().getTime() - fromDate.getTime()) / (1000 * 60 * 60 * 24));
    }

    public void setData(Context context, List<String> item, ProfilesCallback callback, String doctype, int position) {
        if (doctype.equalsIgnoreCase("POS Profile")) {
            name.setText(item.get(0));
            name2.setText(item.get(0));
            daysAgo.setText("last modified: " + DateTime.getFormatedDateTimeString(item.get(1)));
            noOfReply.setText(item.get(6));
//        noOfLikes.setText(item.getNoOfLikes());
//        if (item.isEnabled()){
//            enabled.setText("Enabled");
//        }else enabled.setText("Disabled");


        } else if (doctype.equalsIgnoreCase("POS Opening Entry") || doctype.equalsIgnoreCase("POS Closing Entry")) {
            name.setText(item.get(0));
            daysAgo.setText("last modified: " + DateTime.getFormatedDateTimeString(item.get(3)));
            status.setText(item.get(16));
            if (item.get(16).equalsIgnoreCase("Open")) {
                status.setBackgroundResource(R.drawable.round_bg_red);
                status.setTextColor(context.getResources().getColor(R.color.red));
            } else if (item.get(16).equalsIgnoreCase("Closed")) {
                status.setBackgroundResource(R.drawable.round_bg_green);
                status.setTextColor(context.getResources().getColor(R.color.green));
            } else if (item.get(16).equalsIgnoreCase("Draft")) {
                status.setBackgroundResource(R.drawable.round_bg_dull);
                status.setTextColor(context.getResources().getColor(R.color.light_grey));
            } else if (item.get(16).equalsIgnoreCase("Submitted")) {
                status.setBackgroundResource(R.drawable.round_bg_blue);
                status.setTextColor(context.getResources().getColor(R.color.blue));
            }
            noOfReply.setText(item.get(19));
        } else if (doctype.equalsIgnoreCase("Loyalty Program")) {
            name.setText(item.get(0));
            name2.setText(item.get(0));
            daysAgo.setText("last modified: " + DateTime.getFormatedDateTimeString(item.get(3)));
            noOfReply.setText(item.get(9));
//        noOfLikes.setText(item.getNoOfLikes());
//        if (item.isEnabled()){
//            enabled.setText("Enabled");
//        }else enabled.setText("Disabled");


        } else if (doctype.equalsIgnoreCase("POS Invoice")) {
            name.setText(item.get(28));
            name2.setVisibility(View.VISIBLE);
            name2.setText(item.get(0));
            daysAgo.setText("last modified: " + DateTime.getFormatedDateTimeString(item.get(3)));
            noOfReply.setText(item.get(9));
            status.setText(item.get(25));
            grandTotal.setVisibility(View.VISIBLE);
            grandTotal.setText("$ " + item.get(19));

            if (item.get(25).equalsIgnoreCase("Draft") || item.get(25).equalsIgnoreCase("Cancelled")) {
                status.setBackgroundResource(R.drawable.round_bg_red);
                status.setTextColor(context.getResources().getColor(R.color.red));
            } else if (item.get(25).equalsIgnoreCase("Consolidated") || item.get(25).equalsIgnoreCase("Paid")) {
                status.setBackgroundResource(R.drawable.round_bg_green);
                status.setTextColor(context.getResources().getColor(R.color.green));
            } else if (item.get(25).equalsIgnoreCase("Draft")) {
                status.setBackgroundResource(R.drawable.round_bg_dull);
                status.setTextColor(context.getResources().getColor(R.color.light_grey));
            } else if (item.get(25).equalsIgnoreCase("Submitted")) {
                status.setBackgroundResource(R.drawable.round_bg_blue);
                status.setTextColor(context.getResources().getColor(R.color.blue));
            } else if (item.get(25).equalsIgnoreCase("Unpaid")) {
                status.setBackgroundResource(R.drawable.round_bg_orange);
                status.setTextColor(context.getResources().getColor(R.color.orange));
            }
            if (item.get(9).equalsIgnoreCase("2")) {
                status.setText("Cancelled");
                status.setBackgroundResource(R.drawable.round_bg_red);
                status.setTextColor(context.getResources().getColor(R.color.red));
            }

        } else if (doctype.equalsIgnoreCase("Lead")) {
            name.setText(item.get(17));
            name2.setText(item.get(0));
            name2.setVisibility(View.VISIBLE);
            daysAgo.setText("last modified: " + DateTime.getFormatedDateTimeString(item.get(3)));
            noOfReply.setText(item.get(9));
            status.setText(item.get(16));


        }
        parent.setOnClickListener(view -> callback.onProfileClick(item));
        parent.setOnLongClickListener(v -> {
            callback.onLongClick(item, position);
            return false;
        });
    }

}
