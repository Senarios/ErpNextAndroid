package com.example.erpnext.app;


public class BaseClass {
    public String string(int id) {
        return MainApp.getAppContext().getString(id);
    }
}
