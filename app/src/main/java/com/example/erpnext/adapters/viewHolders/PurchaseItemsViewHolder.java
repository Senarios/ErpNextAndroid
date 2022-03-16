package com.example.erpnext.adapters.viewHolders;

import android.app.Dialog;
import android.content.Context;
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
import com.example.erpnext.activities.AddNewDeliveryNoteActivity;
import com.example.erpnext.activities.AddNewPurchaseReceiptActivity;
import com.example.erpnext.adapters.PurchaseItemsAdapter;
import com.example.erpnext.callbacks.PurchaseItemCallback;
import com.example.erpnext.models.SearchItemDetail;
import com.example.erpnext.utils.Notify;
import com.example.erpnext.utils.Utils;


public class PurchaseItemsViewHolder extends RecyclerView.ViewHolder {


    public TextView productName, price, itemQuantity, itemDescription, rate;
    public View parent;
    PurchaseItemsAdapter purchaseItemsAdapter;
    ImageView cross;

    public PurchaseItemsViewHolder(@NonNull View itemView) {
        super(itemView);
        parent = itemView;
        productName = itemView.findViewById(R.id.name);
        price = itemView.findViewById(R.id.price);
        itemQuantity = itemView.findViewById(R.id.item_quantity);
        cross = itemView.findViewById(R.id.cross);
        rate = itemView.findViewById(R.id.rate);
        itemDescription = itemView.findViewById(R.id.description);


    }

    public void setData(Context context, SearchItemDetail item, PurchaseItemCallback callback, int position, PurchaseItemsAdapter purchaseItemsAdapter, String docType) {
        this.purchaseItemsAdapter = purchaseItemsAdapter;
        if (item != null && item.getItemCode() != null) {
            productName.setText(item.getItemName());
            itemDescription.setText(item.getItemCode());
            itemDescription.setText(item.getItemCode());
            itemQuantity.setText(String.valueOf(item.getQty().intValue()));
            rate.setText("$ " + item.getPriceListRate().toString());
            float amount = item.getQty().floatValue() * item.getPriceListRate().floatValue();
            item.setAmount((double) amount);
            price.setText("$ " + Utils.round(item.getAmount().floatValue(), 2));

            rate.setOnClickListener(v -> {
                showQuantitydialog(context, item, "Rate", item.getPriceListRate().toString());
            });
            itemQuantity.setOnClickListener(v -> {
                showQuantitydialog(context, item, "Quantity", String.valueOf(item.getQty().intValue()));
            });
            cross.setOnClickListener(v -> {
                callback.onDeleteClick(item);
            });

            if (docType.equalsIgnoreCase("New Purchase Receipt")) {

                if (position == 0) {
                    AddNewPurchaseReceiptActivity.netWeight = 0;
                    AddNewPurchaseReceiptActivity.totalQuantity = 0;
                    AddNewPurchaseReceiptActivity.totalUSD = 0;
                    AddNewPurchaseReceiptActivity.baseGrandTotal = 0;
                }
                AddNewPurchaseReceiptActivity.netWeight = AddNewPurchaseReceiptActivity.netWeight + item.getWeightPerUnit().floatValue() * item.getQty().floatValue();
                AddNewPurchaseReceiptActivity.totalQuantity = AddNewPurchaseReceiptActivity.totalQuantity + item.getQty().floatValue();
                AddNewPurchaseReceiptActivity.totalUSD = AddNewPurchaseReceiptActivity.totalUSD + item.getAmount().floatValue();
                AddNewPurchaseReceiptActivity.baseGrandTotal = AddNewPurchaseReceiptActivity.baseGrandTotal + item.getAmount().floatValue();


                AddNewPurchaseReceiptActivity.setTotal();
            }
        } else if (docType.equalsIgnoreCase("New Delivery Note")) {
            if (position == 0) {
                AddNewDeliveryNoteActivity.netWeight = 0;
                AddNewDeliveryNoteActivity.totalQuantity = 0;
                AddNewDeliveryNoteActivity.totalUSD = 0;
                AddNewDeliveryNoteActivity.baseGrandTotal = 0;
            }
            AddNewDeliveryNoteActivity.netWeight = AddNewDeliveryNoteActivity.netWeight + item.getWeightPerUnit().floatValue() * item.getQty().floatValue();
            AddNewDeliveryNoteActivity.totalQuantity = AddNewDeliveryNoteActivity.totalQuantity + item.getQty().floatValue();
            AddNewDeliveryNoteActivity.totalUSD = AddNewDeliveryNoteActivity.totalUSD + item.getAmount().floatValue();
            AddNewDeliveryNoteActivity.baseGrandTotal = AddNewDeliveryNoteActivity.baseGrandTotal + item.getAmount().floatValue();

            AddNewDeliveryNoteActivity.setTotal();
        }


    }


    private void showQuantitydialog(Context context, SearchItemDetail item, String title, String rate) {
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
        setQuantity.setOnClickListener(v -> {
            if (quantity.getText().toString() != null && !quantity.getText().toString().isEmpty() && !quantity.getText().toString().equalsIgnoreCase("0")) {
                dialog.dismiss();
                if (title.equalsIgnoreCase("rate")) {
                    item.setPriceListRate(Double.valueOf(quantity.getText().toString()));
                    float amount = item.getQty().floatValue() * item.getPriceListRate().floatValue();
                    item.setAmount((double) amount);
                    purchaseItemsAdapter.notifyDataSetChanged();
                    AddNewPurchaseReceiptActivity.binding.discountAmount.setText("");
                    AddNewPurchaseReceiptActivity.binding.discountPercent.setText("");

                } else if (title.equalsIgnoreCase("Quantity")) {
                    item.setQty(Double.valueOf(quantity.getText().toString()));
                    float amount = item.getQty().floatValue() * item.getPriceListRate().floatValue();
                    item.setAmount((double) amount);
                    purchaseItemsAdapter.notifyDataSetChanged();
                    AddNewPurchaseReceiptActivity.binding.discountAmount.setText("");
                    AddNewPurchaseReceiptActivity.binding.discountPercent.setText("");
                }
            } else Notify.Toast("Please enter quantity");
        });
    }
}
