package com.example.erpnext.callbacks;


import com.example.erpnext.models.WarehouseItem;

public interface ReconciliationItemCallback {
    void onDeleteClick(WarehouseItem warehouseItem, int position);
}
