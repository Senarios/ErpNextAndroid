package com.example.erpnext.utils;

import android.util.Log;

import org.json.JSONObject;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class FileUpload {
    public static MultipartBody.Part requestBody(String key, File file) {
        MultipartBody.Part body;
        if (file != null) {
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            body = MultipartBody.Part.createFormData(key, file.getName(), requestFile);
        } else {
            RequestBody requestFile = RequestBody.create(MediaType.parse("text/plain"), "");
            body = MultipartBody.Part.createFormData(key, "", requestFile);
        }
        return body;
    }

    public static MultipartBody.Part requestBodyPDF(String key, File file) {
        MultipartBody.Part body;
        if (file != null) {
            RequestBody requestFile = RequestBody.create(MediaType.parse("application/pdf"), file);
            body = MultipartBody.Part.createFormData(key, file.getName(), requestFile);
        } else {
            RequestBody requestFile = RequestBody.create(MediaType.parse("text/plain"), "");
            body = MultipartBody.Part.createFormData(key, "", requestFile);
        }


        return body;
    }

    public static RequestBody uploadVoiceBuilder(String key, File file) {
        RequestBody body = RequestBody.create(MediaType.parse("audio/mp3"), file);
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart(key, file.getAbsolutePath(), body);
        return builder.build();
    }

    public static List<MultipartBody.Part> requestBody(String key, List<File> fileList) {

        List<MultipartBody.Part> imageParts = Arrays.asList(new MultipartBody.Part[fileList.size()]);

        for (int index = 0; index < fileList.size(); index++) {
            Log.d("TAG",
                    "requestUploadSurvey: survey image " + index + "  " + fileList.get(index).getPath());
            File file = new File(fileList.get(index).getPath());
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"),
                    file);
            imageParts.set(index, MultipartBody.Part.createFormData(key,
                    file.getName(),
                    requestBody));
        }
        return imageParts;
    }

    public static MultipartBody.Part requestBody(String key, File file, boolean isVideo) {
        MultipartBody.Part body;
        RequestBody requestFile = RequestBody.create(MediaType.parse("video/mp4"), file);
        body = MultipartBody.Part.createFormData(key, file.getName(), requestFile);
        return body;
    }

    public static RequestBody requestBody(String param) {
//        return RequestBody.create(okhttp3.MultipartBody.FORM, param);
        return RequestBody.create(MediaType.parse("text/plain"), String.valueOf(param));
    }

    public static RequestBody requestBody(Integer param) {
//        return RequestBody.create(okhttp3.MultipartBody.FORM, String.valueOf(param));
        return RequestBody.create(MediaType.parse("text/plain"), String.valueOf(param));
    }

    public static RequestBody requestBody(double param) {
//        return RequestBody.create(okhttp3.MultipartBody.FORM, String.valueOf(param));
        return RequestBody.create(MediaType.parse("text/plain"), String.valueOf(param));
    }

    public static RequestBody requestBody(Boolean param) {
//        return RequestBody.create(okhttp3.MultipartBody.FORM, String.valueOf(param));
        return RequestBody.create(MediaType.parse("text/plain"), String.valueOf(param));
    }

    public static Map<String, RequestBody> pmdcCertificate(String certificateNo, String
            expiryDate) {

        Map<String, RequestBody> map = new HashMap();
        map.put("pmdcRegistrationNumber", requestBody(certificateNo));
        map.put("pmdcCertificateExpiry", requestBody(expiryDate));
        return map;
    }


    public static Map<String, RequestBody> uploadAttachments(Integer isPrivate, String folder, String docname, String doctype) {

        Map<String, RequestBody> map = new HashMap();
        map.put("is_private", requestBody(isPrivate));
        map.put("folder", requestBody(folder));
        map.put("doctype", requestBody(doctype));
        map.put("docname", requestBody(docname));
        return map;
    }

    public static Map<String, RequestBody> stokeBalanceReport(String report_name, JSONObject filters) {

        Map<String, RequestBody> map = new HashMap();
        map.put("report_name", requestBody(report_name));
        map.put("filters", requestBody(filters.toString()));
        return map;
    }

    public static Map<String, RequestBody> updateProject(String projectName, String websiteLink, String description, int projectId) {

        Map<String, RequestBody> map = new HashMap();
        map.put("name", requestBody(projectName));
        map.put("server_link", requestBody(websiteLink));
        map.put("description", requestBody(description));
        map.put("id", requestBody(projectId));
        return map;
    }

    public static Map<String, RequestBody> saveDoc(JSONObject jsonObject, String action) {

        Map<String, RequestBody> map = new HashMap();
        map.put("doc", requestBody(jsonObject.toString()));
        if (action != null) {
            map.put("action", requestBody(action));
        }
        return map;
    }

    public static Map<String, RequestBody> runDoc(JSONObject jsonObject, String method) {

        Map<String, RequestBody> map = new HashMap();
        map.put("docs", requestBody(jsonObject.toString()));
        map.put("method", requestBody(method));

        return map;
    }

    public static Map<String, RequestBody> createGroup(String projectName, String description, int projectId) {

        Map<String, RequestBody> map = new HashMap();
        map.put("name", requestBody(projectName));
        map.put("description", requestBody(description));
        map.put("project_id", requestBody(projectId));
        return map;
    }

    public static Map<String, RequestBody> updateGroup(String projectName, String description, int groupId) {

        Map<String, RequestBody> map = new HashMap();
        map.put("name", requestBody(projectName));
        map.put("description", requestBody(description));
        map.put("id", requestBody(groupId));
        return map;
    }

    public static Map<String, RequestBody> createTask(String name, String description, int projectId, int groupId, String staffIds) {

        Map<String, RequestBody> map = new HashMap();
        map.put("name", requestBody(name));
        map.put("description", requestBody(description));
        map.put("project_id", requestBody(projectId));
        map.put("group_id", requestBody(groupId));
        map.put("staff_ids", requestBody(staffIds));
        return map;
    }

    public static Map<String, RequestBody> updateTask(String name, String description, int taskId) {

        Map<String, RequestBody> map = new HashMap();
        map.put("name", requestBody(name));
        map.put("description", requestBody(description));
        map.put("id", requestBody(taskId));
        return map;
    }

    public static Map<String, RequestBody> dmvLicenseMap(String licenseNumber, String
            expiryDate, String dob, String gender) {

        Map<String, RequestBody> map = new HashMap();
        map.put("license_number", requestBody(licenseNumber));
        map.put("expiry_date", requestBody(expiryDate));
        map.put("dob", requestBody(dob));
        map.put("gender", requestBody(gender));
        return map;
    }

    public static Map<String, RequestBody> addressMap(String street, String city, String
            state, String zipCode) {
        Map<String, RequestBody> map = new HashMap();
        map.put("street_address", requestBody(street));
        map.put("city", requestBody(city));
        map.put("state", requestBody(state));
        map.put("zip_code", requestBody(zipCode));
        return map;
    }

    public static Map<String, RequestBody> purchasePackageMap(Integer packageId,
                                                              boolean isCoverage) {
        Map<String, RequestBody> map = new HashMap();
        map.put("rental_package_id", requestBody(packageId));
        map.put("is_coverage", requestBody(isCoverage));
        return map;
    }

    public static Map<String, RequestBody> createMap(String name, String cardNo, String
            expiry, String street, String city, String state, String zipCode) {

        Map<String, RequestBody> map = new HashMap();
        map.put("card_no", requestBody(cardNo));
        map.put("expiry", requestBody(expiry));
        map.put("holder_name", requestBody(name));
        map.put("street", requestBody(street));
        map.put("city", requestBody(city));
        map.put("state", requestBody(state));
        map.put("zip", requestBody(zipCode));
        return map;
    }

    public static Map<String, RequestBody> ssnMap(String ssnNo) {

        Map<String, RequestBody> map = new HashMap();
        map.put("social_security_number", requestBody(ssnNo));
        return map;
    }

    public static Map<String, RequestBody> createMap(String token) {
        Map<String, RequestBody> map = new HashMap();
        map.put("Authorization", requestBody(token));
        return map;
    }

    public static Map<String, RequestBody> createVideoRequestMap(int id) {
        Map<String, RequestBody> map = new HashMap();
        map.put("id", requestBody(id));
        return map;
    }

    public static Map<String, RequestBody> createMap(int type) {
//
//        @Part("type") RequestBody type,
//        @Part("subtype") RequestBody subtype
        Map<String, RequestBody> map = new HashMap();
        map.put("documentType", requestBody(type));
        return map;
    }

    public static Map<String, RequestBody> createPrecautionsMap(String appointmentId, String
            precautions) {
        Map<String, RequestBody> map = new HashMap();
        map.put("appointmentId", requestBody(appointmentId));
        map.put("precaution", requestBody(precautions));
        return map;
    }

}
