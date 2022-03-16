package com.example.erpnext.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.erpnext.models.Info;

import java.util.List;

public class CustomersLocationViewModel extends AndroidViewModel {

    private MutableLiveData<List<Info>> lv_broadcasts=new MutableLiveData<>();

    public CustomersLocationViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Info>> getInfo(){
        return lv_broadcasts;
    }

    // TODO: Implement the ViewModel
}