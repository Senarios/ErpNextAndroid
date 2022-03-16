package com.example.erpnext.adapters.viewHolders;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.activities.AddNewClosingActivity;
import com.example.erpnext.callbacks.CartItemCallback;
import com.example.erpnext.models.InvoicePayment;


public class ModeOfPaymentViewHolder extends RecyclerView.ViewHolder {

    public TextView modeOfPayment, counter, opening_amount, difference_amount, expected_amount;
    public RecyclerView linksRV;
    public EditText closing_amount;

    public View parent;

    public ModeOfPaymentViewHolder(@NonNull View itemView) {
        super(itemView);
        parent = itemView;
        modeOfPayment = itemView.findViewById(R.id.mode_ofPayment);
        opening_amount = itemView.findViewById(R.id.opening_amount);
        closing_amount = itemView.findViewById(R.id.closing_amount);
        expected_amount = itemView.findViewById(R.id.expected_amount);
        difference_amount = itemView.findViewById(R.id.difference_amount);
//        counter = itemView.findViewById(R.id.counter);


    }

    public void setData(Context context, InvoicePayment item, CartItemCallback callback, int position) {
        modeOfPayment.setText(item.getModeOfPayment());
        opening_amount.setText(String.valueOf(AddNewClosingActivity.openingAmount));
        closing_amount.setText("0");
        expected_amount.setText(String.valueOf(item.getBaseAmount()));
        difference_amount.setText(String.valueOf(Math.abs(item.getBaseAmount())));
//        counter.setText(String.valueOf(position+1));

    }


}
