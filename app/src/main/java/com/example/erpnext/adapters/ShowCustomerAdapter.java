package com.example.erpnext.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.models.ShowCustomerDatum;

import java.util.ArrayList;

public class ShowCustomerAdapter extends RecyclerView.Adapter<ShowCustomerAdapter.ViewHolder> {
    private ArrayList<ShowCustomerDatum> showCustomerData = new ArrayList<>();
    private Context context;
    private CustomerClick customerClick;

    public ShowCustomerAdapter(ArrayList<ShowCustomerDatum> showCustomerData, Context context, CustomerClick customerClick) {
        this.showCustomerData = showCustomerData;
        this.context = context;
        this.customerClick = customerClick;
    }

    public interface CustomerClick {
        void doClick(String name,String phone,String ref,String image);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.mytask_invoice, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(showCustomerData.get(position).getName());
        holder.phone.setVisibility(View.VISIBLE);
        holder.phone.setText("phone: "+ showCustomerData.get(position).getPhoneNumber());
        holder.comment.setText(showCustomerData.get(position).getReference());
        holder.menu_more.setVisibility(View.GONE);
        holder.dayAgo.setVisibility(View.GONE);
        holder.itemView.setOnClickListener(v ->{
            customerClick.doClick(showCustomerData.get(position).getName(),showCustomerData.get(position).getPhoneNumber()
            ,showCustomerData.get(position).getReference(),showCustomerData.get(position).getImage());
        });
    }

    @Override
    public int getItemCount() {
        return showCustomerData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, dayAgo, noOfReplys, enabled, phone, comment;
        LinearLayout mainlayout;
        ImageView menu_more;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            phone = itemView.findViewById(R.id.name1);
            dayAgo = itemView.findViewById(R.id.daysAgo);
            noOfReplys = itemView.findViewById(R.id.noOfReplys);
            enabled = itemView.findViewById(R.id.enabled);
            mainlayout = itemView.findViewById(R.id.mainlayout);
            menu_more = itemView.findViewById(R.id.menu_more_tasts);
            comment = itemView.findViewById(R.id.show_comment);
        }
    }
}
