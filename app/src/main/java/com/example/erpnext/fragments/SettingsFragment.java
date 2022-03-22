package com.example.erpnext.fragments;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.erpnext.R;
import com.example.erpnext.app.MainApp;
import com.example.erpnext.databinding.SettingsFragmentBinding;
import com.example.erpnext.databinding.WarehouseFragmentBinding;
import com.example.erpnext.viewmodels.WarehouseViewModel;

import java.util.Locale;

public class SettingsFragment extends Fragment {
    private SettingsFragmentBinding binding;
    private SettingsViewModel mViewModel;

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);
        MainApp.INSTANCE.setCurrentActivity(getActivity());
        binding = SettingsFragmentBinding.inflate(inflater, container, false);

        binding.language.setOnClickListener(v -> {
            selectLang();
        });
        binding.logs.setOnClickListener(v-> {
            fragmentTrx(LogsFragment.newInstance(), null, "LogsFragment");
        });
        binding.back.setOnClickListener(v ->{
            getActivity().onBackPressed();
        });

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);
        // TODO: Use the ViewModel
    }

    private void selectLang() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("Select Language");
        String[] items = {"English (Auto)", "Farsi"};
        alertDialog.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    SharedPreferences.Editor editor = getContext().getSharedPreferences("Settings", Context.MODE_PRIVATE).edit();
                    editor.putString("MyLang", "english");
                    editor.apply();
                    setLocale("en");
                    getActivity().recreate();
                } else if (which == 1) {
                    SharedPreferences.Editor editor = getContext().getSharedPreferences("Settings", Context.MODE_PRIVATE).edit();
                    editor.putString("MyLang", "farsi");
                    editor.apply();
                    setLocale("fa");
                    getActivity().recreate();
                }
            }
        });
        AlertDialog alert = alertDialog.create();
        alert.show();
    }
    public void fragmentTrx(Fragment fragment, Bundle bundle, String tag) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment, tag);
        fragment.setArguments(bundle);
        transaction.addToBackStack(fragment.getTag());
        transaction.commitAllowingStateLoss();
    }
    private void setLocale(String lang) {

        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getActivity().getBaseContext().getResources().updateConfiguration(configuration, getActivity().getBaseContext().getResources().getDisplayMetrics());

//        SharedPreferences.Editor editor = getContext().getSharedPreferences("Settings", Context.MODE_PRIVATE).edit();
//        editor.putString("MyLang", lang);
//        editor.apply();
    }

//    private void loadLocale() {
//        SharedPreferences preferences = getContext().getSharedPreferences("Settings", Context.MODE_PRIVATE);
//        String language = preferences.getString("MyLang", "");
//        setLocale(language);
//    }

}