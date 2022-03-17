package com.example.erpnext.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.models.AddCustomerOfflineModel;

import java.util.ArrayList;
import java.util.List;

public class LogsAddCustomerAdapter extends RecyclerView.Adapter<LogsAddCustomerAdapter.ViewHolder> {
    private List<AddCustomerOfflineModel> arrayList = new ArrayList();
    private Context context;
    private LogCustomerUpdate customerUpdate;

    public LogsAddCustomerAdapter(List<AddCustomerOfflineModel> arrayList, Context context, LogCustomerUpdate customerUpdate) {
        this.arrayList = arrayList;
        this.context = context;
        this.customerUpdate = customerUpdate;
    }

    public interface LogCustomerUpdate {
        void customerdoupdate(View view,int position);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.log_addcustomer_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.name.setText(arrayList.get(position).getCustomerName()+" is failed to add");
        holder.menu.setOnClickListener(v->{
            customerUpdate.customerdoupdate(holder.menu,holder.getAdapterPosition());
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,status;
        ImageView menu;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.namelog);
            status = itemView.findViewById(R.id.status);
            menu = itemView.findViewById(R.id.log_menu_customers);

        }
    }
}
