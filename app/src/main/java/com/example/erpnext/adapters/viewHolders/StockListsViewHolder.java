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


public class StockListsViewHolder extends RecyclerView.ViewHolder {

    public TextView name, name2, daysAgo, noOfLikes, noOfReply, status, grandTotal;

    public View parent;

    public StockListsViewHolder(@NonNull View itemView) {
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
        if (doctype.equalsIgnoreCase("Purchase Receipt")) {
            name.setText(item.get(25));
            name2.setVisibility(View.VISIBLE);
            name2.setText(item.get(0));
            daysAgo.setText("last modified: " + DateTime.getFormatedDateTimeString(item.get(3)));
            noOfReply.setText(item.get(34));
            status.setText(item.get(24));
            if (item.get(21) != null && Float.parseFloat(item.get(21)) != 0) {
                grandTotal.setVisibility(View.VISIBLE);
                grandTotal.setText("$ " + item.get(21));
            }


            if (item.get(24).equalsIgnoreCase("Cancelled")) {
                status.setBackgroundResource(R.drawable.round_bg_red);
                status.setTextColor(context.getResources().getColor(R.color.red));
            } else if (item.get(24).equalsIgnoreCase("Completed") || item.get(24).equalsIgnoreCase("Closed")) {
                status.setBackgroundResource(R.drawable.round_bg_green);
                status.setTextColor(context.getResources().getColor(R.color.green));
            } else if (item.get(24).equalsIgnoreCase("Draft")) {
                status.setBackgroundResource(R.drawable.round_bg_dull);
                status.setTextColor(context.getResources().getColor(R.color.light_grey));
            } else if (item.get(24).equalsIgnoreCase("Submitted")) {
                status.setBackgroundResource(R.drawable.round_bg_blue);
                status.setTextColor(context.getResources().getColor(R.color.blue));
            } else if (item.get(24).equalsIgnoreCase("To Bill")) {
                status.setBackgroundResource(R.drawable.round_bg_orange);
                status.setTextColor(context.getResources().getColor(R.color.orange));
            }

        } else if (doctype.equalsIgnoreCase("Item Price")) {
            name.setText(item.get(15));
            name2.setVisibility(View.VISIBLE);
            name2.setText(item.get(14));
            daysAgo.setText("last modified: " + DateTime.getFormatedDateTimeString(item.get(3)));
            noOfReply.setText(item.get(20));
            if (item.get(17) != null && Float.parseFloat(item.get(17)) != 0) {
                grandTotal.setVisibility(View.VISIBLE);
                grandTotal.setText("$ " + item.get(17));
            }
        } else if (doctype.equalsIgnoreCase("Stock Reconciliation")) {
            name.setText(item.get(0));
            daysAgo.setText("last modified: " + DateTime.getFormatedDateTimeString(item.get(3)));
            noOfReply.setText(item.get(16));

            if (item.get(9).equalsIgnoreCase("1")) {
                status.setText("Submitted");
                status.setBackgroundResource(R.drawable.round_bg_blue);
                status.setTextColor(context.getResources().getColor(R.color.blue));
            } else if (item.get(9).equalsIgnoreCase("0")) {
                status.setText("Draft");
                status.setBackgroundResource(R.drawable.round_bg_red);
                status.setTextColor(context.getResources().getColor(R.color.red));
            }
        } else if (doctype.equalsIgnoreCase("Landed Cost Voucher")) {
            name.setText(item.get(0));
            daysAgo.setText("last modified: " + DateTime.getFormatedDateTimeString(item.get(3)));
            noOfReply.setText(item.get(15));

            if (item.get(9).equalsIgnoreCase("1")) {
                status.setText("Submitted");
                status.setBackgroundResource(R.drawable.round_bg_blue);
                status.setTextColor(context.getResources().getColor(R.color.blue));
            } else if (item.get(9).equalsIgnoreCase("0")) {
                status.setText("Draft");
                status.setBackgroundResource(R.drawable.round_bg_red);
                status.setTextColor(context.getResources().getColor(R.color.red));
            }
        } else if (doctype.equalsIgnoreCase("Item")) {
            name.setText(item.get(15));
            name2.setVisibility(View.VISIBLE);
            name2.setText(item.get(14));
            daysAgo.setText("last modified: " + DateTime.getFormatedDateTimeString(item.get(3)));
            noOfReply.setText(item.get(22));
//            if (item.get(17) != null && Float.parseFloat(item.get(17)) != 0) {
//                grandTotal.setVisibility(View.VISIBLE);
//                grandTotal.setText("$ " + item.get(17));
//            }
        } else if (doctype.equalsIgnoreCase("Warehouse")) {
            name.setText(item.get(17));
            name2.setVisibility(View.VISIBLE);
            name2.setText(item.get(0));
            daysAgo.setText("last modified: " + DateTime.getFormatedDateTimeString(item.get(3)));
            noOfReply.setText(item.get(18));
            if (item.get(9) != null && Float.parseFloat(item.get(9)) == 0) {
                status.setText("Enabled");
            }

        } else if (doctype.equalsIgnoreCase("Territory")) {
            name.setText(item.get(0));
            name2.setVisibility(View.VISIBLE);
            name2.setText(item.get(15));
            daysAgo.setText("last modified: " + DateTime.getFormatedDateTimeString(item.get(3)));
            noOfReply.setText(item.get(18));
            if (item.get(9) != null && Float.parseFloat(item.get(9)) == 0) {
                status.setText("Enabled");
            }

        } else if (doctype.equalsIgnoreCase("Delivery Note")) {

            name.setText(item.get(23));
            name2.setVisibility(View.VISIBLE);
            name2.setText(item.get(0));
            daysAgo.setText("last modified: " + DateTime.getFormatedDateTimeString(item.get(3)));
            noOfReply.setText(item.get(30));
            status.setText(item.get(19));

            if (item.get(19).equalsIgnoreCase("Cancelled")) {
                status.setBackgroundResource(R.drawable.round_bg_red);
                status.setTextColor(context.getResources().getColor(R.color.red));
            } else if (item.get(19).equalsIgnoreCase("Completed") || item.get(24).equalsIgnoreCase("Closed")) {
                status.setBackgroundResource(R.drawable.round_bg_green);
                status.setTextColor(context.getResources().getColor(R.color.green));
            } else if (item.get(19).equalsIgnoreCase("Draft")) {
                status.setBackgroundResource(R.drawable.round_bg_dull);
                status.setTextColor(context.getResources().getColor(R.color.light_grey));
            } else if (item.get(19).equalsIgnoreCase("Submitted")) {
                status.setBackgroundResource(R.drawable.round_bg_blue);
                status.setTextColor(context.getResources().getColor(R.color.blue));
            } else if (item.get(19).equalsIgnoreCase("To Bill")) {
                status.setBackgroundResource(R.drawable.round_bg_orange);
                status.setTextColor(context.getResources().getColor(R.color.orange));
            }
        } else if (doctype.equalsIgnoreCase("Opportunity")) {
            name.setText(item.get(19));
            name2.setVisibility(View.VISIBLE);
            name2.setText(item.get(0));
            daysAgo.setText("last modified: " + DateTime.getFormatedDateTimeString(item.get(3)));
            noOfReply.setText(item.get(23));
            status.setText(item.get(17));

            if (item.get(17).equalsIgnoreCase("Open")) {
                status.setBackgroundResource(R.drawable.round_bg_red);
                status.setTextColor(context.getResources().getColor(R.color.red));
            } else if (item.get(17).equalsIgnoreCase("Quotation") || item.get(17).equalsIgnoreCase("Converted")
                    || item.get(17).equalsIgnoreCase("Closed")) {
                status.setBackgroundResource(R.drawable.round_bg_green);
                status.setTextColor(context.getResources().getColor(R.color.green));
            } else if (item.get(17).equalsIgnoreCase("Draft") || item.get(17).equalsIgnoreCase("Lost")
                    || item.get(17).equalsIgnoreCase("Replied")) {
                status.setBackgroundResource(R.drawable.round_bg_dull);
                status.setTextColor(context.getResources().getColor(R.color.light_grey));
            }
        } else if (doctype.equalsIgnoreCase("Customer Group")) {
            name.setText(item.get(14));
            name2.setVisibility(View.VISIBLE);
            name2.setText(item.get(0));
            daysAgo.setText("last modified: " + DateTime.getFormatedDateTimeString(item.get(3)));
            noOfReply.setText(item.get(17));
        } else if (doctype.equalsIgnoreCase("Customer")) {
            name.setText(item.get(16));
            name2.setVisibility(View.VISIBLE);
            name2.setText(item.get(0));
            daysAgo.setText("last modified: " + DateTime.getFormatedDateTimeString(item.get(3)));
            noOfReply.setText(item.get(20));
        } else if (doctype.equalsIgnoreCase("Lead Source")) {
            name.setText(item.get(14));
            name2.setVisibility(View.VISIBLE);
            name2.setText(item.get(0));
            daysAgo.setText("last modified: " + DateTime.getFormatedDateTimeString(item.get(3)));
            noOfReply.setText(item.get(15));
        } else if (doctype.equalsIgnoreCase("Communication")) {
            name.setText(item.get(17));
            name2.setVisibility(View.VISIBLE);
            name2.setText(item.get(0));
            daysAgo.setText("last modified: " + DateTime.getFormatedDateTimeString(item.get(3)));
            noOfReply.setText(item.get(28));
            status.setText(item.get(15));
            if (item.get(15).equalsIgnoreCase("Open")) {
                status.setBackgroundResource(R.drawable.round_bg_red);
                status.setTextColor(context.getResources().getColor(R.color.red));
            } else if (item.get(15).equalsIgnoreCase("Quotation") || item.get(15).equalsIgnoreCase("Converted")
                    || item.get(15).equalsIgnoreCase("Closed")) {
                status.setBackgroundResource(R.drawable.round_bg_green);
                status.setTextColor(context.getResources().getColor(R.color.green));
            } else if (item.get(15).equalsIgnoreCase("Draft") || item.get(15).equalsIgnoreCase("Lost")
                    || item.get(15).equalsIgnoreCase("Replied")) {
                status.setBackgroundResource(R.drawable.round_bg_dull);
                status.setTextColor(context.getResources().getColor(R.color.light_grey));
            }
        } else if (doctype.equalsIgnoreCase("Contact")) {
            name.setText(item.get(0));
            name2.setVisibility(View.VISIBLE);
            name2.setText(item.get(14));
            daysAgo.setText("last modified: " + DateTime.getFormatedDateTimeString(item.get(3)));
            noOfReply.setText(item.get(18));
            status.setText(item.get(15));
            if (item.get(15).equalsIgnoreCase("Open")) {
                status.setBackgroundResource(R.drawable.round_bg_red);
                status.setTextColor(context.getResources().getColor(R.color.red));
            } else if (item.get(15).equalsIgnoreCase("Passive")) {
                status.setBackgroundResource(R.drawable.round_bg_blue);
                status.setTextColor(context.getResources().getColor(R.color.blue));
            } else if (item.get(15).equalsIgnoreCase("Draft") || item.get(15).equalsIgnoreCase("Lost")
                    || item.get(15).equalsIgnoreCase("Replied")) {
                status.setBackgroundResource(R.drawable.round_bg_dull);
                status.setTextColor(context.getResources().getColor(R.color.light_grey));
            }
        } else if (doctype.equalsIgnoreCase("Sales Person")) {
            name.setText(item.get(14));
            name2.setVisibility(View.VISIBLE);
            name2.setText(item.get(0));
            daysAgo.setText("last modified: " + DateTime.getFormatedDateTimeString(item.get(3)));
            noOfReply.setText(item.get(18));
            if (item.get(17).equalsIgnoreCase("1")) {
                status.setText("Enabled");
                status.setBackgroundResource(R.drawable.round_bg_blue);
                status.setTextColor(context.getResources().getColor(R.color.blue));
            } else status.setText("Disabled");
        }
        else if (doctype.equalsIgnoreCase("User")) {
            name.setText(item.get(14));
            name2.setVisibility(View.VISIBLE);
            name2.setText(item.get(0));
            daysAgo.setText("last modified: " + DateTime.getFormatedDateTimeString(item.get(3)));
            noOfReply.setText(item.get(18));
            if (item.get(17).equalsIgnoreCase("1")) {
                status.setText("Enabled");
                status.setBackgroundResource(R.drawable.round_bg_blue);
                status.setTextColor(context.getResources().getColor(R.color.blue));
            } else status.setText("Disabled");
        }

        parent.setOnClickListener(view -> callback.onProfileClick(item));
        parent.setOnLongClickListener(v -> {
            callback.onLongClick(item, position);
            return false;
        });
    }

}
