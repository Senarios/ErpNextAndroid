package com.example.erpnext.roomDB.Dao;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.erpnext.models.PendingEditPOS;

import java.util.List;

@Dao
public interface PendingEditPOSDao {
    //Menu
    @Query("SELECT * FROM PendingEditPOS")
    List<PendingEditPOS> getPendingEditPOS();

    @Insert(onConflict = REPLACE)
    void insert(PendingEditPOS pendingEditPOS);

    @Delete
    void delete(PendingEditPOS pendingEditPOS);

}
