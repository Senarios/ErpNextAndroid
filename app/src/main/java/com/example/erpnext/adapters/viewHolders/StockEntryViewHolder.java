package com.example.erpnext.adapters.viewHolders;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.utils.DateTime;

public class StockEntryViewHolder extends RecyclerView.ViewHolder {

    public TextView title, name, daysAgo, purpose, source, status;

    public StockEntryViewHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.title);
        name = itemView.findViewById(R.id.name);
        daysAgo = itemView.findViewById(R.id.daysAgo);
        purpose = itemView.findViewById(R.id.purpose);
        source = itemView.findViewById(R.id.source);
        status = itemView.findViewById(R.id.status);
    }

    public void setData(Context context, String title, String name, String daysAgo, String purpose, String source, String status) {
        this.title.setText(title);
        this.name.setText(name);
        this.daysAgo.setText("last modified: " + DateTime.getFormatedDateTimeString(daysAgo));
        setPurpose(purpose);
        setSource(source);
        setStatus(context, status);
    }

    private void setSource(String source) {
        if (source != null) {
            this.source.setVisibility(View.VISIBLE);
            this.source.setText(source);
        }
    }

    private void setPurpose(String purpose) {
        if (purpose != null) {
            this.purpose.setVisibility(View.VISIBLE);
            this.purpose.setText(purpose);
        }
    }

    private void setStatus(Context context, String status) {
        if (status.equalsIgnoreCase("1")) {
            this.status.setText("Submitted");
            this.status.setBackgroundResource(R.drawable.round_bg_blue);
            this.status.setTextColor(context.getResources().getColor(R.color.blue));
        } else if (status.equalsIgnoreCase("0")) {
            this.status.setText("Draft");
            this.status.setBackgroundResource(R.drawable.round_bg_red);
            this.status.setTextColor(context.getResources().getColor(R.color.red));
        } else if (status.equalsIgnoreCase("2")) {
            this.status.setText("Cancelled");
            this.status.setBackgroundResource(R.drawable.round_bg_red);
            this.status.setTextColor(context.getResources().getColor(R.color.red));
        } else {
            this.status.setText(status);
            this.status.setBackgroundResource(R.drawable.round_bg_blue);
            this.status.setTextColor(context.getResources().getColor(R.color.blue));
        }
    }
}
