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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.adapters.CreditLimitsAdapter;
import com.example.erpnext.callbacks.CreditLimitCallback;
import com.example.erpnext.models.CreditLimit;
import com.example.erpnext.models.WarehouseItem;
import com.example.erpnext.utils.Notify;


public class CreditLimitsViewHolder extends RecyclerView.ViewHolder {


    public TextView company;
    public View parent;
    CheckBox isByPass;
    TextView creditLimit;
    CreditLimitsAdapter adapter;
    ImageView cross;
    int position;

    public CreditLimitsViewHolder(@NonNull View itemView) {
        super(itemView);
        parent = itemView;
        company = itemView.findViewById(R.id.name);
        isByPass = itemView.findViewById(R.id.bypass);
        cross = itemView.findViewById(R.id.cross);
        creditLimit = itemView.findViewById(R.id.credit_limit);


    }

    public void setData(Context context, CreditLimit item, CreditLimitCallback callback, int position, CreditLimitsAdapter adapter) {
        this.adapter = adapter;
        this.position = position;
        company.setText("Izat Afghan Limited");
        isByPass.setChecked(item.isByPass());
        creditLimit.setText(item.getCreditLimit());


        cross.setOnClickListener(v -> {
            callback.onDeleteCredit(item, position);
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
