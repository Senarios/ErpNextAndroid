package com.example.erpnext.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.adapters.BCCAdapter;
import com.example.erpnext.adapters.CCAdapter;
import com.example.erpnext.adapters.SelectedAssigneesAdapter;
import com.example.erpnext.adapters.StockListsAdapter;
import com.example.erpnext.adapters.viewHolders.BCCViewHolder;
import com.example.erpnext.adapters.viewHolders.CCViewHolder;
import com.example.erpnext.adapters.viewHolders.SelectedAssigneesViewHolder;
import com.example.erpnext.app.MainApp;
import com.example.erpnext.callbacks.BCCCallback;
import com.example.erpnext.callbacks.CCCallback;
import com.example.erpnext.callbacks.ProfilesCallback;
import com.example.erpnext.callbacks.SelectedAssigneeCallback;
import com.example.erpnext.databinding.CommunicationsFragmentBinding;
import com.example.erpnext.repositories.CommunicationsRepo;
import com.example.erpnext.utils.Notify;
import com.example.erpnext.utils.Utils;
import com.example.erpnext.viewmodels.CommunicationsViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommunicationsFragment extends Fragment implements View.OnClickListener, ProfilesCallback, SelectedAssigneeCallback, CCCallback, BCCCallback {

    public boolean isItemsEnded = false;
    String doctype = "Communication";
    public static String selectedadapter;
    CommunicationsFragmentBinding binding;
    RecyclerView selectedToContactsRv, selectedCCRv, selectedBCCRv;
    Dialog dialog;
    HashMap<String, String> data = new HashMap<>();
    SelectedAssigneesAdapter selectedToContactsAdapter;
    CCAdapter selectedCCAdapter;
    BCCAdapter selectedBCCAdapter;
    AutoCompleteTextView toEdit, ccEdit, bccEdit;
    AutoCompleteTextView selectedEdit;
    private StockListsAdapter stockListsAdapter;
    private int limitStart = 0;
    private CommunicationsViewModel mViewModel;

    public static CommunicationsFragment newInstance() {
        return new CommunicationsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(CommunicationsViewModel.class);
        MainApp.INSTANCE.setCurrentActivity(getActivity());
        binding = CommunicationsFragmentBinding.inflate(inflater, container, false);

        setClickListeners();
        getItems();
        setObservers();
        binding.listRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (Utils.isNetworkAvailable()) {
                    if (Utils.isLastItemDisplaying(binding.listRv)) {
                        if (!isItemsEnded && stockListsAdapter != null && stockListsAdapter.getAllItems() != null && stockListsAdapter.getAllItems().size() > 10) {
                            limitStart = limitStart + 20;
                            getItems();
                        }
                    }
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        return binding.getRoot();
    }

    private void getItems() {
        mViewModel.getItemsApi(doctype,
                20,
                true,
                "`tabCommunication`.`modified` desc",
                limitStart);
    }

    private void setObservers() {
        mViewModel.getItems().observe(getActivity(), lists -> {
            if (lists != null) {
                setItemsAdapter(lists);
            }
        });
        mViewModel.getContacts().observe(getActivity(), contacts -> {
            if (contacts != null) {
                Utils.setSearchAdapter(getActivity(), selectedEdit, contacts);
                CommunicationsRepo.getInstance().contacts.setValue(null);
            }
        });

    }

    private void setSaveObserver() {
        mViewModel.emailSent().observe(this, doc -> {
            if (doc != null) {
                Notify.Toast(getString(R.string.successfully_created));
                selectedBCCAdapter.clear();
                selectedCCAdapter.clear();
                selectedToContactsAdapter.clear();
                selectedEdit = null;
                dialog.dismiss();
                CommunicationsRepo.getInstance().savedDoc.setValue(null);
                CommunicationsRepo.getInstance().contacts.setValue(null);
                limitStart = 0;
                isItemsEnded = false;
                getItems();

            }
        });
    }

    private void setClickListeners() {
        binding.back.setOnClickListener(this);
        binding.addNew.setOnClickListener(this);
    }

    private void setItemsAdapter(List<List<String>> profilesList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        stockListsAdapter = new StockListsAdapter(getContext(), profilesList, doctype, this);
        binding.listRv.setLayoutManager(linearLayoutManager);
        binding.listRv.setAdapter(stockListsAdapter);
    }

    private void setSelectedToContactsRv(List<String> profilesList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        selectedToContactsAdapter = new SelectedAssigneesAdapter(getContext(), profilesList, this);
        selectedToContactsRv.setLayoutManager(linearLayoutManager);
        selectedToContactsRv.setAdapter(selectedToContactsAdapter);
    }

    private void setSelectedCCRv(List<String> profilesList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        selectedCCAdapter = new CCAdapter(getContext(), profilesList, this);
        selectedCCRv.setLayoutManager(linearLayoutManager);
        selectedCCRv.setAdapter(selectedCCAdapter);
    }

    private void setSelectedBCCRv(List<String> profilesList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        selectedBCCAdapter = new BCCAdapter(getContext(), profilesList, this);
        selectedBCCRv.setLayoutManager(linearLayoutManager);
        selectedBCCRv.setAdapter(selectedBCCAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                getActivity().onBackPressed();
                break;
            case R.id.add_new:
                showAddDialog();
                break;
        }
    }

    @Override
    public void onProfileClick(List<String> list) {

    }

    @Override
    public void onLongClick(List<String> list, int position) {

    }

    private void showAddDialog() {
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.add_new_communications_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
        toEdit = dialog.findViewById(R.id.to_edit);
        bccEdit = dialog.findViewById(R.id.bcc);
        ccEdit = dialog.findViewById(R.id.cc);
        AutoCompleteTextView subjectEdit = dialog.findViewById(R.id.subject);
        ImageView expandOptions = dialog.findViewById(R.id.expand_options);
        ImageView searchTo = dialog.findViewById(R.id.search_to);
        ImageView searchCC = dialog.findViewById(R.id.search_cc);
        ImageView searchBcc = dialog.findViewById(R.id.search_bcc);
        LinearLayout cc_bcc_layout = dialog.findViewById(R.id.cc_bcc_layout);
        selectedToContactsRv = dialog.findViewById(R.id.selected_contactsRv);
        selectedCCRv = dialog.findViewById(R.id.selected_cc_Rv);
        selectedBCCRv = dialog.findViewById(R.id.selected_bcc_Rv);
        CheckBox sendMeCopy = dialog.findViewById(R.id.send_me_copy);
        CheckBox sendReceipt = dialog.findViewById(R.id.send_receipt);
        EditText content = dialog.findViewById(R.id.message);
        Button send = dialog.findViewById(R.id.send);

        toEdit.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                selectedEdit = toEdit;
                mViewModel.getContacts(toEdit.getText().toString());
            }
        });
        ccEdit.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                selectedEdit = ccEdit;
                mViewModel.getContacts(ccEdit.getText().toString());
            }
        });
        bccEdit.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                selectedEdit = bccEdit;
                mViewModel.getContacts(bccEdit.getText().toString());
            }
        });
        searchTo.setOnClickListener(v -> {
            selectedEdit = toEdit;
            mViewModel.getContacts(toEdit.getText().toString());
        });
        searchCC.setOnClickListener(v -> {
            selectedEdit = ccEdit;
            mViewModel.getContacts(ccEdit.getText().toString());
        });
        searchBcc.setOnClickListener(v -> {
            selectedEdit = bccEdit;
            mViewModel.getContacts(bccEdit.getText().toString());
        });
        expandOptions.setOnClickListener(v -> {
            if (cc_bcc_layout.getVisibility() == View.VISIBLE) {
                cc_bcc_layout.setVisibility(View.GONE);
                if (selectedCCAdapter != null) selectedCCAdapter.clear();
                if (selectedBCCAdapter != null) selectedBCCAdapter.clear();
            } else cc_bcc_layout.setVisibility(View.VISIBLE);
        });

        ccEdit.setOnItemClickListener((parent, view, position, id) -> {
            String selectedItem = (String) parent.getItemAtPosition(position);
            ccEdit.setText("");
            if (selectedCCAdapter == null) {
                List<String> searchResults = new ArrayList<>();
                searchResults.add(selectedItem);
                setSelectedCCRv(searchResults);
            } else selectedCCAdapter.addItem(selectedItem);
        });
        bccEdit.setOnItemClickListener((parent, view, position, id) -> {
            String selectedItem = (String) parent.getItemAtPosition(position);
            bccEdit.setText("");
            if (selectedBCCAdapter == null) {
                List<String> searchResults = new ArrayList<>();
                searchResults.add(selectedItem);
                setSelectedBCCRv(searchResults);
            } else selectedBCCAdapter.addItem(selectedItem);
        });
        toEdit.setOnItemClickListener((parent, view, position, id) -> {
            String selectedItem = (String) parent.getItemAtPosition(position);
            toEdit.setText("");
            if (selectedToContactsAdapter == null) {
                List<String> searchResults = new ArrayList<>();
                searchResults.add(selectedItem);
                setSelectedToContactsRv(searchResults);
            } else selectedToContactsAdapter.addItem(selectedItem);
        });
        send.setOnClickListener(v -> {
            if (selectedToContactsAdapter != null && selectedToContactsAdapter.getAllUsers() != null) {
                if (!subjectEdit.getText().toString().isEmpty()) {
                    if (sendMeCopy.isChecked()) {
                        data.put("send_me_a_copy", "1");
                    } else data.put("send_me_a_copy", "0");
                    if (sendReceipt.isChecked()) {
                        data.put("read_receipt", "1");
                    } else data.put("read_receipt", "0");

                    if (selectedToContactsAdapter != null && selectedToContactsAdapter.getAllUsers() != null && !selectedToContactsAdapter.getAllUsers().isEmpty()) {
                        String selectedCC = "";
                        for (String contact : selectedToContactsAdapter.getAllUsers()) {
                            if (selectedCC.isEmpty()) {
                                selectedCC = contact;
                            } else {
                                selectedCC = selectedCC + "," + contact;
                            }
                        }
                        data.put("recipients", selectedCC);
                    }

                    if (selectedCCAdapter != null && selectedCCAdapter.getAllUsers() != null && !selectedCCAdapter.getAllUsers().isEmpty()) {
                        String selectedCC = "";
                        for (String contact : selectedCCAdapter.getAllUsers()) {
                            if (selectedCC.isEmpty()) {
                                selectedCC = contact;
                            } else {
                                selectedCC = selectedCC + "," + contact;
                            }
                        }
                        data.put("cc", selectedCC);
                    } else data.put("cc", "");
                    if (selectedBCCAdapter != null && selectedBCCAdapter.getAllUsers() != null && !selectedBCCAdapter.getAllUsers().isEmpty()) {
                        String selectedCC = "";
                        for (String contact : selectedBCCAdapter.getAllUsers()) {
                            if (selectedCC.isEmpty()) {
                                selectedCC = contact;
                            } else {
                                selectedCC = selectedCC + "," + contact;
                            }
                        }
                        data.put("bcc", selectedCC);
                    } else data.put("bcc", "");
                    data.put("content", content.getText().toString());
                    mViewModel.sendMail(data);
                    setSaveObserver();

                } else Notify.Toast(getString(R.string.add_subject));
            } else Notify.Toast(getString(R.string.add_receiver_contact));
        });
    }

    @Override
    public void onDeleteClick(String item, SelectedAssigneesViewHolder viewHolder, int position) {
        selectedToContactsAdapter.removeItem(position);
    }

    @Override
    public void onDeleteBCC(String item, BCCViewHolder viewHolder, int position) {
        selectedBCCAdapter.removeItem(position);
    }

    @Override
    public void onDeleteCC(String item, CCViewHolder viewHolder, int position) {
        selectedCCAdapter.removeItem(position);
    }

    @Override
    public void onLongClick(String list, int position) {

    }

    @Override
    public void onResume() {
        MainApp.getAppContext().setCurrentActivity(getActivity());
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        CommunicationsRepo.getInstance().items.setValue(new ArrayList<>());
        CommunicationsRepo.getInstance().contacts.setValue(null);
        CommunicationsRepo.getInstance().savedDoc.setValue(null);
    }


}