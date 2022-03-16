package com.example.erpnext.adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.adapters.viewHolders.EmailViewHolder;
import com.example.erpnext.callbacks.CreditLimitCallback;
import com.example.erpnext.models.CreditLimit;
import com.example.erpnext.models.Email;
import com.example.erpnext.utils.Notify;

import java.util.ArrayList;
import java.util.List;

public class EmailAdapter extends RecyclerView.Adapter<EmailViewHolder> {

    private final Context context;
    private final CreditLimitCallback callback;
    private List<Email> itemList;

    public EmailAdapter(Context context, List<Email> itemList, CreditLimitCallback callback) {
        this.context = context;
        this.itemList = itemList;
        this.callback = callback;
    }

    @NonNull
    @Override
    public EmailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.email_list_item, parent, false);
        return new EmailViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull EmailViewHolder holder, int position) {
        holder.setData(context, itemList.get(position), callback, position, this);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void filteredList(ArrayList<Email> filteredList) {
        this.itemList = filteredList;
        notifyDataSetChanged();
    }

    public void addItem(List<Email> items) {
        itemList.addAll(items);
        notifyDataSetChanged();
    }

    public void addItem(Email item) {
        List<Email> emails = new ArrayList<>();
        if (!containsEmail(itemList, item.getEmail())) {
            if (item != null) {
                if (item.isPrimary()) {
                    for (Email email : itemList) {
                        email.setPrimary(false);
                        emails.add(email);
                    }
                    itemList = emails;
                }
                itemList.add(item);
                Notify.Toast("Added");
                notifyItemInserted(itemList.size() - 1);
            }
        } else Notify.Toast("Already added");
    }

    public List<Email> getAllItems() {
        return this.itemList;
    }


    public void removeItem(CreditLimit item, int position) {
        itemList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, itemList.size());
    }

    public void removeAll() {
        itemList.clear();
        notifyDataSetChanged();
    }

    public void primaryChanged(Email item, int position) {
        List<Email> emails = new ArrayList<>();
        itemList.get(position).setPrimary(true);
        for (int i = 0; i < itemList.size(); i++) {
            if (i != position) {
                itemList.get(i).setPrimary(false);
            }
        }
        notifyDataSetChanged();
    }

    public boolean containsEmail(final List<Email> list, final String email) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return list.stream().anyMatch(o -> o.getEmail().toLowerCase().contains(email));
        } else return false;
    }
}