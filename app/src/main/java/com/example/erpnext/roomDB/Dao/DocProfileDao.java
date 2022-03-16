package com.example.erpnext.roomDB.Dao;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.erpnext.network.serializers.response.GetProfileDocResponse;

@Dao
public interface DocProfileDao {

    // docProfile
    @Query("SELECT * FROM getprofiledocresponse")
    GetProfileDocResponse getProfileDocResponse();

    @Query("SELECT * FROM getprofiledocresponse WHERE name LIKE :profileName")
    GetProfileDocResponse getProfileDocResponse(String profileName);

    @Insert(onConflict = REPLACE)
    void insertDocProfileResponse(GetProfileDocResponse res);


}
