package com.example.erpnext.adapters.viewHolders;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.activities.AddNewPOSProfileActivity;
import com.example.erpnext.callbacks.ProfilesCallback;
import com.example.erpnext.fragments.POSProfileDetailFragment;
import com.example.erpnext.models.Field;


public class InReadOnlyViewHolder extends RecyclerView.ViewHolder {

    public TextView fieldName, editFieldLabel;
    public CheckBox checkBox;


    public View parent;

    public InReadOnlyViewHolder(@NonNull View itemView) {
        super(itemView);
        parent = itemView;
        checkBox = itemView.findViewById(R.id.check_box);


    }

    public void setData(Context context, Field field, ProfilesCallback callback, boolean isEditMode) {
        if (isEditMode) {
            checkBox.setChecked(POSProfileDetailFragment.docDetail.getDocs().get(0).getDisabled() == 1);
        }
        if (field.getFieldtype().equalsIgnoreCase("Check")) {
            checkBox.setVisibility(View.VISIBLE);
            checkBox.setText(field.getLabel());

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        if (isEditMode) {
                            POSProfileDetailFragment.editedData.put(field.getFieldname(), "1");
                        } else {
                            AddNewPOSProfileActivity.data.put(field.getFieldname(), "1");
                        }
                    } else {
                        if (isEditMode) {
                            POSProfileDetailFragment.editedData.put(field.getFieldname(), "0");
                        } else {
                            AddNewPOSProfileActivity.data.put(field.getFieldname(), "0");
                        }
                    }
                }
            });
        }
//        parent.setOnClickListener(view -> callback.onLinkClick(item));

    }


}
