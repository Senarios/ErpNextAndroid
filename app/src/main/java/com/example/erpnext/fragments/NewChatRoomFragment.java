package com.example.erpnext.fragments;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.erpnext.R;
import com.example.erpnext.viewmodels.NewChatRoomViewModel;

public class NewChatRoomFragment extends Fragment {

    private NewChatRoomViewModel mViewModel;

    public static NewChatRoomFragment newInstance() {
        return new NewChatRoomFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.new_chat_room_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(NewChatRoomViewModel.class);
        // TODO: Use the ViewModel
    }

}