package com.example.erpnext.roomDB.Dao;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.erpnext.network.serializers.response.DocTypeResponse;

@Dao
public interface DoctypeDao {

    //DocType
    @Query("SELECT * FROM doctyperesponse WHERE doctype LIKE :doctype")
    DocTypeResponse getDocTypeResponse(String doctype);

    @Insert(onConflict = REPLACE)
    void insertDocTypeResponse(DocTypeResponse res);


}
