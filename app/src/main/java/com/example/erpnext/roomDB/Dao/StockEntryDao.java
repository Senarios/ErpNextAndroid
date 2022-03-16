package com.example.erpnext.roomDB.Dao;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.erpnext.models.StockEntryOfflineModel;

import java.util.List;

@Dao
public interface StockEntryDao {
    @Query("SELECT * FROM StockEntryOfflineModel")
    List<StockEntryOfflineModel> getEntries();

    @Insert(onConflict = REPLACE)
    void insertStockEntry(StockEntryOfflineModel offlineTask);

    @Delete
    void deleteStockEntry(StockEntryOfflineModel offlineTask);
}
