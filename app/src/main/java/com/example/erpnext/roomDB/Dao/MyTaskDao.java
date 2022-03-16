package com.example.erpnext.roomDB.Dao;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.erpnext.models.MyTaskOfflineModel;

import java.util.List;

@Dao
public interface MyTaskDao {
    @Query("SELECT * FROM MyTaskOfflineModel")
    List<MyTaskOfflineModel> getOrders();

    @Insert(onConflict = REPLACE)
    void insertTask(MyTaskOfflineModel offlineTask);

    @Delete
    void deleteTask(MyTaskOfflineModel offlineTask);
}
