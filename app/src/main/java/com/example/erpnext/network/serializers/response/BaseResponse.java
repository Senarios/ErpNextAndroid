package com.example.erpnext.network.serializers.response;


import android.text.Html;

import com.example.erpnext.network.ResponseCode;
import com.example.erpnext.network.serializers.ErrorType;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Set;

import retrofit2.Response;

public class BaseResponse implements Serializable {

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("code")
    @Expose
    private int code;
    @SerializedName("error")
    @Expose
    private JsonObject errors = null;
    @SerializedName("_server_messages")
    @Expose
    private String serverMessages;

    public BaseResponse() {
    }

    public BaseResponse(String message) {
        setMessage(message);
    }

    public static boolean isSuccess(Response response) {
        try {
            return ResponseCode.isBetweenSuccessRange(response.code());
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isUnAuthorized(Response response) {
        try {
            return response.code() == ResponseCode.FORBIDDEN
                    || response.code() == ResponseCode.UN_AUTHORIZED;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isSessionExpired(Response response) {
        try {
            return response.code() == ResponseCode.FORBIDDEN;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean appNotUpdated(Response response) {
        try {
            return ((BaseResponse) response.body()).getCode() == ResponseCode.APP_NOT_UPDATED;
        } catch (Exception e) {
            return false;
        }
    }

    public String getServerMessages() {
        if (serverMessages != null) {
            Object object = null;
            String message = null;
            try {
                JSONArray jsonArray = new JSONArray(serverMessages);
                object = jsonArray.get(0);
                JSONObject jsonObject = new JSONObject(Html.fromHtml(object.toString()).toString());
                message = (String) jsonObject.get("message");
                return message;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return message;
        } else if (serverMessages != null) {
            return serverMessages;
        } else return getMessage();
    }

    public void setServerMessages(String serverMessages) {
        this.serverMessages = serverMessages;
    }

    private String ggetMessage(Object object) {
        String message = "";
        try {
            Field[] fields = object.getClass().getFields();

            for (int i = 0; i < fields.length; i++) {

                // get value of the fields
                Object value = null;

                value = fields[i].get(message);


                // print result
                System.out.println("Value of Field "
                        + fields[i].getName()
                        + " is " + value);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public JsonObject getErrors() {
        return errors;
    }

    public void setErrors(JsonObject errors) {
        this.errors = errors;
    }

    public String getErrorString() {
        String error = "";
        for (ErrorType type : ErrorType.values()) {
            Set<String> keySet = errors.keySet();
            if (keySet.contains(type.toString())) {
                JsonArray array = errors.getAsJsonArray(type.toString());
                if (array.size() > 0) {
                    error = error + array.get(0).getAsString() + "\n";
                }
            }
        }
        return error;
    }

}