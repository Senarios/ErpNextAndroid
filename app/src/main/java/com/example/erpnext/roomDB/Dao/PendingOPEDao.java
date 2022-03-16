package com.example.erpnext.roomDB.Dao;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.erpnext.models.PendingOPE;

import java.util.List;

@Dao
public interface PendingOPEDao {
    //Menu
    @Query("SELECT * FROM PendingOPE")
    List<PendingOPE> getPendingOpeningEntries();

    @Insert(onConflict = REPLACE)
    void insertOPE(PendingOPE pendingOPE);

    @Delete
    void deleteOPE(PendingOPE pendingOPE);

}
