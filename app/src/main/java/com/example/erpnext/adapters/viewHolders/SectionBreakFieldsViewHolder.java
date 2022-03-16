package com.example.erpnext.adapters.viewHolders;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.adapters.SectionTableAdapter;
import com.example.erpnext.callbacks.SectionBreaksCallback;
import com.example.erpnext.models.Field;

import java.util.ArrayList;
import java.util.List;


public class SectionBreakFieldsViewHolder extends RecyclerView.ViewHolder implements SectionBreaksCallback {

    public TextView name;
    public RecyclerView tableRv;
    public SectionTableAdapter adapter;
    public ImageView arrow;
    public View parent;
    public boolean isEditMode;
    View sectionBreakLine;
    Context context;

    public SectionBreakFieldsViewHolder(@NonNull View itemView) {
        super(itemView);
        parent = itemView;
        name = itemView.findViewById(R.id.table_name);
        arrow = itemView.findViewById(R.id.down_arrow);
        tableRv = itemView.findViewById(R.id.table_fields);
        sectionBreakLine = itemView.findViewById(R.id.break_line);


    }

    public void setData(Context context, List<Field> item, SectionBreaksCallback callback, boolean isEditMode) {
        this.isEditMode = isEditMode;
        ifPOSOpenEntry(item.get(0).getParent());
        name.setText(item.get(0).getLabel());
        item.remove(0);
        List<Field> fields = new ArrayList<>();
        for (int i = 0; i < item.size(); i++) {
            fields.add(item.get(i));
        }
        if (!fields.isEmpty()) setTableAdapter(context, fields);
        arrow.setBackgroundResource(R.drawable.ic_baseline_keyboard_arrow_down_24);

//        if (item.getLinks() != null && !item.getLinks().isEmpty()) {
//            setLinkAdapter(context, item.getLinks());
//        }

        parent.setOnClickListener(view -> {
            if (!item.get(0).getParent().equalsIgnoreCase("POS Opening Entry")) {
                if (tableRv.getVisibility() == View.VISIBLE) {
                    tableRv.setVisibility(View.GONE);
                    arrow.setBackgroundResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
                } else {
                    arrow.setBackgroundResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
                    tableRv.setVisibility(View.VISIBLE);
                }
            }
        });
//        setStacKImageView();

    }

    private void ifPOSOpenEntry(String parent) {
        if (parent.equalsIgnoreCase("POS Opening Entry")) {
            name.setVisibility(View.GONE);
            arrow.setVisibility(View.GONE);
            sectionBreakLine.setVisibility(View.GONE);
            tableRv.setVisibility(View.VISIBLE);
        }
    }

    private void setTableAdapter(Context context, List<Field> linkList) {
        this.context = context;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        adapter = new SectionTableAdapter(context, linkList, this, isEditMode);
        tableRv.setLayoutManager(linearLayoutManager);
        tableRv.setAdapter(adapter);
    }


    @Override
    public void onSectionClick(List<Field> fieldList) {

    }
}
