package com.example.erpnext.roomDB.Dao;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.erpnext.network.serializers.response.GetItemDetailResponse;

@Dao
public interface ItemDetailsDao {
    //Menu
    @Query("SELECT * FROM GetItemDetailResponse WHERE itemCode LIKE :itemCode AND warehouse LIKE :warehouse")
    GetItemDetailResponse getItemDetail(String itemCode, String warehouse);

    @Insert(onConflict = REPLACE)
    void insert(GetItemDetailResponse itemDetailResponse);

    @Delete
    void delete(GetItemDetailResponse itemDetailResponse);

}
