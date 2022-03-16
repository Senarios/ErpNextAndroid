package com.example.erpnext.utils;

import android.Manifest;

import java.util.Arrays;
import java.util.List;

/**
 * Created by fah33m on 5/12/2017.
 */

public class Constants {
    public static final String IS_LOGGED_IN = "is_logged_in";
    public static final String IS_USER = "is_user";
    public static final String IS_ADMIN = "is_admin";
    public static final String FINE_lOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    public static final String COURSE_lOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    public static final String ALLOWED_CHARACTERS = "0123456789qwertyuiopasdfghjklzxcvbnm";
    public static final float DEFAULT_ZOOM = 16f;
    public static final String CHAT_SERVER_URL = "";
    public static final String DATABASE = "DATABASE";
    public static final String CAMERA_PERMISSIONS = Manifest.permission.CAMERA;
    public static final String AUTHENTICATION = "authentication";
    public static final String REMEMBER_ME = "rememberMe";
    public static final int SIGNUP = 1;
    public static final String ACTION_CANCEL_CALL = "actionCancelCall";
    public static final String ACTION_CALL_ACCEPT = "actionAcceptCall";
    public static final String ACTION_CALL_REJECTED = "actionRejectedCall";
    public static final String ACTION_FCM_TOKEN = "fcmToken";
    public static String[] REQUIRED_PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.MANAGE_EXTERNAL_STORAGE,
//            Manifest.permission.ACCESS_COARSE_LOCATION,
//            Manifest.permission.ACCESS_FINE_LOCATION,
//            Manifest.permission.CALL_PHONE,
//            Manifest.permission.RECORD_AUDIO,

    };
    public static String[] CAMERA_VIDEO = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,

    };
    public static String[] STORAGE_PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,

    };
    public static String[] GPS_PERMISSIONS = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
    };
    public static List<String> permList = Arrays.asList(REQUIRED_PERMISSIONS);
    public static String CALL_START_BASE = "call_start_base_time";

    public static class TwilioCall {
        public static final String CALL_SID_KEY = "CALL_SID";
        public static final String VOICE_CHANNEL_LOW_IMPORTANCE = "notification-channel-low-importance";
        public static final String VOICE_CHANNEL_HIGH_IMPORTANCE = "notification-channel-high-importance";
        public static final String INCOMING_CALL_INVITE = "INCOMING_CALL_INVITE";
        public static final String CANCELLED_CALL_INVITE = "CANCELLED_CALL_INVITE";
        public static final String INCOMING_CALL_NOTIFICATION_ID = "INCOMING_CALL_NOTIFICATION_ID";
        public static final String ACTION_ACCEPT = "ACTION_ACCEPT";
        public static final String ACTION_REJECT = "ACTION_REJECT";
        public static final String ACTION_INCOMING_CALL_NOTIFICATION = "ACTION_INCOMING_CALL_NOTIFICATION";
        public static final String ACTION_INCOMING_CALL = "ACTION_INCOMING_CALL";
        public static final String ACTION_CANCEL_CALL = "ACTION_CANCEL_CALL";
        public static final String ACTION_FCM_TOKEN = "ACTION_FCM_TOKEN";
        public static final String ON_GOING_CALL = "ON_GOING_CALL";
        public static final String CALL_DISCONNECTED = "CALL_DISCONNECTED";
        public static final String CALL_START_BASE = "CALL_START_BASE";
        public static final String NOTIFICATION_ID_KEY = "NOTIFICATION_ID";
        public static final String ACTION_VIDEO_NOTIFICATION = "VIDEO_NOTIFICATION";
        public static final String VIDEO_NOTIFICATION_ROOM_NAME = "VIDEO_NOTIFICATION_ROOM_NAME";
        public static final String VOICE_CHANNEL = "default";
        public static final String ACTION_REGISTRATION = "ACTION_REGISTRATION";
    }
}