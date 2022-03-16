package com.example.erpnext.roomDB.Dao;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.erpnext.network.serializers.response.MenuResponse;

@Dao
public interface ApiRequestsDao {
    //Menu
    @Query("SELECT * FROM menuresponse")
    MenuResponse getMenu();

    @Insert(onConflict = REPLACE)
    void insertMenu(MenuResponse menuResponse);

    @Delete
    void deleteMenu(MenuResponse menuResponse);

}
