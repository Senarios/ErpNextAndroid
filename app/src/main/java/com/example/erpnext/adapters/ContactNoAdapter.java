package com.example.erpnext.adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.adapters.viewHolders.ContactNoViewHolder;
import com.example.erpnext.callbacks.CreditLimitCallback;
import com.example.erpnext.models.ContactNo;
import com.example.erpnext.models.CreditLimit;
import com.example.erpnext.utils.Notify;

import java.util.ArrayList;
import java.util.List;

public class ContactNoAdapter extends RecyclerView.Adapter<ContactNoViewHolder> {

    private final Context context;
    private final CreditLimitCallback callback;
    private List<ContactNo> itemList;

    public ContactNoAdapter(Context context, List<ContactNo> itemList, CreditLimitCallback callback) {
        this.context = context;
        this.itemList = itemList;
        this.callback = callback;
    }

    @NonNull
    @Override
    public ContactNoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.email_list_item, parent, false);
        return new ContactNoViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull ContactNoViewHolder holder, int position) {
        holder.setData(context, itemList.get(position), callback, position, this);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void filteredList(ArrayList<ContactNo> filteredList) {
        this.itemList = filteredList;
        notifyDataSetChanged();
    }

    public void addItem(List<ContactNo> items) {
        itemList.addAll(items);
        notifyDataSetChanged();
    }

    public void addItem(ContactNo item) {
        List<ContactNo> emails = new ArrayList<>();
        if (!containsEmail(itemList, item.getNumber())) {
            if (item != null) {
                if (item.isPrimary()) {
                    for (ContactNo email : itemList) {
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

    public List<ContactNo> getAllItems() {
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

    public void primaryChanged(ContactNo item, int position) {
        List<ContactNo> emails = new ArrayList<>();
        itemList.get(position).setPrimary(true);
        for (int i = 0; i < itemList.size(); i++) {
            if (i != position) {
                itemList.get(i).setPrimary(false);
            }
        }
        notifyDataSetChanged();
    }

    public boolean containsEmail(final List<ContactNo> list, final String email) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return list.stream().anyMatch(o -> o.getNumber().toLowerCase().contains(email));
        } else return false;
    }
}