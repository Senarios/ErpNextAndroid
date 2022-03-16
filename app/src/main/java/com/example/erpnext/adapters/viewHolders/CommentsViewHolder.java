package com.example.erpnext.adapters.viewHolders;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.app.AppSession;
import com.example.erpnext.callbacks.CommentsCallback;
import com.example.erpnext.models.Comment;
import com.example.erpnext.utils.DateTime;
import com.example.erpnext.utils.Notify;
import com.example.erpnext.utils.Utils;

import org.jsoup.Jsoup;


public class CommentsViewHolder extends RecyclerView.ViewHolder {

    public TextView email, time, comment;
    public ImageView cross;
    public View parent;
    Context context;

    public CommentsViewHolder(@NonNull View itemView) {
        super(itemView);
        parent = itemView;
        email = itemView.findViewById(R.id.email);
        comment = itemView.findViewById(R.id.comment);
        time = itemView.findViewById(R.id.time);
        cross = itemView.findViewById(R.id.cross);


    }

    public void setData(Context context, Comment item, CommentsViewHolder holder, int position, CommentsCallback callback) {
        email.setText(item.getOwner());
        String userEmail = AppSession.get("email");
//        userEmail = userEmail.replace("%", "@");
        if (userEmail.equalsIgnoreCase(item.getOwner())) {
            cross.setVisibility(View.VISIBLE);
            email.setText("You");
        }
        time.setText(DateTime.getFormatedDateTimeString(item.getCreation()));
        comment.setText(Jsoup.parse(item.getContent()).text().replaceAll("\\<.*?>", ""));
        cross.setOnClickListener(v -> {
            if (Utils.isNetworkAvailable()) {
                callback.onDeleteCommentClick(item);
            } else Notify.Toast("You are in offline mode");
        });
    }
}
