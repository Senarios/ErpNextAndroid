package com.example.erpnext.roomDB.Dao;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.erpnext.models.PendingLoyalty;

import java.util.List;

@Dao
public interface PendingLoyaltyDao {
    //Menu
    @Query("SELECT * FROM PendingLoyalty")
    List<PendingLoyalty> getPendingLoyalty();

    @Insert(onConflict = REPLACE)
    void insert(PendingLoyalty pendingLoyalty);

    @Delete
    void delete(PendingLoyalty pendingLoyalty);

}
