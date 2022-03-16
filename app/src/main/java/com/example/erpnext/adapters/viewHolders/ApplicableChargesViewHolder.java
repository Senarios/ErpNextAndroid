package com.example.erpnext.adapters.viewHolders;

import android.app.Dialog;
import android.content.Context;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.activities.AddNewLandedCostActivity;
import com.example.erpnext.adapters.ApplicableChargesAdapter;
import com.example.erpnext.callbacks.AddLandedCostCallback;
import com.example.erpnext.models.Tax;
import com.example.erpnext.models.WarehouseItem;
import com.example.erpnext.utils.Notify;


public class ApplicableChargesViewHolder extends RecyclerView.ViewHolder {


    public TextView account, amount, itemQuantity, itemDescription, warehouse;
    public View parent;
    ApplicableChargesAdapter adapter;
    ImageView cross;
    int position;

    public ApplicableChargesViewHolder(@NonNull View itemView) {
        super(itemView);
        parent = itemView;
        account = itemView.findViewById(R.id.expense_account);
        amount = itemView.findViewById(R.id.amount);
        cross = itemView.findViewById(R.id.cross);
        itemDescription = itemView.findViewById(R.id.description);


    }

    public void setData(Context context, Tax item, AddLandedCostCallback callback, int position, ApplicableChargesAdapter adapter) {
        this.adapter = adapter;
        this.position = position;
        account.setText(item.getExpenseAccount());
        itemDescription.setText(item.getDescription());
        amount.setText(String.valueOf("$" + item.getAmount()));
        if (position == 0) {
            AddNewLandedCostActivity.totalCharges = 0;
        }
        AddNewLandedCostActivity.totalCharges = AddNewLandedCostActivity.totalCharges + item.getAmount().floatValue();


        AddNewLandedCostActivity.setTotal();
        cross.setOnClickListener(v -> {
            callback.onDeleteChargesClick(item, position);
        });
    }

    private void showQuantitydialog(Context context, WarehouseItem item, String title, String rate) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.set_quantity_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.90);
        dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
        TextView titletv = dialog.findViewById(R.id.title);
        TextView fieldLabel = dialog.findViewById(R.id.field_label);
        EditText quantity = dialog.findViewById(R.id.quantity_edit);
        Button setQuantity = dialog.findViewById(R.id.set);
        titletv.setText("Select " + title);
        fieldLabel.setText(title);
        quantity.setText(rate);
        if (title.equalsIgnoreCase("rate")) {
            quantity.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        }
        setQuantity.setOnClickListener(v -> {
            if (quantity.getText().toString() != null && !quantity.getText().toString().isEmpty() && !quantity.getText().toString().equalsIgnoreCase("0")) {
                dialog.dismiss();
                if (title.equalsIgnoreCase("rate")) {
                    quantity.setVisibility(View.GONE);
                    item.setValuationRate(Double.valueOf(quantity.getText().toString()));

                    adapter.notifyItemChanged(position);

                } else if (title.equalsIgnoreCase("Quantity")) {
                    item.setQty(Double.valueOf(quantity.getText().toString()));
                    adapter.notifyItemChanged(position);
                }
            } else Notify.Toast("Please enter " + title);
        });
    }
}
