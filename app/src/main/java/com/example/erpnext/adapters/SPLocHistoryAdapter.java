package com.example.erpnext.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.models.SPLocHisDatum;

import java.util.ArrayList;

public class SPLocHistoryAdapter extends RecyclerView.Adapter<SPLocHistoryAdapter.ViewHolder> {
    private ArrayList<SPLocHisDatum> myTaskitemArrayList = new ArrayList<>();
    private Context context;
    private Abc abc;

    public SPLocHistoryAdapter(ArrayList<SPLocHisDatum> myTaskitemArrayList, Context context, Abc abc) {
        this.myTaskitemArrayList = myTaskitemArrayList;
        this.context = context;
        this.abc = abc;
    }

    public interface Abc {
        void gett(String salesPerson);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.mytask_invoice, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.name.setText(myTaskitemArrayList.get(position).getSalesPerson());
        holder.name1.setVisibility(View.VISIBLE);
        holder.name1.setText(myTaskitemArrayList.get(position).getSalesOfficer());
        holder.dayAgo.setVisibility(View.GONE);
        holder.lin.setVisibility(View.GONE);
        holder.menu.setVisibility(View.GONE);
        holder.enabled.setText("Enabled");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abc.gett(myTaskitemArrayList.get(position).getSalesPerson());
            }
        });
    }

    @Override
    public int getItemCount() {
        return myTaskitemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, dayAgo, enabled, name1;
        LinearLayout lin;
        ImageView menu;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            name1 = itemView.findViewById(R.id.name1);
            lin = itemView.findViewById(R.id.linlay);
            dayAgo = itemView.findViewById(R.id.daysAgo);
            enabled = itemView.findViewById(R.id.enabled);
            menu = itemView.findViewById(R.id.menu_more_tasts);
        }
    }
}
