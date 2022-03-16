package com.example.erpnext.callbacks;


import java.util.List;

public interface ProfilesCallback {
    void onProfileClick(List<String> list);

    void onLongClick(List<String> list, int position);
}
