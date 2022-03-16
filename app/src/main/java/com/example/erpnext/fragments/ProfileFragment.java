package com.example.erpnext.fragments;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.example.erpnext.R;
import com.example.erpnext.activities.LoginActivity;
import com.example.erpnext.app.AppSession;
import com.example.erpnext.fcm.BackgroundService;
import com.example.erpnext.fcm.BgServiceForSPLoc;
import com.example.erpnext.fcm.FCMService;
import com.example.erpnext.utils.Notify;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    private Button logout;
    private ImageView back;

    public ProfileFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        initViews(view);
        logout.setOnClickListener(v -> {
            Notify.Toast(getString(R.string.logged_out));
            if (isMyServiceRunning(BackgroundService.class)) {
                getContext().stopService(new Intent(getContext(), BackgroundService.class));
                getContext().stopService(new Intent(getContext(), BgServiceForSPLoc.class));
            }
            AppSession.clearSharedPref();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        });
        back.setOnClickListener(v -> {
            getActivity().onBackPressed();
        });
        return view;
    }

    private void initViews(View view) {
        logout = view.findViewById(R.id.logout);
        back = view.findViewById(R.id.back);
    }
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getContext().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}