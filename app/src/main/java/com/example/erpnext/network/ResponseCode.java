package com.example.erpnext.network;


public class ResponseCode {

    public static int SUCCESS = 200;
    public static int INTERNAL_SERVER_ERROR = 500;
    public static int FORBIDDEN = 403;
    public static int UN_AUTHORIZED = 401;
    public static int ERROR = 422;
    public static int APP_NOT_UPDATED = 304;

    public static boolean isBetweenSuccessRange(int reqCode) {
        try {
            return reqCode > 199 && reqCode < 300;
        } catch (Exception e) {
            return true;
        }
    }
}
