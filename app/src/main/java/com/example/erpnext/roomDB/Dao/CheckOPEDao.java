package com.example.erpnext.roomDB.Dao;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.erpnext.network.serializers.response.CheckOpeningEntryResponse;

@Dao
public interface CheckOPEDao {
    //Check opening entry
    @Query("SELECT * FROM checkopeningentryresponse")
    CheckOpeningEntryResponse getCheckOpeningEntries();

    @Insert(onConflict = REPLACE)
    void insertCheckOpeningEntry(CheckOpeningEntryResponse res);

    @Delete
    void deleteCheckOpeningEntry(CheckOpeningEntryResponse response);

}
