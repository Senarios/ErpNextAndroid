package com.example.erpnext.fragments;

import static android.app.Activity.RESULT_OK;
import static com.example.erpnext.utils.RequestCodes.ADD_NEW_LEAD;
import static com.example.erpnext.utils.RequestCodes.PICKFILE_RESULT_CODE;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.adapters.AssigneesAdapter;
import com.example.erpnext.adapters.AttachmentsAdapter;
import com.example.erpnext.adapters.SelectedAssigneesAdapter;
import com.example.erpnext.adapters.TagsAdapter;
import com.example.erpnext.adapters.viewHolders.AssigneesViewHolder;
import com.example.erpnext.adapters.viewHolders.AttachmentsViewHolder;
import com.example.erpnext.adapters.viewHolders.SelectedAssigneesViewHolder;
import com.example.erpnext.adapters.viewHolders.TagsViewHolder;
import com.example.erpnext.callbacks.AssigneeCallback;
import com.example.erpnext.callbacks.SelectedAssigneeCallback;
import com.example.erpnext.models.Assignment;
import com.example.erpnext.models.Attachment;
import com.example.erpnext.models.SearchResult;
import com.example.erpnext.network.Network;
import com.example.erpnext.network.NetworkCall;
import com.example.erpnext.network.OnNetworkResponse;
import com.example.erpnext.network.serializers.requestbody.SearchLinkWithFiltersRequestBody;
import com.example.erpnext.network.serializers.response.AddAssigneesResponse;
import com.example.erpnext.network.serializers.response.AttachmentResponse;
import com.example.erpnext.network.serializers.response.BaseResponse;
import com.example.erpnext.network.serializers.response.RemoveAssigneeResponse;
import com.example.erpnext.network.serializers.response.SearchLinkResponse;
import com.example.erpnext.network.serializers.response.TagSuggestionsResponse;
import com.example.erpnext.utils.Constants;
import com.example.erpnext.utils.DateTime;
import com.example.erpnext.utils.FileUpload;
import com.example.erpnext.utils.FileWriter;
import com.example.erpnext.utils.Notify;
import com.example.erpnext.utils.RequestCodes;
import com.example.erpnext.utils.Utils;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFragment extends Fragment implements View.OnClickListener, OnNetworkResponse, SelectedAssigneeCallback, DateTime.datePickerCallback, AdapterView.OnItemSelectedListener, AssigneeCallback {
    String selectedPriority, bundleKey, tag;
    File attachedFile;
    long delay = 1000; // 1 seconds after user stops typing
    long last_text_edit = 0;
    private final Runnable input_finish_checker = new Runnable() {
        public void run() {
            if (System.currentTimeMillis() > (last_text_edit + delay - 500)) {
                getTagSuggestion(tag);
            }
        }
    };
    Handler handler = new Handler();
    boolean tagSelected = false;
    String encoded;
    private ImageView back;
    private TextView addNewAssignee, title;
    private RecyclerView assigneesRV, selectedAssigneeRV;
    private AssigneesAdapter assigneesAdapter;
    private TagsAdapter tagsAdapter;
    private AttachmentsAdapter attachmentsAdapter;
    private SelectedAssigneesAdapter selectedAssigneesAdapter;
    private Dialog dialog;
    private TextView completeBy;
    private EditText comment;
    private AutoCompleteTextView editText;

    public AddFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AddFragment newInstance() {
        AddFragment fragment = new AddFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        initViews(view);
        setClickisteners();
        Bundle bundle = getArguments();
        if (bundle.containsKey("key")) {
            bundleKey = bundle.getString("key");

            if (bundleKey.equalsIgnoreCase("assignment")) {
                title.setText("Assignees");
                setAssigneesAdapter(POSProfileDetailFragment.docDetail.getDocinfo().getAssignments());
            } else if (bundleKey.equalsIgnoreCase("tag")) {
                title.setText("Tags");
                setTagsAdapter(POSProfileDetailFragment.docDetail.getDocinfo().getTags());
            } else if (bundleKey.equalsIgnoreCase("attachments")) {
                title.setText("Attachments");
                setAttachmentsAdapter(POSProfileDetailFragment.docDetail.getDocinfo().getAttachments());
            }
        }
        return view;

    }

    private void setClickisteners() {
        back.setOnClickListener(this);
        addNewAssignee.setOnClickListener(this);

    }

    private void initViews(View view) {
        assigneesRV = view.findViewById(R.id.assigneesRV);
        addNewAssignee = view.findViewById(R.id.add_new_assignee);
        title = view.findViewById(R.id.title);
        back = view.findViewById(R.id.back);


    }

    private void setAssigneesAdapter(List<Assignment> assignments) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        assigneesAdapter = new AssigneesAdapter(getContext(), assignments, this);
        assigneesRV.setLayoutManager(linearLayoutManager);
        assigneesRV.setAdapter(assigneesAdapter);
    }

    private void setAttachmentsAdapter(List<Attachment> attachments) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        attachmentsAdapter = new AttachmentsAdapter(getContext(), attachments, this);
        assigneesRV.setLayoutManager(linearLayoutManager);
        assigneesRV.setAdapter(attachmentsAdapter);
    }

    private void setTagsAdapter(String tags) {
        String[] words = tags.split(",");
        List<String> tagList = new ArrayList<>();
        for (String word : words) {
            if (!word.equalsIgnoreCase("")) {
                tagList.add(word);
            }
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        tagsAdapter = new TagsAdapter(getContext(), tagList, this);
        assigneesRV.setLayoutManager(linearLayoutManager);
        assigneesRV.setAdapter(tagsAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                getActivity().onBackPressed();
                break;
            case R.id.add_new_assignee:
                switch (bundleKey) {
                    case "assignment":
                        showAddNewAssigneeDialog();
                        break;
                    case "tag":
                        showAddNewTagDialog();
                        break;
                    case "attachments":
                        selectAttachment();
                        break;
                }
                break;
        }
    }

    private void selectAttachment() {
        try {
            if (EasyPermissions.hasPermissions(getContext(), Constants.CAMERA_PERMISSIONS)) {
                final CharSequence[] options = {"Choose From Gallery", "File(PDF)", "Cancel"};
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Select Option");
                builder.setItems(options, (dialog, item) -> {
                    if (options[item].equals("Choose From Gallery")) {
                        dialog.dismiss();
                        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(pickPhoto, ADD_NEW_LEAD);

                    } else if (options[item].equals("File(PDF)")) {
                        Intent intentPDF = new Intent(Intent.ACTION_GET_CONTENT);
                        intentPDF.setType("application/pdf");
                        intentPDF.addCategory(Intent.CATEGORY_OPENABLE);
                        startActivityForResult(Intent.createChooser(intentPDF, "Choose File"), PICKFILE_RESULT_CODE);
                    } else if (options[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA},
                        RequestCodes.WRITE_PERMISSION);
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), R.string.require_permission, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


    private void setSelectedAssigneeAdapter(List<String> userList) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        selectedAssigneesAdapter = new SelectedAssigneesAdapter(getContext(), userList, this);
        selectedAssigneeRV.setLayoutManager(gridLayoutManager);
        selectedAssigneeRV.setAdapter(selectedAssigneesAdapter);
    }

    private void setSpinner(String[] list, Spinner spinner) {
        ArrayAdapter<CharSequence> spinnerAdapter = new ArrayAdapter<CharSequence>(getContext(), R.layout.checkout_spinner_text, list);
        spinnerAdapter.setDropDownViewResource(R.layout.checkout_spinner_layout);
        spinner.setAdapter(spinnerAdapter);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onDeleteClick(String item, SelectedAssigneesViewHolder viewHolder, int position) {
        selectedAssigneesAdapter.removeItem(position);
    }

    @Override
    public void onDeleteAssigneeClick(Assignment assignment, AssigneesViewHolder viewHolder, int position) {
        removeAssignee(assignment);
    }

    @Override
    public void onDeleteTagClick(String tag, TagsViewHolder viewHolder, int position) {
        RemoveTag(tag);
    }

    @Override
    public void onDeleteAttachmentClick(Attachment attachment, AttachmentsViewHolder viewHolder, int position) {
        RemoveAttachment(attachment);
    }

    @Override
    public void onAttachmentClick(Attachment attachment, AttachmentsViewHolder viewHolder, int position) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://75.119.143.175" + attachment.getFileUrl()));
        startActivity(browserIntent);
    }

    @Override
    public void onSelected(String date) {
        completeBy.setText(date);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position != 0) {
            selectedPriority = parent.getItemAtPosition(position).toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == PICKFILE_RESULT_CODE) {
                Uri uri = data.getData();
                Cursor c = getActivity().getContentResolver().query(data.getData(), null, null, null, null);
                if (c != null) {
                    c.moveToFirst();
                }
                String filename = c.getString(c.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                //Do something with files
                String path = FileWriter.getFilePathFromURI(getContext(), uri, filename);
                attachedFile = new File(path);
                showAttachmentDialog(filename);
            } else if (requestCode == RequestCodes.ADD_NEW_LEAD) {
                Uri pickedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor cursor = getActivity().getContentResolver().query(pickedImage, filePath, null, null, null);
                cursor.moveToFirst();
                if (cursor != null) {
                    cursor.moveToFirst();
                }
                String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));
                attachedFile = new File(imagePath);
                showAttachmentDialog("Image");

            }
        }
    }

    private void showAddNewAssigneeDialog() {
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.add_new_assginee_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
        editText = dialog.findViewById(R.id.autoCompleteEdit);
        completeBy = dialog.findViewById(R.id.complete_by);
        comment = dialog.findViewById(R.id.comment);
        Spinner prioritySpinner = dialog.findViewById(R.id.priority_spinner);
        selectedAssigneeRV = dialog.findViewById(R.id.selected_assigneesRv);
        Button add = dialog.findViewById(R.id.add);
        List<String> lists = new ArrayList<>();
        lists.add("--Select Priority--");
        lists.add("Low");
        lists.add("Medium");
        lists.add("High");
        String[] list = lists.toArray(new String[0]);
        setSpinner(list, prioritySpinner);
        List<String> list1 = new ArrayList<>();
        setSelectedAssigneeAdapter(new ArrayList<String>());
        completeBy.setOnClickListener(v -> {
            DateTime.showDatePicker(getActivity(), this);
        });
        add.setOnClickListener(view -> {
            if (selectedAssigneesAdapter.getAllUsers() != null && !selectedAssigneesAdapter.getAllUsers().isEmpty()) {
                addAssignees();
            } else Notify.ToastLong(getString(R.string.select_one_assignee));
        });
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    getLinkSearch();
                }
            }
        });
        editText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                editText.setText("");
                if (selectedAssigneesAdapter == null) {
                    List<String> searchResults = new ArrayList<>();
                    searchResults.add(selectedItem);
                    setSelectedAssigneeAdapter(searchResults);
                } else selectedAssigneesAdapter.addItem(selectedItem);
            }
        });
    }

    private void showAddNewTagDialog() {
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.add_tag_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
        editText = dialog.findViewById(R.id.autoCompleteEdit);
        Button cancel = dialog.findViewById(R.id.cancel);
        Button add = dialog.findViewById(R.id.add);
        cancel.setOnClickListener(v -> {
            dialog.dismiss();
            editText.setText("");
        });
        add.setOnClickListener(view -> {
            if (editText.getText().toString() != null && !editText.getText().toString().equalsIgnoreCase("")) {
                if (!editText.getText().toString().equalsIgnoreCase("Tag")) {
                    dialog.dismiss();
                    AddTag(editText.getText().toString());
                } else Notify.Toast(getString(R.string.cannot_add_tag));
            } else Notify.Toast(getString(R.string.write_tag));
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //You need to remove this to run only once
                handler.removeCallbacks(input_finish_checker);
            }

            @Override
            public void afterTextChanged(Editable s) {
                //avoid triggering event when text is empty
                if (s.length() > 0) {
                    last_text_edit = System.currentTimeMillis();
                    tag = s.toString();
                    handler.postDelayed(input_finish_checker, delay);
                }
            }
        });
        editText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                editText.setText(selectedItem);
            }
        });
    }

    private void showAttachmentDialog(String filename) {
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.add_attachment_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
        editText = dialog.findViewById(R.id.autoCompleteEdit);
        TextView file_name = dialog.findViewById(R.id.filename);
        Button cancel = dialog.findViewById(R.id.cancel);
        Button add = dialog.findViewById(R.id.add);
        file_name.setText(filename);
        cancel.setOnClickListener(v -> {
            dialog.dismiss();
            attachedFile = null;
        });
        add.setOnClickListener(view -> {
            dialog.dismiss();
            uploadFile();
        });
    }

    private void uploadFile() {
        NetworkCall.make()
                .setCallback(this)
                .setTag(RequestCodes.API.UPLOAD_ATTACHMENT)
                .autoLoadingCancel(Utils.getLoading(getActivity(), "uploading..."))
                .enque(Network.apis().uploadAttachment(FileUpload.uploadAttachments(0, "", POSProfileDetailFragment.docDetail.getDocs().get(0).getName(), "POS Profile"),
                        FileUpload.requestBodyPDF("file", attachedFile)))
                .execute();
    }

    private void getLinkSearch() {
        NetworkCall.make()
                .setCallback(this)
                .setTag(RequestCodes.API.LINK_SEARCH)
                .autoLoadingCancel(Utils.getLoading(getActivity(), "searching..."))
                .enque(Network.apis().getSearchLinkWithFilters(new SearchLinkWithFiltersRequestBody("", "User", "0", "{\"user_type\":\"System User\",\"enabled\":1}", "POS Profile", "")))
                .execute();
    }

    private void removeAssignee(Assignment assignment) {
        NetworkCall.make()
                .setCallback(this)
                .setTag(RequestCodes.API.REMOVE_ASSIGNEE)
                .autoLoadingCancel(Utils.getLoading(getActivity(), "Removing..."))
                .enque(Network.apis().removeAssignee(assignment.getOwner(), "POS Profile", POSProfileDetailFragment.docDetail.getDocs().get(0).getName()))
                .execute();
    }

    private void AddTag(String tag) {
        NetworkCall.make()
                .setCallback(this)
                .setTag(RequestCodes.API.ADD_TAG)
                .autoLoadingCancel(Utils.getLoading(getActivity(), "Please wait"))
                .enque(Network.apis().addTag(tag, "POS Profile", POSProfileDetailFragment.docDetail.getDocs().get(0).getName()))
                .execute();
    }

    private void RemoveTag(String tag) {
        NetworkCall.make()
                .setCallback(this)
                .setTag(RequestCodes.API.REMOVE_TAG)
                .autoLoadingCancel(Utils.getLoading(getActivity(), "Removing..."))
                .enque(Network.apis().removeTag(tag, "POS Profile", POSProfileDetailFragment.docDetail.getDocs().get(0).getName()))
                .execute();
    }

    private void RemoveAttachment(Attachment attachment) {
        NetworkCall.make()
                .setCallback(this)
                .setTag(RequestCodes.API.REMOVE_ATTACHMENT)
                .autoLoadingCancel(Utils.getLoading(getActivity(), "Removing..."))
                .enque(Network.apis().removeAttachment(attachment.getName(), "POS Profile", POSProfileDetailFragment.docDetail.getDocs().get(0).getName()))
                .execute();
    }

    private void getTagSuggestion(String tag) {
        NetworkCall.make()
                .setCallback(this)
                .setTag(RequestCodes.API.GET_TAG_SUGGESTIONS)
                .enque(Network.apis().getTagSuggestion("POS Profile", tag))
                .execute();
    }

    private void addAssignees() {
        JSONArray jsonArray = new JSONArray();
        try {
            jsonArray = new JSONArray(new Gson().toJson(selectedAssigneesAdapter.getAllUsers()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        NetworkCall.make()
                .setCallback(this)
                .setTag(RequestCodes.API.ADD_ASSIGNEES)
                .autoLoadingCancel(Utils.getLoading(getActivity(), "Please wait"))
                .enque(Network.apis().addAssignees(0, jsonArray, comment.getText().toString(), completeBy.getText().toString(), selectedPriority, "POS Profile", POSProfileDetailFragment.docDetail.getDocs().get(0).getName(), false, false))
                .execute();
    }

    @Override
    public void onSuccess(Call call, Response response, Object tag) throws ParseException {
        if ((int) tag == RequestCodes.API.LINK_SEARCH) {
            SearchLinkResponse res = (SearchLinkResponse) response.body();
            List<String> list = new ArrayList<>();
            if (res != null && !res.getResults().isEmpty()) {
                for (SearchResult searchResult : res.getResults()) {
                    list.add(searchResult.getValue());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_list_item_1, list);
                editText.setAdapter(adapter);
            }
        } else if ((int) tag == RequestCodes.API.GET_TAG_SUGGESTIONS) {
            TagSuggestionsResponse res = (TagSuggestionsResponse) response.body();
            List<String> list = new ArrayList<>();
            if (res != null && !res.getTags().isEmpty()) {
                for (String tagSuggestion : res.getTags()) {
                    list.add(tagSuggestion);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_list_item_1, list);
                editText.setAdapter(adapter);
                editText.showDropDown();
            }
        } else if ((int) tag == RequestCodes.API.ADD_TAG) {
            BaseResponse res = (BaseResponse) response.body();
            tagsAdapter.addItem(res.getMessage());
            editText.setText("");
            Notify.Toast(getString(R.string.added));
        } else if ((int) tag == RequestCodes.API.ADD_ASSIGNEES) {
            AddAssigneesResponse res = (AddAssigneesResponse) response.body();
            dialog.dismiss();
            setAssigneesAdapter(res.getAssigneeMessages());
            Notify.Toast(getString(R.string.successfully_created));
        } else if ((int) tag == RequestCodes.API.REMOVE_ASSIGNEE) {
            RemoveAssigneeResponse res = (RemoveAssigneeResponse) response.body();
            setAssigneesAdapter(res.getAssignmentList());
            Notify.Toast(getString(R.string.removed));
        } else if ((int) tag == RequestCodes.API.REMOVE_TAG) {
            BaseResponse res = (BaseResponse) response.body();
            getActivity().onBackPressed();
            Notify.Toast(getString(R.string.removed));
        } else if ((int) tag == RequestCodes.API.UPLOAD_ATTACHMENT) {
            AttachmentResponse res = (AttachmentResponse) response.body();
            if (res.getAttachmentMessage() != null) Notify.Toast(getString(R.string.successfully_uploaded));
            getActivity().onBackPressed();
        } else if ((int) tag == RequestCodes.API.REMOVE_ATTACHMENT) {
            BaseResponse res = (BaseResponse) response.body();
            getActivity().onBackPressed();
            Notify.Toast(getString(R.string.removed));
        }
    }

    @Override
    public void onFailure(Call call, BaseResponse response, Object tag) {

    }

}