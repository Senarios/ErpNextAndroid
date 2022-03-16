package com.example.erpnext.adapters.viewHolders;

import android.app.Dialog;
import android.content.Context;
import android.text.Html;
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
import com.example.erpnext.adapters.LandedCostItemsAdapter;
import com.example.erpnext.callbacks.AddLandedCostCallback;
import com.example.erpnext.models.LandedCostItem;
import com.example.erpnext.utils.Utils;


public class LandedCostItemsViewHolder extends RecyclerView.ViewHolder {


    public TextView productName, charges, amount, itemDescription, item_quantity;
    public View parent;
    LandedCostItemsAdapter landedCostItemsAdapter;
    ImageView cross;
    int position;

    public LandedCostItemsViewHolder(@NonNull View itemView) {
        super(itemView);
        parent = itemView;
        productName = itemView.findViewById(R.id.name);
        charges = itemView.findViewById(R.id.val_rate);
        amount = itemView.findViewById(R.id.item_quantity);
        cross = itemView.findViewById(R.id.cross);
        item_quantity = itemView.findViewById(R.id.warehouse);
        itemDescription = itemView.findViewById(R.id.description);


    }

    public void setData(Context context, LandedCostItem item, AddLandedCostCallback callback, int position, LandedCostItemsAdapter landedCostItemsAdapter) {
        this.landedCostItemsAdapter = landedCostItemsAdapter;
        this.position = position;
        if (item != null && item.getItemCode() != null) {
            productName.setText(item.getItemCode());
            item_quantity.setText(item.getQty().toString());
            itemDescription.setText(Html.fromHtml(item.getDescription()).toString());
            amount.setText("$ " + item.getAmount());
            charges.setText("$ " + Utils.round(item.getApplicableCharges().floatValue(), 3));
//
//            valRate.setOnClickListener(v -> {
//                showQuantitydialog(context, item, "Rate", item.getValuationRate().toString());
//            });
//            itemQuantity.setOnClickListener(v -> {
//                showQuantitydialog(context, item, "Quantity", String.valueOf(item.getQty().intValue()));
//            });
            cross.setOnClickListener(v -> {
                callback.onDeleteItemClick(item, position);
            });
        }
    }

    private void showQuantitydialog(Context context, LandedCostItem item, String title, String rate) {
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
//            if (quantity.getText().toString() != null && !quantity.getText().toString().isEmpty() && !quantity.getText().toString().equalsIgnoreCase("0")) {
//                dialog.dismiss();
//                if (title.equalsIgnoreCase("rate")) {
//                    quantity.setVisibility(View.GONE);
//                    item.setValuationRate(Double.valueOf(quantity.getText().toString()));
//
//                    landedCostItemsAdapter.notifyItemChanged(position);
//
//                } else if (title.equalsIgnoreCase("Quantity")) {
//                    item.setQty(Double.valueOf(quantity.getText().toString()));
//                    landedCostItemsAdapter.notifyItemChanged(position);
//                }
//            } else Notify.Toast("Please enter " + title);
        });
    }
}
