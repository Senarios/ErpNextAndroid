package com.example.erpnext.roomDB.Dao;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.erpnext.models.PendingInvoiceAction;

import java.util.List;

@Dao
public interface PendingCancelInvoiceDao {
    //Menu
    @Query("SELECT * FROM PendingInvoiceAction")
    List<PendingInvoiceAction> getPendingForCancel();

    @Insert(onConflict = REPLACE)
    void insert(PendingInvoiceAction pendingInvoiceAction);

    @Delete
    void delete(PendingInvoiceAction pendingInvoiceAction);

    @Query("DELETE FROM PendingInvoiceAction WHERE uid = :uid")
    void delete(Integer uid);

}
