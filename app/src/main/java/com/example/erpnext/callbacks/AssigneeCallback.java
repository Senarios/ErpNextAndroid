package com.example.erpnext.callbacks;

import com.example.erpnext.adapters.viewHolders.AssigneesViewHolder;
import com.example.erpnext.adapters.viewHolders.AttachmentsViewHolder;
import com.example.erpnext.adapters.viewHolders.TagsViewHolder;
import com.example.erpnext.models.Assignment;
import com.example.erpnext.models.Attachment;

public interface AssigneeCallback {
    void onDeleteAssigneeClick(Assignment item, AssigneesViewHolder viewHolder, int position);

    void onDeleteTagClick(String tag, TagsViewHolder viewHolder, int position);

    void onDeleteAttachmentClick(Attachment attachment, AttachmentsViewHolder viewHolder, int position);

    void onAttachmentClick(Attachment attachment, AttachmentsViewHolder viewHolder, int position);
}
