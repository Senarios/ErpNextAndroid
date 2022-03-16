package com.example.erpnext.callbacks;


import com.example.erpnext.models.LandedCostItem;
import com.example.erpnext.models.LandedCostReceipt;
import com.example.erpnext.models.Tax;

public interface AddLandedCostCallback {
    void onDeletePurchaseReceiptClick(LandedCostReceipt costReceipt, int position);

    void onDeleteItemClick(LandedCostItem landedCostItem, int position);

    void onDeleteChargesClick(Tax tax, int position);
}
