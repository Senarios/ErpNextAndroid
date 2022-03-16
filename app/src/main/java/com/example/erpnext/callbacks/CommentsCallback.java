package com.example.erpnext.callbacks;


import com.example.erpnext.models.Comment;

public interface CommentsCallback {
    void onDeleteCommentClick(Comment comment);
}
