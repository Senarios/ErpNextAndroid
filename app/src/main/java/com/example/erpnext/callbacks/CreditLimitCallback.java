package com.example.erpnext.callbacks;


import com.example.erpnext.models.CreditLimit;

public interface CreditLimitCallback {
    void onDeleteCredit(CreditLimit creditLimit, int position);

}
