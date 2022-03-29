package com.example.erpnext.fragments;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.example.erpnext.R;
import com.example.erpnext.activities.AddCustomerActivity;
import com.example.erpnext.adapters.ShowCustomerAdapter;
import com.example.erpnext.adapters.StockListsAdapter;
import com.example.erpnext.app.MainApp;
import com.example.erpnext.callbacks.ProfilesCallback;
import com.example.erpnext.databinding.CustomerFragmentBinding;
import com.example.erpnext.models.SearchResult;
import com.example.erpnext.models.ShowCustomerDatum;
import com.example.erpnext.models.ShowCustomerRes;
import com.example.erpnext.network.ApiServices;
import com.example.erpnext.network.serializers.response.SearchLinkResponse;
import com.example.erpnext.repositories.CustomersRepo;
import com.example.erpnext.utils.RequestCodes;
import com.example.erpnext.utils.Utils;
import com.example.erpnext.viewmodels.CustomersViewModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CustomersFragment extends Fragment implements View.OnClickListener, ProfilesCallback, ShowCustomerAdapter.CustomerClick {

    public boolean isItemsEnded = false;
    String doctype = "Customer";
    CustomerFragmentBinding binding;
    private StockListsAdapter stockListsAdapter;
    private int limitStart = 0;
    Dialog dialog;
    private CustomersViewModel mViewModel;
    ShowCustomerAdapter showCustomerAdapter;
    private List<ShowCustomerDatum> list = new ArrayList<>();
    boolean clear = true;

    public static CustomersFragment newInstance() {
        return new CustomersFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(CustomersViewModel.class);
        MainApp.INSTANCE.setCurrentActivity(getActivity());
        binding = CustomerFragmentBinding.inflate(inflater, container, false);

        setClickListeners();
        enlistCustomers();
        binding.filterTV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    binding.search.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_search_24));
                    clear = true;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
