package com.example.erpnext.roomDB.Dao;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.erpnext.models.AddCustomerOfflineModel;

import java.util.List;

@Dao
public interface AddCustomerDao {
    @Query("SELECT * FROM AddCustomerOfflineModel")
    List<AddCustomerOfflineModel> getCustomers();

    @Insert(onConflict = REPLACE)
    void insertCustomer(AddCustomerOfflineModel addCustomerOfflineModel);

    @Delete
    void deleteCustomer(AddCustomerOfflineModel addCustomerOfflineModel);
}
