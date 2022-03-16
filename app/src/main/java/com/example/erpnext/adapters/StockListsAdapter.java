package com.example.erpnext.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.adapters.viewHolders.StockListsViewHolder;
import com.example.erpnext.callbacks.ProfilesCallback;

import java.util.List;

public class StockListsAdapter extends RecyclerView.Adapter<StockListsViewHolder> {

    private final Context context;
    private final List<List<String>> profileList;
    private final ProfilesCallback callback;
    private final String doctype;

    public StockListsAdapter(Context context, List<List<String>> profileList, String doctype, ProfilesCallback callback) {
        this.context = context;
        this.profileList = profileList;
        this.callback = callback;
        this.doctype = doctype;
    }

    @NonNull
    @Override
    public StockListsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.pos_profile_list_item, parent, false);
        return new StockListsViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull StockListsViewHolder holder, int position) {
        holder.setData(context, profileList.get(position), callback, doctype, position);
    }

    @Override
    public int getItemCount() {
        return profileList.size();
    }

    //    public void filteredList(ArrayList<Profile> filteredList) {
//        this.profileList = filteredList;
//        notifyDataSetChanged();
//    }
    public void addItems(List<List<String>> profileList) {
        this.profileList.addAll(profileList);
        notifyDataSetChanged();
    }

    public void addNewItem(List<String> item) {
        profileList.add(0, item);
        notifyDataSetChanged();
    }


    public List<List<String>> getAllItems() {
        return profileList;
    }
}