//        setSearchAdapter(getActivity(), binding.filterTV, list);
//        getItems();
//        setObservers();
//        binding.listRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                if (Utils.isNetworkAvailable()) {
//                    if (Utils.isLastItemDisplaying(binding.listRv)) {
//                        if (!isItemsEnded && stockListsAdapter != null && stockListsAdapter.getAllItems() != null && stockListsAdapter.getAllItems().size() > 10) {
//                            limitStart = limitStart + 20;
//                            getItems();
//                        }
//                    }
//                }
//                super.onScrolled(recyclerView, dx, dy);
//            }
//        });
        return binding.getRoot();
    }

    private void getItems() {
        mViewModel.getItemsApi(doctype,
                20,
                true,
                "`tabCustomer`.`modified` desc",
                limitStart);
    }

    private void setObservers() {
        mViewModel.getItems().observe(getActivity(), lists -> {
            if (lists != null) {
                setItemsAdapter(lists);
            }
        });
    }

    private void setClickListeners() {
        binding.back.setOnClickListener(this);
        binding.addNew.setOnClickListener(this);
        binding.search.setOnClickListener(this);
    }

    private void setItemsAdapter(List<List<String>> profilesList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        stockListsAdapter = new StockListsAdapter(getContext(), profilesList, doctype, this);
        binding.listRv.setLayoutManager(linearLayoutManager);
        binding.listRv.setAdapter(stockListsAdapter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                getActivity().onBackPressed();
                break;
            case R.id.add_new:
                startActivityForResult(new Intent(getActivity(), AddCustomerActivity.class), RequestCodes.ADD_CUSTOMER);
                break;
            case R.id.search:
                if (clear) {
                    if (!binding.filterTV.getText().toString().trim().isEmpty()) {
                        binding.search.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_close_24));
                        clear = false;
                        List<ShowCustomerDatum> filteredList = filteredList(binding.filterTV.getText().toString().trim());
                        setCustomerAdapter(filteredList);
                    }
                } else {
                    binding.search.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_search_24));
                    clear = true;
                    binding.filterTV.setText("");
                    setCustomerAdapter(list);
                }
                break;
        }
    }

    private List<ShowCustomerDatum> filteredList(String trim) {
        trim = trim.toLowerCase(Locale.ROOT);
        List<ShowCustomerDatum> newList = new ArrayList<>();
        if(!trim.isEmpty()){
            for(ShowCustomerDatum customer: list){
                if(customer.getName().toLowerCase(Locale.ROOT).contains(trim))
                    newList.add(customer);
            }
        }
        return newList;
    }

    @Override
    public void onProfileClick(List<String> list) {

    }

    @Override
    public void onLongClick(List<String> list, int position) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RequestCodes.ADD_CUSTOMER) {
            if (resultCode == RESULT_OK) {
                CustomersRepo.getInstance().items.setValue(new ArrayList<>());
                limitStart = 0;
                getItems();
            }
        }
    }

    @Override
    public void onResume() {
        MainApp.getAppContext().setCurrentActivity(getActivity());
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        CustomersRepo.getInstance().items.setValue(new ArrayList<>());
    }

    private void enlistCustomers() {
        Utils.showLoading(getActivity());
        Retrofit retrofit = new Retrofit.Builder().baseUrl(getString(R.string.frappeurl))
                .addConverterFactory(GsonConverterFactory.create()).build();
        ApiServices apiServices = retrofit.create(ApiServices.class);
        Call<ShowCustomerRes> call = apiServices.getAllCustomers();
        call.enqueue(new Callback<ShowCustomerRes>() {
            @Override
            public void onResponse(Call<ShowCustomerRes> call, Response<ShowCustomerRes> response) {
                if (response.isSuccessful()) {
                    Utils.dismiss();
                    ShowCustomerRes resObj = response.body();
                    list = new ArrayList<>();
                    list = resObj.getData();
                    setCustomerAdapter(list);
//                    setSearchAdapter(getActivity(), binding.filterTV, list);

                } else {
                    Utils.dismiss();
                    Toast.makeText(getContext(), "Process Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ShowCustomerRes> call, Throwable t) {
                Utils.dismiss();
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void doClick(String name, String phone, String ref, String image, String qr) {
        showCustomerDialog(name, phone, ref, image,qr);
    }

    private void showCustomerDialog(String name, String phone, String ref, String image,String qr) {
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.show_customer_layout);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window window = dialog.getWindow();
        dialog.setCancelable(false);
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
        ProgressBar progressBar = dialog.findViewById(R.id.customerProgress);
        ImageView customer_image = dialog.findViewById(R.id.show_image);
        ImageView customer_qr = dialog.findViewById(R.id.show_qr);
        RelativeLayout print_qr = dialog.findViewById(R.id.print_qr);
        RelativeLayout share_qr = dialog.findViewById(R.id.share_qr);
        TextView tvname = dialog.findViewById(R.id.show_cus_name);
        TextView tvphone = dialog.findViewById(R.id.show_phone_no);
        TextView tvrefernce = dialog.findViewById(R.id.show_reference);
        Button cancel = dialog.findViewById(R.id.cancel);
        tvname.setText(name);
        tvphone.setText(phone);
        tvrefernce.setText(ref);
        progressBar.setVisibility(View.VISIBLE);
        Glide.with(getContext()).load(getString(R.string.frappeurl) + image)
                .placeholder(R.drawable.logo)
                .addListener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(customer_image);
        Glide.with(getContext()).load(getString(R.string.frappeurl) + qr)
                .placeholder(R.drawable.logo)
                .addListener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(customer_qr);

        share_qr.setOnClickListener(v->{
            Utils.showLoading(getActivity());
            Glide.with(getContext()).asBitmap()
                    .load(getString(R.string.frappeurl) + qr).skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(new SimpleTarget<Bitmap>(250,250) {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            Intent intent = new Intent(Intent.ACTION_SEND);
                            intent.putExtra(Intent.EXTRA_TEXT, "Hey view/download this image");
                            String path = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), resource, "", null);
                            Uri screenshotUri = Uri.parse(path);
                            intent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
                            intent.setType("image/*");
                            Utils.dismiss();
                            startActivity(Intent.createChooser(intent, "Share image via..."));

                        }
                    });

        });
        print_qr.setOnClickListener(v->{
            saveReceipt();
        });
        cancel.setOnClickListener(v -> {
            dialog.dismiss();
        });
    }

    private void setCustomerAdapter(List<ShowCustomerDatum> list){
        Collections.reverse(list);
        showCustomerAdapter = new ShowCustomerAdapter((ArrayList<ShowCustomerDatum>) list, getContext(), CustomersFragment.this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        binding.listRv.setLayoutManager(layoutManager);
        binding.listRv.setAdapter(showCustomerAdapter);
        showCustomerAdapter.notifyDataSetChanged();
    }

    private void setSearchAdapter(Activity activity, AutoCompleteTextView textView, List<ShowCustomerDatum> list) {
        List<String> list2 = new ArrayList<>();
        for (ShowCustomerDatum searchResult : list) {
            list2.add(searchResult.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity,
                android.R.layout.simple_list_item_1, list2);
        textView.setAdapter(adapter);
        textView.showDropDown();

        binding.filterTV.setOnItemClickListener((parent, view12, position, id) -> {
//            item_group = (String) parent.getItemAtPosition(position);
//            limitSet = 0;
//            isItemsEnded = false;
//            if (Utils.isNetworkAvailable()) getItems(binding.filterWarehouse.getText().toString());
//            else loadFromLoacal();
        });
    }
    private void saveReceipt() {
        View mainView;
        mainView = getActivity().getWindow().getDecorView()
                .findViewById(android.R.id.content);
        mainView.setDrawingCacheEnabled(true);
// This is the bitmap for the main activity
        Bitmap bitmap = mainView.getDrawingCache();

        View dialogView = dialog.getWindow().getDecorView().findViewById(R.id.show_qr);
        int[] location = new int[2];
        mainView.getLocationOnScreen(location);
        int[] location2 = new int[2];
        dialogView.getLocationOnScreen(location2);

        dialogView.setDrawingCacheEnabled(true);
// This is the bitmap for the dialog view
        Bitmap bitmap2 = dialogView.getDrawingCache();

        Canvas canvas = new Canvas(bitmap);
// Need to draw the dialogView into the right position
        canvas.drawBitmap(bitmap2, location2[0] - location[0], location2[1] - location[1],
                new Paint());

        String filename = String.valueOf(System.currentTimeMillis());// filename to save
        File myPath = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + Environment.DIRECTORY_DOWNLOADS + "/" + filename + ".jpg");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(myPath);
            bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, fos);
//            fos.flush();
            fos.close();
            File pdfFile = Utils.convertToPDF(bitmap2, myPath);
            Utils.openPDFFile(pdfFile, getActivity());
            Utils.loadImageToGallery(getActivity(), myPath);
//            dialog.dismiss();
//            final Handler handler = new Handler(Looper.getMainLooper());
//            handler.postDelayed(() -> getActivity().onBackPressed(), 500);


        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}