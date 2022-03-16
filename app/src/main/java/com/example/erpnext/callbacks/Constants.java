package com.example.erpnext.callbacks;

import android.content.Context;

import androidx.fragment.app.Fragment;
public interface Constants {
    void OnChange(Fragment fragment, String tag);

    interface SharedPreference {
        String Preference = "SimX";
        int Preference_Mode = Context.MODE_PRIVATE;
        String Login_Boolean = "Login";
        String TYPE = "text_type";
        String ACCESS_TOKEN="Access_token";
        String Resource = "Resource";
        String LinkedIn_Code = "Code";
        String Linkedin_ID="id";
        String Fullname="fullname";
        String Email="email";
        String LINKED_IN_LINK="linked_in_link";
        String HOURLY_RATE="hourly_rate";
        String USER="user";
        String FCM="fcm_id";
        String USER_TYPE="user_type";
        String QB_USER="qb_user_sp";
        String SUB_AWS="subscribed to aws";
        String B_LINKEDIN="linkedbolean";
        String B_TWITTER="twitterbolean";
        String iS_COMMENT="iscomment";
        String IMAGE_META="image_metadata";
        String ISUPLOADING="uploading video";
        String S3_REQUEST="pending s3 request";
        String ISCVUPLOADED="cvuploaded";
    }
}
