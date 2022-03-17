package com.example.erpnext.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.models.StockEntryOfflineModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LogsStockEntryAdapter extends RecyclerView.Adapter<LogsStockEntryAdapter.ViewHolder> {
    private List<StockEntryOfflineModel> arrayList = new ArrayList();
    private Context context;
    private LogStockEntryUpdate logStockEntryUpdate;
    String seName,swarehouse,twarehouse;
    JSONArray jsonArray;

    public LogsStockEntryAdapter(List<StockEntryOfflineModel> arrayList, Context context, LogStockEntryUpdate logStockEntryUpdate) {
        this.arrayList = arrayList;
        this.context = context;
        this.logStockEntryUpdate = logStockEntryUpdate;
    }

    public interface LogStockEntryUpdate {
        void updateStockEntry(View view,int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.log_stockentry_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        JSONObject jresponse = null;
        try {
            jresponse = new JSONObject(arrayList.get(position).getData());
            seName = jresponse.getString("name");
            JSONArray jsonArray = jresponse.getJSONArray("items");
            for(int i=0;i<jsonArray.length();i++){
                swarehouse = jsonArray.getJSONObject(i).getString("s_warehouse");
                twarehouse = jsonArray.getJSONObject(i).getString("t_warehouse");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        holder.name.setText(seName +" "+ context.getString(R.string.failed_to_add));
        holder.swarehouse.setText(context.getString(R.string.swarehouse)+" "+ swarehouse);
        holder.twarehouse.setText(context.getString(R.string.twarehouse)+" " + twarehouse);
        holder.menu.setOnClickListener(v -> {
            logStockEntryUpdate.updateStockEntry(holder.menu,holder.getAdapterPosition());
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, status,swarehouse,twarehouse;
        ImageView menu;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.namelog);
            swarehouse = itemView.findViewById(R.id.swarehouse);
            twarehouse = itemView.findViewById(R.id.twarehouse);
            status = itemView.findViewById(R.id.status);
            menu = itemView.findViewById(R.id.log_menu_tasts);

        }
    }
}
