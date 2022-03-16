package com.example.erpnext.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.erpnext.repositories.MyTaskViewRepo;
import java.util.List;

public class MyTasksViewModel extends ViewModel {
    MyTaskViewRepo repo;

    public MyTasksViewModel() {
        repo = MyTaskViewRepo.getInstance();
    }

    // TODO: Implement the ViewModel
//    public void getMyTaskList(String email)  {
//        repo.getMyTaskItemsApi(email);
//    }

    public LiveData<List<List<String>>> getMyTaskItems() {
        return repo.getMyTaskItemsList();
    }
}