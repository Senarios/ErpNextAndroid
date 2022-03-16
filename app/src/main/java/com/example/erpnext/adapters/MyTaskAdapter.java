package com.example.erpnext.adapters;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.app.AppSession;
import com.example.erpnext.models.Info;
import com.example.erpnext.models.MyTaskUpdateRes;
import com.example.erpnext.models.TrackLocRes;
import com.example.erpnext.network.ApiServices;
import com.example.erpnext.utils.DateTime;
import com.example.erpnext.utils.Notify;
import com.example.erpnext.utils.Utils;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyTaskAdapter extends RecyclerView.Adapter<MyTaskAdapter.ViewHolder> {
    private ArrayList<Info> myTaskitemArrayList = new ArrayList<>();
    private final Context context;
    BottomSheetDialog bottomSheetDialog;
    public static final String MY_PREF = "my pref";
    private TaskUpdate taskUpdate;
    Dialog dialog;

    public MyTaskAdapter(ArrayList<Info> myTaskitems, Context context, TaskUpdate taskUpdate) {
        this.myTaskitemArrayList = myTaskitems;
        this.context = context;
        this.taskUpdate = taskUpdate;
    }

    public interface TaskUpdate {
        void taskdoupdate();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.mytask_invoice, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.name.setText(myTaskitemArrayList.get(position).getTaskName()+" at "+myTaskitemArrayList.get(position).getShopName());
        holder.dayAgo.setText("last modified: " + DateTime.getFormatedDateTimeString(myTaskitemArrayList.get(position).getDate()));
        holder.enabled.setText(myTaskitemArrayList.get(position).getShopStatus());
        holder.comment.setText(myTaskitemArrayList.get(position).getComment());

        holder.menu_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog = new BottomSheetDialog(context, R.style.BottomSheetTheme);
                View bsView = LayoutInflater.from(context).inflate(R.layout.mytask_bs_layout,
                        v.findViewById(R.id.bottom_sheet));

                bsView.findViewById(R.id.pending).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREF, Context.MODE_PRIVATE).edit();
                        editor.putString("statusKey", "pending");
                        editor.putString("taskName", myTaskitemArrayList.get(position).getTaskName());
                        editor.putString("shopName", myTaskitemArrayList.get(position).getShopName());
                        editor.putString("lat", myTaskitemArrayList.get(position).getShopLat());
                        editor.putString("long", myTaskitemArrayList.get(position).getShopLng());
                        editor.apply();
                        taskUpdate.taskdoupdate();
                        bottomSheetDialog.cancel();
                    }
                });
                bsView.findViewById(R.id.completed).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREF, Context.MODE_PRIVATE).edit();
                        editor.putString("statusKey", "completed");
                        editor.putString("taskName", myTaskitemArrayList.get(position).getTaskName());
                        editor.putString("shopName", myTaskitemArrayList.get(position).getShopName());
                        editor.putString("lat", myTaskitemArrayList.get(position).getShopLat());
                        editor.putString("long", myTaskitemArrayList.get(position).getShopLng());
                        editor.apply();
                        taskUpdate.taskdoupdate();
                        bottomSheetDialog.cancel();
                    }
                });
                bsView.findViewById(R.id.shopclosed).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREF, Context.MODE_PRIVATE).edit();
                        editor.putString("statusKey", "shop closed");
                        editor.putString("taskName", myTaskitemArrayList.get(position).getTaskName());
                        editor.putString("shopName", myTaskitemArrayList.get(position).getShopName());
                        editor.putString("lat", myTaskitemArrayList.get(position).getShopLat());
                        editor.putString("long", myTaskitemArrayList.get(position).getShopLng());
                        editor.apply();
                        taskUpdate.taskdoupdate();
                        bottomSheetDialog.cancel();
                    }
                });
                bsView.findViewById(R.id.nopurchase).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREF, Context.MODE_PRIVATE).edit();
                        editor.putString("statusKey", "no purchase");
                        editor.putString("taskName", myTaskitemArrayList.get(position).getTaskName());
                        editor.putString("shopName", myTaskitemArrayList.get(position).getShopName());
                        editor.putString("lat", myTaskitemArrayList.get(position).getShopLat());
                        editor.putString("long", myTaskitemArrayList.get(position).getShopLng());
                        editor.apply();
                        taskUpdate.taskdoupdate();

                        bottomSheetDialog.cancel();
                    }
                });
                bsView.findViewById(R.id.add_comment).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog = new Dialog(context);
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
                        add.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREF, Context.MODE_PRIVATE).edit();
                                editor.putString("added_comment", note_edit.getText().toString());
                                editor.putString("taskName", myTaskitemArrayList.get(position).getTaskName());
                                editor.putString("shopName", myTaskitemArrayList.get(position).getShopName());
                                editor.putString("lat", myTaskitemArrayList.get(position).getShopLat());
                                editor.putString("long", myTaskitemArrayList.get(position).getShopLng());
                                editor.apply();
                                taskUpdate.taskdoupdate();
                                dialog.dismiss();
                            }
                        });
                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });

                        bottomSheetDialog.cancel();
                    }
                });

                bottomSheetDialog.setContentView(bsView);
                bottomSheetDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return myTaskitemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, dayAgo, noOfReplys, enabled,shopName,comment;
        LinearLayout mainlayout;
        ImageView menu_more;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            shopName = itemView.findViewById(R.id.name1);
            dayAgo = itemView.findViewById(R.id.daysAgo);
            noOfReplys = itemView.findViewById(R.id.noOfReplys);
            enabled = itemView.findViewById(R.id.enabled);
            mainlayout = itemView.findViewById(R.id.mainlayout);
            menu_more = itemView.findViewById(R.id.menu_more_tasts);
            comment = itemView.findViewById(R.id.show_comment);

        }
    }

}