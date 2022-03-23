package com.example.erpnext.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Environment;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.adapters.InvoiceItemsAdapter;
import com.example.erpnext.adapters.PosProfileListAdapter;
import com.example.erpnext.app.MainApp;
import com.example.erpnext.callbacks.ProfilesCallback;
import com.example.erpnext.models.Invoice;
import com.example.erpnext.models.InvoiceItem;
import com.example.erpnext.models.Profile;
import com.example.erpnext.models.ProfileDoc;
import com.example.erpnext.models.SearchResult;
import com.example.erpnext.network.Network;
import com.example.erpnext.network.NetworkCall;
import com.example.erpnext.network.OnNetworkResponse;
import com.example.erpnext.network.serializers.requestbody.SearchLinkWithFiltersRequestBody;
import com.example.erpnext.network.serializers.response.BaseResponse;
import com.example.erpnext.network.serializers.response.SearchLinkResponse;
import com.example.erpnext.repositories.POSInvoicesRepo;
import com.example.erpnext.roomDB.data.DBSearchLink;
import com.example.erpnext.roomDB.data.Room;
import com.example.erpnext.utils.DateTime;
import com.example.erpnext.utils.Notify;
import com.example.erpnext.utils.RequestCodes;
import com.example.erpnext.utils.Utils;
import com.example.erpnext.viewmodels.POSInvoiceViewModel;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PosInvoicesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PosInvoicesFragment extends BaseFragment implements View.OnClickListener, ProfilesCallback, OnNetworkResponse {
    private final List<Profile> profileList = new ArrayList<>();
    public boolean isProfilesEnded = false;
    public boolean isActionPerformed = false;
    List<List<String>> profilesList = new ArrayList<>();
    int limitStart = 0;
    private String selectedCustomer = "";
    private ProfileDoc profileDoc;
    String searchDoctype = "", query = "", filters = "", baseTotal, changeAmount = "0", outstandingAmount = "0", paidAmount = "0";
    String doctype = "POS Invoice";
    Dialog dialog;
    private RecyclerView posInvoicesRv;
    private PosProfileListAdapter posInvoiceAdapter;
    private POSInvoiceViewModel posInvoiceViewModel;
    private ImageView back;
    private AutoCompleteTextView searchCustomerInvoice;

    public PosInvoicesFragment() {
        // Required empty public constructor
        setDrawer(false);
    }

    // TODO: Rename and change types and number of parameters
    public static PosInvoicesFragment newInstance() {
        PosInvoicesFragment fragment = new PosInvoicesFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        MainApp.INSTANCE.setCurrentActivity(getActivity());
        View view = inflater.inflate(R.layout.fragment_pos_invoices, container, false);

        initViews(view);
        setClickListeners();
        if (posInvoiceViewModel != null) {
            Objects.requireNonNull(posInvoiceViewModel.getInvoices().getValue()).clear();
        } else
            posInvoiceViewModel = new ViewModelProvider(requireActivity()).get(POSInvoiceViewModel.class);
        if (Utils.isNetworkAvailable()) {
            getInvoices();
            setObservers();
        } else setInvoicesAdapter(Room.getReportView("POS Invoice"));
        posInvoicesRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (Utils.isNetworkAvailable()) {
                    if (Utils.isLastItemDisplaying(posInvoicesRv)) {
                        if (!isProfilesEnded) {
                            Gson gson = new Gson();
                            String jsonString = null;
                            limitStart = limitStart + 20;
                            getInvoices();
                        }
                    }
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        return view;
    }


    private void initViews(View view) {
        posInvoicesRv = view.findViewById(R.id.pos_invoice_rv);
        searchCustomerInvoice = view.findViewById(R.id.search_pos_invoice);
        back = view.findViewById(R.id.back);
        searchCustomerInvoice.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                if (Utils.isNetworkAvailable())
                    getLinkSearch(RequestCodes.API.LINK_SEARCH_CUSTOMER);
                else {
                    DBSearchLink.load(getContext(), "Customer", "erpnext.controllers.queries.customer_query", searchCustomerInvoice);
                }
            }
        });
        searchCustomerInvoice.setOnItemClickListener((parent, view1, position, id) -> {
            selectedCustomer = (String) parent.getItemAtPosition(position);
        });
    }

    private void setClickListeners() {
        back.setOnClickListener(this);
    }
    private void getLinkSearch(int requestCode) {

        if (requestCode == RequestCodes.API.LINK_SEARCH_CUSTOMER) {
            searchDoctype = "Customer";
            query = "erpnext.controllers.queries.customer_query";
            filters = null;
        } else if (requestCode == RequestCodes.API.LINK_SEARCH_ITEM_GROUP) {
            searchDoctype = "Item Group";
            query = "erpnext.selling.page.point_of_sale.point_of_sale.item_group_query";
            filters = "{\"pos_profile\": " + "\"" + profileDoc.getName() + "\"" + "}";
        } else if (requestCode == RequestCodes.API.LINK_SEARCH_UMO) {
            searchDoctype = "UOM";
            query = null;
            filters = null;
        }
        NetworkCall.make()
                .setCallback(this)
                .setTag(requestCode)
                .autoLoadingCancel(Utils.getLoading(getActivity(), "searching"))
                .enque(Network.apis().getSearchLinkWithFilters(new SearchLinkWithFiltersRequestBody("", searchDoctype, "0", filters, "", query)))
                .execute();
    }
    private void setInvoicesAdapter(List<List<String>> profilesList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        posInvoiceAdapter = new PosProfileListAdapter(getContext(), profilesList, "POS Invoice", this);
        posInvoicesRv.setLayoutManager(linearLayoutManager);
        posInvoicesRv.setAdapter(posInvoiceAdapter);
    }

    private void setInvoiceAdapter(RecyclerView recyclerView, List<InvoiceItem> itemList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        InvoiceItemsAdapter invoiceItemsAdapter = new InvoiceItemsAdapter(getContext(), itemList, null);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(invoiceItemsAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                getActivity().onBackPressed();
        }
    }

    private void setObservers() {
        posInvoiceViewModel.getInvoices().observe(getActivity(), lists -> {
            if (isActionPerformed) {
                setInvoicesAdapter(lists);
                isActionPerformed = false;
            }
            if (posInvoiceAdapter == null || posInvoiceAdapter.getAllItems() == null || posInvoiceAdapter.getAllItems().isEmpty()) {
                setInvoicesAdapter(lists);
            } else posInvoiceAdapter.notifyItemRangeChanged(0, lists.size());
        });
        posInvoiceViewModel.getInvoiceStatus().observe(getActivity(), isChanged -> {
            if (isChanged) {
                limitStart = 0;
                isActionPerformed = true;
                getInvoices();
            }
        });
        posInvoiceViewModel.getInvoice().observe(getActivity(), invoiceResponse -> {
            if (invoiceResponse != null && invoiceResponse.getInvoices() != null) {
                showInvoiceDialog(invoiceResponse.getInvoices().get(0), null);
            }
        });
    }

    private void getInvoices() {
        posInvoiceViewModel.getInvoicesApi(doctype,
                20,
                true,
                "`tabPOS Invoice`.`modified` desc",
                limitStart);
    }

    @Override
    public void onProfileClick(List<String> list) {
        posInvoiceViewModel.getInvoiceDetails(doctype, list.get(0));
    }

    @Override
    public void onLongClick(List<String> list, int position) {
        selectAction(list, position);
    }

    private void selectAction(List<String> profile, int position) {
        final CharSequence[] options = {"Cancel", "Delete"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Select Action");
        builder.setItems(options, (dialog, item) -> {
            if (options[item].equals("Cancel")) {
                if (profile.get(25).equalsIgnoreCase("Paid") || profile.get(25).equalsIgnoreCase("UnPaid")) {
                    sureDialog("cancel", profile, position);
                    dialog.dismiss();
                } else Notify.Toast("You cannot cancel " + profile.get(25).toString() + " invoice");

            } else if (options[item].equals("Delete")) {
                dialog.dismiss();
                if (profile.get(9).equalsIgnoreCase("2")) {
                    sureDialog("delete", profile, position);
                } else Notify.Toast(getString(R.string.cancel_invoice_first));
            }
        });
        builder.show();
    }

    private void sureDialog(String action, List<String> profile, int position) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
        builder1.setMessage("Are you sure you want to " + action + " ?");
        builder1.setCancelable(false);

        builder1.setPositiveButton(
                "Yes",
                (dialog, id) -> {
                    dialog.cancel();
                    posInvoiceViewModel.changeStatus(doctype, profile.get(0), action);
                });

        builder1.setNegativeButton(
                "No",
                (dialog, id) -> {
                    dialog.cancel();
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    private void showInvoiceDialog(Invoice invoice, String note) {
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.pos_invoice_layout);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window window = dialog.getWindow();
        dialog.setCancelable(false);
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
        TextView name = dialog.findViewById(R.id.name);
        TextView price = dialog.findViewById(R.id.price);
        TextView net_total = dialog.findViewById(R.id.net_total);
        TextView grand_total = dialog.findViewById(R.id.grand_total);
        TextView invoice_number = dialog.findViewById(R.id.invoice_name);
        TextView status = dialog.findViewById(R.id.status);
        TextView sold_by = dialog.findViewById(R.id.sold_by);
        RecyclerView itemsRV = dialog.findViewById(R.id.items_RV);
        TextView cash = dialog.findViewById(R.id.cash);
        TextView created_at = dialog.findViewById(R.id.created_at);
        TextView noteText = dialog.findViewById(R.id.note);
        LinearLayout noteLayout = dialog.findViewById(R.id.noteLayout);
        RelativeLayout discountLayout = dialog.findViewById(R.id.discountLayout);
        RelativeLayout changeAmountLayout = dialog.findViewById(R.id.change_amount_layout);
        RelativeLayout outstandingAmountLayout = dialog.findViewById(R.id.outstanding_amount_layout);
        TextView paidAmount = dialog.findViewById(R.id.paid_amount);
        TextView changeAmount = dialog.findViewById(R.id.change_amount);
        TextView outstandingAmount = dialog.findViewById(R.id.outstanding_amount);
        TextView discountPercent = dialog.findViewById(R.id.discount);
        TextView discountedAmount = dialog.findViewById(R.id.discounted_amount);
        TextView modeOfPayment = dialog.findViewById(R.id.mode_ofPayment);
        Button cancel = dialog.findViewById(R.id.cancel);
        Button print = dialog.findViewById(R.id.print);
        grand_total.setText("$ " + invoice.getGrandTotal());
        name.setText(invoice.getCustomerName());
        price.setText("$ " + invoice.getGrandTotal());
        net_total.setText("$ " + invoice.getNetTotal());
        invoice_number.setText(invoice.getName());
        sold_by.setText(invoice.getOwner());
        status.setText(invoice.getStatus());
        paidAmount.setText("$ " + invoice.getBaseGrandTotal().toString());
//        if (!this.changeAmount.equalsIgnoreCase("0") && !this.changeAmount.equalsIgnoreCase("")) {
//            changeAmountLayout.setVisibility(View.VISIBLE);
//            changeAmount.setText("$ " + this.changeAmount);
//        }
//        if (!this.outstandingAmount.equalsIgnoreCase("0") && !this.outstandingAmount.equalsIgnoreCase("")) {
//            outstandingAmountLayout.setVisibility(View.VISIBLE);
//            outstandingAmount.setText("$ " + this.outstandingAmount);
//        }
        cash.setText(invoice.getPayments().get(0).getModeOfPayment());
        modeOfPayment.setText(invoice.getPayments().get(0).getModeOfPayment());
        created_at.setText(DateTime.getFormatedDateTimeString(invoice.getCreation()));
        setInvoiceAdapter(itemsRV, invoice.getItems());
        Double discountAmount = 0.0;
        for (InvoiceItem item : invoice.getItems()) {
            discountAmount = discountAmount + item.getDiscountAmount() * item.getQty();
        }
        if (discountAmount > 0) {
            discountLayout.setVisibility(View.VISIBLE);
//            discountPercent.setVisibility(View.GONE);
//            discountPercent.setText("Discount " + invoice.getAdditionalDiscountPercentage() + "%");
            discountedAmount.setText("$ " + discountAmount.floatValue());
        }
        if (note != null) {
            noteLayout.setVisibility(View.VISIBLE);
            noteText.setText(note);
        } else noteLayout.setVisibility(View.GONE);
        cancel.setOnClickListener(v -> {
            dialog.dismiss();
        });
        print.setOnClickListener(v -> {
            saveReceipt();
        });

    }

    private void saveReceipt() {
        View mainView;
        mainView = getActivity().getWindow().getDecorView()
                .findViewById(android.R.id.content);
        mainView.setDrawingCacheEnabled(true);
// This is the bitmap for the main activity
        Bitmap bitmap = mainView.getDrawingCache();

        View dialogView = dialog.getWindow().getDecorView().findViewById(R.id.mainlayout);
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

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        POSInvoicesRepo.getInstance().posInvoices.setValue(new ArrayList<>());
        POSInvoicesRepo.getInstance().posInvoiceResponseMutableLiveData.setValue(null);
    }

    @Override
    public void onSuccess(Call call, Response response, Object tag) throws ParseException {
        if ((int) tag == RequestCodes.API.LINK_SEARCH_CUSTOMER) {
            SearchLinkResponse res = (SearchLinkResponse) response.body();
            DBSearchLink.save(res, searchDoctype, query);
            List<String> list = new ArrayList<>();
            if (res != null && !res.getResults().isEmpty()) {
                for (SearchResult searchResult : res.getResults()) {
                    list.add(searchResult.getValue());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_list_item_1, list);
                searchCustomerInvoice.setAdapter(adapter);
                searchCustomerInvoice.showDropDown();
            }
        }
    }

    @Override
    public void onFailure(Call call, BaseResponse response, Object tag) {

    }
}