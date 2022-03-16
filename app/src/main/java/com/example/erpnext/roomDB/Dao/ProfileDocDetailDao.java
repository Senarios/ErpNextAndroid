package com.example.erpnext.roomDB.Dao;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.erpnext.network.serializers.response.DocDetailResponse;

@Dao
public interface ProfileDocDetailDao {


    @Query("SELECT * FROM docdetailresponse WHERE profileName LIKE :profileName")
    DocDetailResponse getProfileDetail(String profileName);

    @Insert(onConflict = REPLACE)
    void insert(DocDetailResponse res);


}
