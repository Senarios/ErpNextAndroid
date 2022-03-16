package com.example.erpnext.roomDB.Dao;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.erpnext.network.serializers.response.GetItemsResponse;

import java.util.List;

@Dao
public interface ItemsDao {

    // items
    @Query("SELECT * FROM getitemsresponse")
    GetItemsResponse getItemResponse();

    @Query("SELECT * FROM getitemsresponse")
    List<GetItemsResponse> getAllItemResponse();

    @Query("SELECT * FROM getitemsresponse WHERE item_group LIKE :itemGroup")
    GetItemsResponse getItemResponse(String itemGroup);

    @Insert(onConflict = REPLACE)
    void insertGetItemResponse(GetItemsResponse res);


}
