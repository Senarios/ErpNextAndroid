package com.example.erpnext.roomDB.Dao;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.erpnext.network.serializers.response.ReportViewResponse;

@Dao
public interface ReportViewDao {

    // reportView
    @Query("SELECT * FROM reportviewresponse WHERE doctype LIKE :doctype")
    ReportViewResponse getReportViewResponse(String doctype);

    @Insert(onConflict = REPLACE)
    void insertReportViewResponse(ReportViewResponse res);


}
