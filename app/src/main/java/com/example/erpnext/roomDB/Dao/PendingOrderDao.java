package com.example.erpnext.roomDB.Dao;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.erpnext.models.PendingOrder;

import java.util.List;

@Dao
public interface PendingOrderDao {
    //Menu
    @Query("SELECT * FROM PendingOrder")
    List<PendingOrder> getOrders();

    @Insert(onConflict = REPLACE)
    void insertOrder(PendingOrder pendingOrder);

    @Delete
    void deleteOrder(PendingOrder pendingOrder);

}
