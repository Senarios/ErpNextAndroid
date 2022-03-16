package com.example.erpnext.chatroom;


import com.example.erpnext.chatroom.chatnotification.MyResponse;
import com.example.erpnext.chatroom.chatnotification.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAToGDE0k:APA91bH2mKZmCsJZYNYpoZag8ptswyJ6sfw06B4T3RCiKcjtQgGl6n6GojQeAiXopOOxeEawdgT9yH0zBcTbUZ9NiE1CuQC-5Vswftoz3rkUPOLqkYMObRTid73X3Coc4OXm3rcsViH9"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
