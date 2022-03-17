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
import com.example.erpnext.models.MyTaskOfflineModel;

import java.util.ArrayList;
import java.util.List;

public class LogsTasksAdapter extends RecyclerView.Adapter<LogsTasksAdapter.ViewHolder> {
    private List<MyTaskOfflineModel> arrayList = new ArrayList();
    private Context context;
    private LogTaskUpdate taskUpdate;

    public LogsTasksAdapter(List<MyTaskOfflineModel> arrayList, Context context, LogTaskUpdate taskUpdate) {
        this.arrayList = arrayList;
        this.context = context;
        this.taskUpdate = taskUpdate;
    }

    public interface LogTaskUpdate {
        void taskToUpdate(View view, int position);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.log_mytasks_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.name.setText(arrayList.get(position).getTaskName()+context.getString(R.string.task_failed_to_add));
        holder.status.setText(arrayList.get(position).getShopStat());
        holder.menu.setOnClickListener(v->{
            taskUpdate.taskToUpdate(holder.menu, holder.getAdapterPosition());
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
            menu = itemView.findViewById(R.id.log_menu_tasts);

        }
    }
}
