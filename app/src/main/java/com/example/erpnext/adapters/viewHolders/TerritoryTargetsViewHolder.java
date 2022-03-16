package com.example.erpnext.adapters.viewHolders;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.adapters.TerritoryTargetsAdapter;
import com.example.erpnext.callbacks.AddNewTerritoryCallBack;
import com.example.erpnext.models.Target;

public class TerritoryTargetsViewHolder extends RecyclerView.ViewHolder {
    public TextView itemGroup, fiscalYear, targetQty, targetAmount, targetDistribution;
    public View parent;
    TerritoryTargetsAdapter adapter;
    ImageView cross;
    int position;

    public TerritoryTargetsViewHolder(@NonNull View itemView) {
        super(itemView);
        parent = itemView;
        itemGroup = itemView.findViewById(R.id.item_group);
        fiscalYear = itemView.findViewById(R.id.fiscal_year);
        targetQty = itemView.findViewById(R.id.target_qty);
        targetAmount = itemView.findViewById(R.id.target_amount);
        targetDistribution = itemView.findViewById(R.id.target_distribution);
        cross = itemView.findViewById(R.id.cross);
    }

    public void setData(Context context, Target item, AddNewTerritoryCallBack callback, int position, TerritoryTargetsAdapter adapter) {
        this.adapter = adapter;
        this.position = position;
        itemGroup.setText(item.getItemGroup());
        fiscalYear.setText(item.getFiscalYear());
        targetQty.setText("" + item.getTargetQty());
        targetAmount.setText("$" + item.getTargetAmount());
        targetDistribution.setText(item.getDistributionId());
        cross.setOnClickListener(v -> {
            callback.onDeleteTarget(item, position);
        });
//        if (position == 0) {
//            AddNewLandedCostActivity.totalCharges = 0;
//        }
//        AddNewLandedCostActivity.totalCharges = AddNewLandedCostActivity.totalCharges + item.getAmount().floatValue();
//
//
//        AddNewLandedCostActivity.setTotal();

    }
}
