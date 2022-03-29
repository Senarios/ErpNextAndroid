package com.example.erpnext.fragments;


import static com.example.erpnext.activities.ScanQRActivity.contents;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.activities.ScanQRActivity;
import com.example.erpnext.adapters.CartItemsAdapter;
import com.example.erpnext.adapters.InvoiceItemsAdapter;
import com.example.erpnext.adapters.ItemsAdapter;
import com.example.erpnext.adapters.SPLocHistoryAdapter;
import com.example.erpnext.app.MainApp;
import com.example.erpnext.callbacks.CartItemCallback;
import com.example.erpnext.models.CartItem;
import com.example.erpnext.models.CompleteOrderItem;
import com.example.erpnext.models.CompleteOrderPayment;
import com.example.erpnext.models.Invoice;
import com.example.erpnext.models.InvoiceItem;
import com.example.erpnext.models.ItemDetail;
import com.example.erpnext.models.ItemMessage;
import com.example.erpnext.models.PendingOrder;
import com.example.erpnext.models.ProfileDoc;
import com.example.erpnext.models.SPLocHisDatum;
import com.example.erpnext.models.SPLocHisRes;
import com.example.erpnext.models.ScanQRDatum;
import com.example.erpnext.models.ScanQRRes;
import com.example.erpnext.models.SearchResult;
import com.example.erpnext.network.ApiServices;
import com.example.erpnext.network.Network;
import com.example.erpnext.network.NetworkCall;
import com.example.erpnext.network.OnNetworkResponse;
import com.example.erpnext.network.serializers.requestbody.CompleteOrderRequestBody;
import com.example.erpnext.network.serializers.requestbody.SearchLinkWithFiltersRequestBody;
import com.example.erpnext.network.serializers.response.BaseResponse;
import com.example.erpnext.network.serializers.response.CompleteOrderResponse;
import com.example.erpnext.network.serializers.response.GetItemDetailResponse;
import com.example.erpnext.network.serializers.response.GetItemsResponse;
import com.example.erpnext.network.serializers.response.ItemResponse;
import com.example.erpnext.network.serializers.response.SearchLinkResponse;
import com.example.erpnext.roomDB.data.DBSearchLink;
import com.example.erpnext.roomDB.data.Room;
import com.example.erpnext.utils.DateTime;
import com.example.erpnext.utils.Notify;
import com.example.erpnext.utils.RequestCodes;
import com.example.erpnext.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PointOfSaleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PointOfSaleFragment extends Fragment implements View.OnClickListener, OnNetworkResponse, CartItemCallback, DateTime.datePickerCallback {
    public static TextView netTotal_tv, grandTotal_tv;
    public static float netTotal = 0;
    public static float grandTotal = 0;
    public static float discountedAmount = 0;
    private final String item_search = "";
    float discountPercent = (float) 0.0;
    CartItem selectedItem, clickedItem;
    TextView date_text, profileName, discount;
    String searchDoctype = "", query = "", filters = "", baseTotal, changeAmount = "0", outstandingAmount = "0", paidAmount = "0";
    GetItemsResponse getItemsResponse;
    CompleteOrderRequestBody completedOrder;
    private ImageView back, qrScanner;
    private ProfileDoc profileDoc;
    private RecyclerView itemsRV, cartItemsRV;
    private ItemsAdapter itemsAdapter;
    private CartItemsAdapter selectedCartItemsAdapter;
    private ItemResponse itemResponse;
    private Dialog dialog;
    private AutoCompleteTextView customerSearch, itemSearch, itemGroupSearch, edit_UMO;
    private Integer limitSet = 0;
    private List<CartItem> itemList = new ArrayList<>();
    private String item_group = "All Item Groups";
    private String selectedCustomer = "";
    private boolean isItemsEnded = false, isSearchingItem = false;
    private Button checkout_btn;
    private PointOfSaleFragment pointOfSaleFragment;
    private RelativeLayout discountLayout;
    private FragmentActivity fragmentActivity;
    private AdapterView.OnItemClickListener onItemClickListener;

    public PointOfSaleFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static PointOfSaleFragment newInstance() {
        PointOfSaleFragment fragment = new PointOfSaleFragment();
        return fragment;
    }

    public static void setTotal() {
        netTotal_tv.setText("$ " + netTotal);
        grandTotal_tv.setText("$ " + grandTotal);
    }

    public static float getDiscountedAmount(ItemDetail cartItem) {
        if (cartItem.getDiscountPercentage() > 0) {
            float discountedAmount = cartItem.getPriceListRate().floatValue() * cartItem.getDiscountPercentage().floatValue() / 100;
            return cartItem.getPriceListRate().floatValue() - discountedAmount;
        } else return cartItem.getPriceListRate().floatValue();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_point_of_sale, container, false);

        initViews(view);
        setClickListeners();
        getDocsFromBundle();
        if (Utils.isNetworkAvailable()) getItems();
        else loadFromLoacal();
        setSearchList();
        setCartItemsAdapter(new ArrayList<>());
        itemsRV.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (Utils.isNetworkAvailable()) {
                    if (Utils.isLastItemDisplaying(itemsRV)) {
                        if (!isItemsEnded && !isSearchingItem) {
                            limitSet = limitSet + 40;
                            getItems();
                        }
                    }
                    super.onScrolled(recyclerView, dx, dy);
                }
            }
        });

        return view;
    }

    private void loadFromLoacal() {
        if (MainApp.database.itemsDao().getItemResponse(item_group) != null) {
            setItemsAdapter(MainApp.database.itemsDao().getItemResponse(item_group).getItemMessage().getCartItemList());
            itemList = itemsAdapter.getAllItems();
        } else {
            setItemsAdapter(new ArrayList<>());
        }
    }

    private void getDocsFromBundle() {
        if (getArguments().containsKey("doc")) {
            String docsString = (String) getArguments().get("doc");
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            profileDoc = gson.fromJson(docsString, ProfileDoc.class);
            profileName.setText(profileDoc.getName());
        }
    }

    private void initViews(View view) {
        itemsRV = view.findViewById(R.id.pos_items_RV);
        cartItemsRV = view.findViewById(R.id.cart_RV);
        profileName = view.findViewById(R.id.profileName);
        back = view.findViewById(R.id.back);
        qrScanner = view.findViewById(R.id.qr_scan);
        itemGroupSearch = view.findViewById(R.id.item_group);
        customerSearch = view.findViewById(R.id.customer_name);
        itemSearch = view.findViewById(R.id.search_item);
        netTotal_tv = view.findViewById(R.id.net_total);
        checkout_btn = view.findViewById(R.id.checkout);
        grandTotal_tv = view.findViewById(R.id.grand_total);
        discountLayout = view.findViewById(R.id.discount_layout);
        discount = view.findViewById(R.id.discount);
        customerSearch.requestFocus();
        itemGroupSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (Utils.isNetworkAvailable())
                        getLinkSearch(RequestCodes.API.LINK_SEARCH_ITEM_GROUP);
                    else {
                        DBSearchLink.load(getContext(), "Item Group",
                                "{\"pos_profile\": " + "\"" + profileDoc.getName() + "\"" + "}",
                                "erpnext.selling.page.point_of_sale.point_of_sale.item_group_query",
                                itemGroupSearch);
                    }
                }
            }
        });
        itemGroupSearch.setOnItemClickListener((parent, view12, position, id) -> {
            item_group = (String) parent.getItemAtPosition(position);
            limitSet = 0;
            isItemsEnded = false;
            if (Utils.isNetworkAvailable()) getItems();
            else loadFromLoacal();
        });
        customerSearch.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                if (Utils.isNetworkAvailable())
                    getLinkSearch(RequestCodes.API.LINK_SEARCH_CUSTOMER);
                else {
                    DBSearchLink.load(getContext(), "Customer", "erpnext.controllers.queries.customer_query", customerSearch);
                }
            }
        });
        customerSearch.setOnItemClickListener((parent, view1, position, id) -> {
            selectedCustomer = (String) parent.getItemAtPosition(position);
        });
        itemSearch.setOnFocusChangeListener((v, hasFocus) -> {
            isSearchingItem = hasFocus;
        });

    }

    private void setClickListeners() {
        back.setOnClickListener(this);
        qrScanner.setOnClickListener(this);
        checkout_btn.setOnClickListener(this);
        discountLayout.setOnClickListener(this);
    }

    private void setItemsAdapter(List<CartItem> itemList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        itemsAdapter = new ItemsAdapter(getContext(), itemList, this);
        itemsRV.setLayoutManager(linearLayoutManager);
        itemsRV.setAdapter(itemsAdapter);
    }

    private void setCartItemsAdapter(List<ItemDetail> itemList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        selectedCartItemsAdapter = new CartItemsAdapter(getContext(), itemList, this);
        cartItemsRV.setLayoutManager(linearLayoutManager);
        cartItemsRV.setAdapter(selectedCartItemsAdapter);
    }

    private void setInvoiceAdapter(RecyclerView recyclerView, List<InvoiceItem> itemList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        InvoiceItemsAdapter invoiceItemsAdapter = new InvoiceItemsAdapter(getContext(), itemList, this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(invoiceItemsAdapter);
    }

    private void setLocalInvoiceAdapter(RecyclerView recyclerView, List<ItemDetail> itemList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        CartItemsAdapter invoiceItemsAdapter = new CartItemsAdapter(getContext(), itemList, this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(invoiceItemsAdapter);
    }

    @Override
    public void onItemClick(CartItem cartItem) {
        if (cartItem.getActualQty() > 0) {
            if (!selectedCustomer.equalsIgnoreCase("")) {
                selectedItem = new CartItem();
                selectedItem.setIsStockItem(cartItem.getIsStockItem());
                selectedItem.setCurrency(cartItem.getCurrency());
                selectedItem.setDescription(cartItem.getDescription());
                selectedItem.setItemCode(cartItem.getItemCode());
                selectedItem.setItemName(cartItem.getItemName());
                selectedItem.setPriceListRate(cartItem.getPriceListRate());
                selectedItem.setStockUom(cartItem.getStockUom());
                selectedItem.setActualQty(1);
                clickedItem = cartItem;
                if (Utils.isNetworkAvailable()) {
                    getItemDetail(cartItem.getItemCode());
                } else {
                    GetItemDetailResponse response = Room.getItemDetail(selectedItem.getItemCode(), profileDoc.getWarehouse());
                    if (response != null) {
                        selectedCartItemsAdapter.addItem(response.getItemDetail());
                        Notify.Toast(getString(R.string.added_to_cart));
                        itemsAdapter.changeQuantity(selectedItem);
                    }
                }
//                selectedCartItemsAdapter.addItem(selectedCartItem);
//                Notify.Toast("Added to cart");
//                itemsAdapter.changeQuantity(cartItem);

            } else {
                Notify.ToastLong(getString(R.string.select_customer_to_continue));
            }
        } else Notify.Toast(getString(R.string.sorry_item_outstock));
    }

    @Override
    public void onCartItemClick(ItemDetail cartItem) {
        showItemDetailDialog(cartItem);
    }

    @Override
    public void onDeleteClick(ItemDetail cartItem) {
        if (selectedCartItemsAdapter.getAllItems().size() == 1) {
            grandTotal = 0;
            netTotal = 0;
            discountPercent = 0;
            discountedAmount = 0;
            setTotal();
        }
        CartItem cartItem1 = new CartItem();
        cartItem1.setItemCode(cartItem.getItemCode());
        cartItem1.setActualQty(cartItem.getQty().floatValue());
        itemsAdapter.changeQuantity(cartItem1, cartItem.getQty().floatValue());
        selectedCartItemsAdapter.removeItem(cartItem);
    }

    private void setSearchList() {
        itemSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
    }

    private void filter(String text) {
        ArrayList<CartItem> filteredList = new ArrayList<>();
        for (CartItem item : itemList) {
            if (item.getItemName().toLowerCase().contains(text.toLowerCase()) || item.getItemCode().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        itemsAdapter.filteredList(filteredList);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                getActivity().onBackPressed();
                break;
            case R.id.qr_scan:
                customerSearch.setText("");
                startActivity(new Intent(getContext(), ScanQRActivity.class));
                break;
            case R.id.checkout:
                if (grandTotal > 0) {
                    if (discountPercent > 0) {
                        float discount = grandTotal * discountPercent;
                        discount = discount / 100;
                        float grandTotal = PointOfSaleFragment.grandTotal - discount;
                        float netTotal = grandTotal;
                        discountedAmount = discount;
                        showPaymentDialog(grandTotal, netTotal);
                    } else {
                        discountedAmount = 0;
                        showPaymentDialog(grandTotal, netTotal);
                    }


                } else Notify.Toast(getString(R.string.add_item_cart));
                break;
            case R.id.complete_order:
                createOrder();
                break;
            case R.id.discount_layout:
                showDiscountDialog();
                break;
        }
    }

    private void createOrder() {
        CompleteOrderRequestBody completeOrderRequestBody = new CompleteOrderRequestBody();
        List<CartItem> cartItemList = new ArrayList<>();
        for (ItemDetail itemDetail : selectedCartItemsAdapter.getAllItems()) {
            for (CartItem cartItem : itemsAdapter.getAllItems()) {
                if (itemDetail.getItemCode().equalsIgnoreCase(cartItem.getItemCode())) {
                    cartItem.setActualQty(itemDetail.getQty().floatValue());
                    cartItem.setDiscountPercentage(itemDetail.getDiscountPercentage().floatValue());
                    cartItem.setPriceListRate(getDiscountedAmount(itemDetail));
                    cartItemList.add(cartItem);
                    break;
                }
            }
        }
        List<CompleteOrderItem> invoiceItems = new ArrayList<>();
        for (CartItem cartItem : cartItemList) {
            CompleteOrderItem invoiceItem = new CompleteOrderItem();
            invoiceItem.setItemCode(cartItem.getItemCode());
            invoiceItem.setQty(cartItem.getActualQty());
            invoiceItem.setRate(cartItem.getPriceListRate());
            invoiceItem.setDiscountPercentage(cartItem.getDiscountPercentage());
            invoiceItems.add(invoiceItem);
        }
        completeOrderRequestBody.setItems(invoiceItems);
        completeOrderRequestBody.setName("001-002");
        completeOrderRequestBody.setNamingSeries("ACC-PSINV-.YYYY.-");
        completeOrderRequestBody.setCustomer(selectedCustomer);
        completeOrderRequestBody.setPostingDate(date_text.getText().toString());
        completeOrderRequestBody.setDocstatus(1);
        completeOrderRequestBody.setPaidAmount(paidAmount);
        completeOrderRequestBody.setOutstandingAmount(outstandingAmount);
        completeOrderRequestBody.setChangeAmount(changeAmount);
        completeOrderRequestBody.setBaseChangeAmount(changeAmount);
        completeOrderRequestBody.setDiscountPercentage(discountPercent);
        completeOrderRequestBody.setBaseTotal(String.valueOf(grandTotal));


        CompleteOrderPayment completeOrderPayment = new CompleteOrderPayment();
        completeOrderPayment.setModeOfPayment(profileDoc.getPayments().get(0).getModeOfPayment());
        completeOrderPayment.setDefault(profileDoc.getPayments().get(0).getDefault());
        completeOrderPayment.setParent("new-pos-invoice");
        completeOrderPayment.setParentfield("payments");
        completeOrderPayment.setDocstatus(0);
        completeOrderPayment.setIdx(1);
        completeOrderPayment.setType("Cash");
        completeOrderPayment.setDoctype("Sales Invoice Payment");
        completeOrderPayment.setName("new-sales-invoice-payment");
        completeOrderPayment.setIslocal(1);
        completeOrderPayment.setAccount((String) profileDoc.getAccountForChangeAmount());
        List<CompleteOrderPayment> completeOrderPaymentList = new ArrayList<>();
        completeOrderPaymentList.add(completeOrderPayment);
        completeOrderRequestBody.setPayments(completeOrderPaymentList);
        if (Utils.isNetworkAvailable()) {
            completeOrder(completeOrderRequestBody);
        } else {
            PendingOrder pendingOrder = new PendingOrder();
            pendingOrder.setOreder(completeOrderRequestBody);
            pendingOrder.setItem_group(item_group);
            MainApp.database.pendingOrderDao().insertOrder(pendingOrder);
            updateStokeFromDB();
            dialog.dismiss();
            boolean isUnpaid = false;
            if (Float.parseFloat(outstandingAmount) > 0) {
                isUnpaid = true;
            }
            confirmationDialog(null, isUnpaid);
            Notify.ToastLong(getString(R.string.order_completed));
        }
    }

    private void updateStokeFromDB() {
        GetItemsResponse response = MainApp.database.itemsDao().getItemResponse(item_group);
        if (response != null) {
            int id = MainApp.database.itemsDao().getItemResponse(item_group).uid;
            String itemGroup = MainApp.database.itemsDao().getItemResponse(item_group).itemGroup;
            response.uid = (id);
            response.itemGroup = itemGroup;
        } else {
            response = new GetItemsResponse();
            response.itemGroup = item_group;
            ItemMessage itemMessage = new ItemMessage();
            response.setItemMessage(itemMessage);
        }
        response.customer = selectedCustomer;
        List<CartItem> cartItemList = itemsAdapter.getAllItems();
        response.getItemMessage().setCartItemList(cartItemList);
        MainApp.database.itemsDao().insertGetItemResponse(response);
    }

    private void showItemDetailDialog(ItemDetail cartItem) {
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.item_details_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        dialog.setCancelable(false);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
        EditText edit_quantity = dialog.findViewById(R.id.autoCompleteEditQuantity);
        edit_UMO = dialog.findViewById(R.id.autoCompleteEditUOM);
        EditText edit_UMO_conFactor = dialog.findViewById(R.id.autoCompleteEditConFactor);
        AutoCompleteTextView edit_warehouse = dialog.findViewById(R.id.autoCompleteEditWarehouse);
        EditText edit_price_rate_list = dialog.findViewById(R.id.autoCompleteEditPriceRateList);
        EditText edit_availableQty = dialog.findViewById(R.id.autoCompleteEditAvailableQty);
        EditText edit_discount = dialog.findViewById(R.id.autoCompleteEditDiscount);
        EditText edit_rate = dialog.findViewById(R.id.autoCompleteEditRate);
        TextView name_tv = dialog.findViewById(R.id.name);
        TextView actual_price = dialog.findViewById(R.id.actual_price);
        TextView discountPercent = dialog.findViewById(R.id.percentage);
        LinearLayout discount_layout = dialog.findViewById(R.id.discount_layout);
        TextView description_tv = dialog.findViewById(R.id.description);
        TextView price_tv = dialog.findViewById(R.id.price);
        Button done = dialog.findViewById(R.id.done);
        edit_quantity.setText("" + cartItem.getQty());
        edit_UMO.setText(cartItem.getUom());
        if (cartItem.getDiscountPercentage() > 0) {
            float discountedAmount = cartItem.getPriceListRate().floatValue() * cartItem.getDiscountPercentage().floatValue() / 100;
            float afterDiscount = cartItem.getPriceListRate().floatValue() - discountedAmount;
            price_tv.setText("$ " + afterDiscount);
            discount_layout.setVisibility(View.VISIBLE);
            actual_price.setPaintFlags(actual_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            actual_price.setText("$ " + cartItem.getPriceListRate());
            discountPercent.setText("" + cartItem.getDiscountPercentage() + "% off");
        } else price_tv.setText("$ " + cartItem.getPriceListRate());
        edit_rate.setText(String.valueOf(cartItem.getPriceListRate()));
        edit_price_rate_list.setText(String.valueOf(cartItem.getPriceListRate()));
        edit_availableQty.setText("" + cartItem.getActualQty());
        edit_discount.setText(cartItem.getDiscountPercentage().toString());
        edit_UMO_conFactor.setText(cartItem.getConversionFactor().toString());
        edit_warehouse.setText(cartItem.getWarehouse());
        name_tv.setText(cartItem.getItemName());
        description_tv.setText(cartItem.getDescription());
//        price_tv.setText("$ " + cartItem.getPriceListRate());

        done.setOnClickListener(v -> {
            if (edit_quantity.getText().toString() != null && !edit_quantity.getText().toString().equalsIgnoreCase("")) {
                Double preQuantity = cartItem.getQty();
                CartItem cartItem1 = new CartItem();
                cartItem1.setItemCode(cartItem.getItemCode());
                cartItem1.setActualQty((float) cartItem.getQty().doubleValue());
                float actualQuantity = (float) (itemsAdapter.getItemQuantity(cartItem1) + preQuantity);
                float newQuantity = Float.parseFloat(edit_quantity.getText().toString());
                if (Float.parseFloat(edit_quantity.getText().toString()) == 0) {
                    Notify.ToastLong(getString(R.string.select_one_qty));
                } else if (Float.parseFloat(edit_quantity.getText().toString()) > actualQuantity) {
                    Notify.ToastLong(getString(R.string.select_quantity_item) + actualQuantity);
                } else {
                    if (preQuantity == newQuantity) {
                        cartItem.setQty((double) Float.parseFloat(edit_quantity.getText().toString()));
                        selectedCartItemsAdapter.setItem(cartItem);
                        dialog.dismiss();
                    } else {
                        cartItem.setQty((double) Float.parseFloat(edit_quantity.getText().toString()));
                        selectedCartItemsAdapter.setItem(cartItem);
                        if (Float.parseFloat(edit_quantity.getText().toString()) > 0) {
                            itemsAdapter.changeQuantity(cartItem1, (float) (preQuantity - newQuantity));
                        }

                        dialog.dismiss();
                    }
                }
            } else Notify.Toast(getString(R.string.enter_quantity));
        });
        edit_UMO.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (Utils.isNetworkAvailable())
                        getLinkSearch(RequestCodes.API.LINK_SEARCH_UMO);
                    else {
                        DBSearchLink.load(getContext(), "UOM", edit_UMO);
                    }
                }
            }
        });
        edit_UMO.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);

            }
        });
    }

    private void showPaymentDialog(float grandTotal, float netTotal) {
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.payment_method_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
        EditText cash_edit = dialog.findViewById(R.id.cash_edit);
        TextView grand_total = dialog.findViewById(R.id.grand_total);
        TextView amount_paid = dialog.findViewById(R.id.amount_paid);
        TextView change_amount = dialog.findViewById(R.id.change);
        TextView change_text = dialog.findViewById(R.id.change_text);
        LinearLayout discountLayout = dialog.findViewById(R.id.discount_layout);
        TextView actualPrice = dialog.findViewById(R.id.actual_price);
        TextView afterDiscount = dialog.findViewById(R.id.after_discount);
        date_text = dialog.findViewById(R.id.date_text);
        Button completeOrder = dialog.findViewById(R.id.complete_order);
        grand_total.setText("$ " + grandTotal);
        cash_edit.setText(String.valueOf(grandTotal));
        change_text.setText("To Be Paid");
        change_amount.setText("$ 0");
        amount_paid.setText("$ " + String.valueOf(grandTotal));
        paidAmount = (String.valueOf(grandTotal));
        date_text.setText(DateTime.getCurrentDate());
        if (discountPercent > 0) {
            discountLayout.setVisibility(View.VISIBLE);
            actualPrice.setText("$ " + String.valueOf(PointOfSaleFragment.grandTotal));
            afterDiscount.setText("$ " + String.valueOf(grandTotal));
        }
        completeOrder.setOnClickListener(v -> {
            PointOfSaleFragment.grandTotal = grandTotal;
            PointOfSaleFragment.netTotal = grandTotal;
            createOrder();
        });
        date_text.setOnClickListener(v -> {
            DateTime.showDatePicker(getActivity(), this);
        });

        cash_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                float amount;
                if (s.toString().equalsIgnoreCase("")) {
                    amount = 0;
                } else {
                    amount = Float.parseFloat(s.toString());
                }
                float change;
//                if (amount >= grandTotal) {
                change = grandTotal - amount;
//                if (change <= 0) {
//                    completeOrder.setEnabled(true);
//                } else {
//                    completeOrder.setEnabled(false);
//                }
                if (change > 0) {
                    change_text.setText("To Be Paid");
                    outstandingAmount = String.valueOf(Utils.round(Math.abs(change), 2));
                    changeAmount = "0";
                } else {
                    changeAmount = String.valueOf(Utils.round(Math.abs(change), 2));
                    outstandingAmount = "0";
                    change = Math.abs(change);
                    change_text.setText("Change");
                }
                amount_paid.setText("$ " + amount);
                change_amount.setText("$ " + Utils.round(change, 2));
                paidAmount = String.valueOf(amount);
//                outstandingAmount = String.valueOf(Utils.round(change, 2));

            }

        });
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
        if (!this.changeAmount.equalsIgnoreCase("0") && !this.changeAmount.equalsIgnoreCase("")) {
            changeAmountLayout.setVisibility(View.VISIBLE);
            changeAmount.setText("$ " + this.changeAmount);
        }
        if (!this.outstandingAmount.equalsIgnoreCase("0") && !this.outstandingAmount.equalsIgnoreCase("")) {
            outstandingAmountLayout.setVisibility(View.VISIBLE);
            outstandingAmount.setText("$ " + this.outstandingAmount);
        }
        cash.setText(invoice.getPayments().get(0).getModeOfPayment());
        modeOfPayment.setText(invoice.getPayments().get(0).getModeOfPayment());
        created_at.setText(DateTime.getFormatedDateTimeString(invoice.getCreation()));
        setInvoiceAdapter(itemsRV, invoice.getItems());
        if (invoice.getDiscountAmount() > 0) {
            discountLayout.setVisibility(View.VISIBLE);
            discountPercent.setText("Discount " + invoice.getAdditionalDiscountPercentage() + "%");
            discountedAmount.setText("$ " + invoice.getDiscountAmount().toString());
        }
        if (note != null) {
            noteLayout.setVisibility(View.VISIBLE);
            noteText.setText(note);
        } else noteLayout.setVisibility(View.GONE);
        cancel.setOnClickListener(v -> {
            dialog.dismiss();
            taskUpdate();
        });
        print.setOnClickListener(v -> {
            saveReceipt();
        });

    }

    @SuppressLint("ResourceType")
    private void taskUpdate() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
        builder1.setMessage(R.string.update_task);
        builder1.setCancelable(false);

        builder1.setPositiveButton(
                R.string.yes,
                (dialog, id) -> {
                    Fragment fragment = new MyTasksFragment();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainer, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    dialog.cancel();

                });

        builder1.setNegativeButton(
                R.string.no,
                (dialog, id) -> {
                    getActivity().onBackPressed();
                    dialog.cancel();
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    private void showLocalInvoiceDialog(@Nullable String note, boolean isUnpaid) {
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
        TextView created_at = dialog.findViewById(R.id.created_at);
        TextView status = dialog.findViewById(R.id.status);
        TextView sold_by = dialog.findViewById(R.id.sold_by);
        RecyclerView itemsRV = dialog.findViewById(R.id.items_RV);
        TextView cash = dialog.findViewById(R.id.cash);
        RelativeLayout discountLayout = dialog.findViewById(R.id.discountLayout);
        TextView discountPercent = dialog.findViewById(R.id.discount);
        TextView discountedAmount = dialog.findViewById(R.id.discounted_amount);
        RelativeLayout changeAmountLayout = dialog.findViewById(R.id.change_amount_layout);
        RelativeLayout outstandingAmountLayout = dialog.findViewById(R.id.outstanding_amount_layout);
        TextView paidAmount = dialog.findViewById(R.id.paid_amount);
        TextView changeAmount = dialog.findViewById(R.id.change_amount);
        TextView outstandingAmount = dialog.findViewById(R.id.outstanding_amount);
        TextView modeOfPayment = dialog.findViewById(R.id.mode_ofPayment);
        Button cancel = dialog.findViewById(R.id.cancel);
        Button print = dialog.findViewById(R.id.print);
        TextView noteText = dialog.findViewById(R.id.note);
        LinearLayout noteLayout = dialog.findViewById(R.id.noteLayout);
        paidAmount.setText("$ " + grandTotal);
        if (!this.changeAmount.equalsIgnoreCase("0") && !this.changeAmount.equalsIgnoreCase("")) {
            changeAmountLayout.setVisibility(View.VISIBLE);
            changeAmount.setText("$ " + this.changeAmount);
        }
        if (!this.outstandingAmount.equalsIgnoreCase("0") && !this.outstandingAmount.equalsIgnoreCase("")) {
            outstandingAmountLayout.setVisibility(View.VISIBLE);
            outstandingAmount.setText("$ " + this.outstandingAmount);
        }
        if (note != null) {
            noteLayout.setVisibility(View.VISIBLE);
            noteText.setText(note);
        }
        grand_total.setText("$ " + grandTotal);
        name.setText(selectedCustomer);
        price.setText("$ " + grandTotal);
        net_total.setText("$ " + netTotal);
        invoice_number.setText("ACC-PSINV-.YYYY.-");
        sold_by.setText(profileDoc.getOwner());
        if (!isUnpaid) status.setText("Paid");
        else status.setText("UnPaid");
        cash.setText(profileDoc.getPayments().get(0).getModeOfPayment());
        modeOfPayment.setText(profileDoc.getPayments().get(0).getModeOfPayment());
        setLocalInvoiceAdapter(itemsRV, selectedCartItemsAdapter.getAllItems());
        created_at.setText(DateTime.getServerCurrentDateTime());
        if (PointOfSaleFragment.discountedAmount > 0) {
            discountLayout.setVisibility(View.VISIBLE);
            discountPercent.setText("Discount " + this.discountPercent + "%");
            discountedAmount.setText("$ " + PointOfSaleFragment.discountedAmount);
        }
        cancel.setOnClickListener(v -> {
            dialog.dismiss();
            getActivity().onBackPressed();
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

    private void getItems() {
        NetworkCall.make()
                .setCallback(this)
                .setTag(RequestCodes.API.GET_POS_ITEMS)
                .autoLoadingCancel(Utils.getLoading(getActivity(), "Loading"))
                .enque(Network.apis().getItems(limitSet, 40, "Standard Selling", item_group, item_search, profileDoc.getName()))
                .execute();
    }

    private void getItemDetail(String itemCode) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject("{\"item_code\":\"1111\",\"barcode\":null,\"set_warehouse\":\"Testing Ravail Warehouse - IAL\",\"customer\":\"0789290026خیر الله\",\"currency\":\"USD\",\"update_stock\":0,\"conversion_rate\":1,\"price_list\":\"Standard Selling\",\"price_list_currency\":\"USD\",\"plc_conversion_rate\":1,\"company\":\"Izat Afghan Limited\",\"is_pos\":1,\"is_return\":0,\"transaction_date\":\"2021-09-10\",\"ignore_pricing_rule\":0,\"doctype\":\"POS Invoice\",\"name\":\"new-pos-invoice\",\"qty\":1,\"net_rate\":\"15\",\"stock_uom\":\"Carton\",\"pos_profile\":\"Osaid \",\"cost_center\":\"Main - IAL\",\"tax_category\":\"\"}");
            jsonObject.put("item_code", itemCode);
            jsonObject.put("warehouse", profileDoc.getWarehouse());
            jsonObject.put("cost_center", profileDoc.getCostCenter());
            jsonObject.put("pos_profile", profileDoc.getName());
            jsonObject.put("customer", selectedCustomer);
            jsonObject.put("transaction_date", DateTime.getCurrentDate());
            jsonObject.put("net_rate", clickedItem.getPriceListRate());
            jsonObject.put("ignore_pricing_rule", profileDoc.getIgnorePricingRule());
        } catch (JSONException err) {
            Log.d("Error", err.toString());
        }
        NetworkCall.make()
                .setCallback(this)
                .setTag(RequestCodes.API.GET_ITEM_DETAILS)
                .autoLoadingCancel(Utils.getLoading(getActivity(), "Getting item..."))
                .enque(Network.apis().getItemDetail(new JSONObject(), jsonObject))
                .execute();
    }

    private void completeOrder(CompleteOrderRequestBody completeOrderRequestBody) {
        completedOrder = completeOrderRequestBody;
        NetworkCall.make()
                .setCallback(this)
                .setTag(RequestCodes.API.COMPLETE_ORDER)
                .autoLoadingCancel(Utils.getLoading(getActivity(), "Please wait.."))
                .enque(Network.apis().completeOrder(completeOrderRequestBody))
                .execute();
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

    @Override
    public void onSuccess(Call call, Response response, Object tag) throws ParseException {
        if ((int) tag == RequestCodes.API.GET_POS_ITEMS) {
            GetItemsResponse res = (GetItemsResponse) response.body();
            if (!res.getItemMessage().getCartItemList().isEmpty()) {
                List<CartItem> itemList = new ArrayList<>();
                for (CartItem cartItem1 : res.getItemMessage().getCartItemList()) {
                    itemList.add(cartItem1);
                }
                if (itemList != null && !itemList.isEmpty()) {
                    if (limitSet == 0) {
                        setItemsAdapter(itemList);
                        Room.savePOSItems(res, item_group, profileDoc);
                    } else {
                        itemsAdapter.addItem(res.getItemMessage().getCartItemList());
                        Room.saveMorePOSItems(res, item_group);
                    }
                }
            } else {
                isItemsEnded = true;
//                setItemsAdapter(new ArrayList<>());
            }
            if (itemsAdapter != null && itemsAdapter.getAllItems() != null && !itemsAdapter.getAllItems().isEmpty()) {
                itemList = itemsAdapter.getAllItems();
            }
        } else if ((int) tag == RequestCodes.API.GET_ITEM_DETAILS) {
            GetItemDetailResponse res = (GetItemDetailResponse) response.body();
            if (res != null) {
                Room.saveItemDetail(res);
                selectedCartItemsAdapter.addItem(res.getItemDetail());
                Notify.Toast(getString(R.string.added_to_cart));
                itemsAdapter.changeQuantity(selectedItem);
            } else {
                Notify.Toast(getString(R.string.item_not_avail_warehouse));
            }
        } else if ((int) tag == RequestCodes.API.LINK_SEARCH_CUSTOMER) {
            SearchLinkResponse res = (SearchLinkResponse) response.body();
            DBSearchLink.save(res, searchDoctype, query);
            List<String> list = new ArrayList<>();
            if (res != null && !res.getResults().isEmpty()) {
                for (SearchResult searchResult : res.getResults()) {
                    list.add(searchResult.getValue());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_list_item_1, list);
                customerSearch.setAdapter(adapter);
                customerSearch.showDropDown();
            }
        } else if ((int) tag == RequestCodes.API.LINK_SEARCH_ITEM_GROUP) {
            SearchLinkResponse res = (SearchLinkResponse) response.body();
            DBSearchLink.save(res, searchDoctype, filters, query);
            List<String> list = new ArrayList<>();
            if (res != null && !res.getResults().isEmpty()) {
                for (SearchResult searchResult : res.getResults()) {
                    list.add(searchResult.getValue());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_list_item_1, list);
                itemGroupSearch.setAdapter(adapter);
                itemGroupSearch.showDropDown();
            }
        } else if ((int) tag == RequestCodes.API.LINK_SEARCH_UMO) {
            SearchLinkResponse res = (SearchLinkResponse) response.body();
            DBSearchLink.save(res, searchDoctype);
            List<String> list = new ArrayList<>();
            if (res != null && !res.getResults().isEmpty()) {
                for (SearchResult searchResult : res.getResults()) {
                    list.add(searchResult.getValue());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_list_item_1, list);
                edit_UMO.setAdapter(adapter);
                edit_UMO.showDropDown();
            }
        } else if ((int) tag == RequestCodes.API.COMPLETE_ORDER) {
            CompleteOrderResponse res = (CompleteOrderResponse) response.body();
            if (res != null) {
                Notify.Toast(getString(R.string.pos_invoice_created));
                dialog.dismiss();
//            getActivity().onBackPressed();
                confirmationDialog(res.getInvoice(), false);
//                showInvoiceDialog(res.getInvoice());
            }
        }
    }

    @Override
    public void onFailure(Call call, BaseResponse response, Object tag) {
        Notify.ToastLong(response.getServerMessages());
    }

    private void confirmationDialog(Invoice invoice, boolean isUnpaid) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
        builder1.setMessage(R.string.note_in_receipt);
        builder1.setCancelable(false);

        builder1.setPositiveButton(
                R.string.yes,
                (dialog, id) -> {
                    dialog.cancel();
                        showNoteDialog(invoice, isUnpaid);
                });

        builder1.setNegativeButton(
                R.string.no,
                (dialog, id) -> {
                    dialog.cancel();
                    if (Utils.isNetworkAvailable()) {
                        showInvoiceDialog(invoice, null);
                    } else showLocalInvoiceDialog(null, isUnpaid);
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    private void showNoteDialog(Invoice invoice, boolean isUnpaid) {
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.add_note_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        dialog.setCancelable(false);
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
        EditText note_edit = dialog.findViewById(R.id.note_edit);
        Button add = dialog.findViewById(R.id.add);
        Button cancel = dialog.findViewById(R.id.cancel);
        add.setOnClickListener(v -> {
            String note = note_edit.getText().toString();
            if (note != null && !note.equalsIgnoreCase("")) {
                dialog.dismiss();
                if (Utils.isNetworkAvailable()) {
                    showInvoiceDialog(invoice, note);
                } else {
                    showLocalInvoiceDialog(note, isUnpaid);
                }
            } else Notify.Toast(getString(R.string.write_note));
        });
        cancel.setOnClickListener(v -> {
            dialog.dismiss();
            if (Utils.isNetworkAvailable()) {
                showInvoiceDialog(invoice, null);
            } else {
                showLocalInvoiceDialog(null, isUnpaid);
            }
        });

    }

    private void showDiscountDialog() {
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.add_discount_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
        EditText discount_edit = dialog.findViewById(R.id.discount_edit);
        Button add = dialog.findViewById(R.id.add);
        Button cancel = dialog.findViewById(R.id.cancel);
        add.setOnClickListener(v -> {
            String enteredDiscount = discount_edit.getText().toString();
            if (enteredDiscount != null && !enteredDiscount.equalsIgnoreCase("")) {
                dialog.dismiss();
                discountPercent = Float.parseFloat(enteredDiscount);
                discount.setText(enteredDiscount);
            } else Notify.Toast(getString(R.string.enter_percentage));
        });
        cancel.setOnClickListener(v -> {
            discountPercent = 0;
            discount.setText("0%");
            dialog.dismiss();
        });

    }

    @Override
    public void onSelected(String date) {
        date_text.setText(date);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (contents != null) {
            scan_qr();
        } else {
            //do nothing
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        contents = "";
    }

    private void scan_qr() {
        Utils.showLoading(getActivity());
        Retrofit retrofit = new Retrofit.Builder().baseUrl(getString(R.string.frappeurl))
                .addConverterFactory(GsonConverterFactory.create()).build();
        ApiServices apiServices = retrofit.create(ApiServices.class);
        Call<ScanQRRes> call = apiServices.getScannedCustomer(contents);
        call.enqueue(new Callback<ScanQRRes>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<ScanQRRes> call, Response<ScanQRRes> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus().toString().equals("200")) {
                        Utils.dismiss();
                        ScanQRRes resObj = response.body();
                        List<ScanQRDatum> list = resObj.getInfo();
                        Notify.Toast(getString(R.string.select_cus_from_dropdown));
                        customerSearch.setText(list.get(0).getName());
                        customerSearch.requestFocus();
                    } else {
                        Utils.dismiss();
                        Toast.makeText(getContext(), "Process Failed", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Utils.dismiss();
                    Toast.makeText(getContext(), "Process Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ScanQRRes> call, Throwable t) {
                Utils.dismiss();
//                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }

        });
    }

}