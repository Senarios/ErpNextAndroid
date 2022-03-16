package com.example.erpnext.roomDB.Dao;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.erpnext.network.serializers.response.ItemResponse;

@Dao
public interface MenuItemsDao {
    //Menu items
    @Query("SELECT * FROM itemresponse WHERE item_name LIKE :itemname")
    ItemResponse getMenuItems(String itemname);

    @Insert(onConflict = REPLACE)
    void insertRetailItems(ItemResponse res);

    @Delete
    void deleteItems(ItemResponse itemResponse);


}
