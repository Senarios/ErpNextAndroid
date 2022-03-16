package com.example.erpnext.fragments;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.erpnext.R;


public class BaseFragment extends Fragment {
    protected String name = "";
    protected boolean isDrawer;


    public boolean isDrawer() {
        return isDrawer;
    }

    public void setDrawer(boolean drawer) {
        isDrawer = drawer;
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            DrawerLayout drawerLayout = getActivity().findViewById(R.id.drawer_layout);
            if (!isDrawer()) {
//                ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            } else {
//                ((AppCompatActivity) getActivity()).getSupportActionBar().show();
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            }
        } catch (Exception e) {

        }
    }
}
