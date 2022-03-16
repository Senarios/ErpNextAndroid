package com.example.erpnext.network;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.example.erpnext.R;
import com.example.erpnext.app.MainApp;
import com.example.erpnext.network.serializers.response.BaseResponse;
import com.example.erpnext.utils.Configurations;
import com.example.erpnext.utils.Logger;
import com.example.erpnext.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Network {
    public static OkHttpClient.Builder httpClient;
    private static Network object;
    private final ApiServices services;
    private Retrofit networkClient = null;

    private Network() {
        Gson gson = new GsonBuilder().create();
        Context context = MainApp.getAppContext();
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.retryOnConnectionFailure(true);
        httpClient.connectTimeout(30, TimeUnit.SECONDS);
        httpClient.readTimeout(40, TimeUnit.SECONDS);
        httpClient.writeTimeout(40, TimeUnit.SECONDS);
        httpClient.addInterceptor(getLoggingIntercept());
        httpClient.addInterceptor(chain -> {
            PackageInfo pInfo = null;
            try {
                pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            String version = pInfo.versionName;
//            String token = User.init().getToken();
            String token = null;
            if (token != null) {
                Request request = chain.request().newBuilder().addHeader("Authorization", "Bearer " + token)
                        .build();
                return chain.proceed(request);
            } else {
                Request request = chain.request().newBuilder()
                        .build();
                return chain.proceed(request);
            }
        });
//        OkHttpClient client = getUnsafeOkHttpClient();
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        httpClient.cookieJar(new JavaNetCookieJar(cookieManager))
                .build();

        networkClient = new Retrofit.Builder()
                .baseUrl(Configurations.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build();
        services = networkClient.create(ApiServices.class);
        Network.httpClient = httpClient;

    }

    public synchronized static Network getInstance() {
        if (object == null) {
            object = new Network();
        }
        return object;
    }

    public static void clearInstance() {
        object = null;
    }

    public static ApiServices apis() {
        return Network.getInstance().getApiServices();
    }

    public static String getBaseUrl() {
        return getNetworkClient().baseUrl().toString();
    }

    private static Retrofit getNetworkClient() {
        if (object == null)
            getInstance();
        return getInstance().networkClient;
    }

    public static BaseResponse parseErrorResponse(Response response) {
        BaseResponse errorResponse = null;
        try {
            if (ResponseCode.isBetweenSuccessRange(response.code())) {
                return (BaseResponse) response.body();
            } else {
                Converter<ResponseBody, BaseResponse> errorConverter =
                        getInstance().getNetworkClient().responseBodyConverter(BaseResponse.class, new Annotation[0]);
                errorResponse = errorConverter.convert(response.errorBody());
                errorResponse.setCode(response.code());
                if (errorResponse.getMessage() == null
                        || errorResponse.getMessage().equalsIgnoreCase("")) {
                    errorResponse.setMessage("Unable to communicate with " + Utils.getString(R.string.app_name) + " server, please try again.");
                }
                return errorResponse;
            }
        } catch (Exception e) {
            Logger.log(Logger.EXCEPTION, e);
            errorResponse = new BaseResponse();
            errorResponse.setCode(response.code());
            String errorString;
            try {
                errorString = response.errorBody().string();
            } catch (Exception ex) {
                errorString = Utils.getString(R.string.exceptionInErrorResponse);
            }
            if (errorString == null || errorString.trim().equalsIgnoreCase("")) {
                errorString = "Unable to communicate with " + Utils.getString(R.string.app_name) + " server, try again.";
            }
            errorResponse.setMessage(errorString);
            return errorResponse;
        }
    }

    public ApiServices getApiServices() {
        return object.services;
    }

    private TrustManager[] getWrappedTrustManagers(TrustManager[] trustManagers) {
        final X509TrustManager originalTrustManager = (X509TrustManager) trustManagers[0];
        return new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return originalTrustManager.getAcceptedIssuers();
                    }

                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        try {
                            originalTrustManager.checkClientTrusted(certs, authType);
                        } catch (CertificateException ignored) {
                        }
                    }

                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        try {
                            originalTrustManager.checkServerTrusted(certs, authType);
                        } catch (CertificateException ignored) {
                        }
                    }
                }
        };
    }

    public Interceptor getLoggingIntercept() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(message -> {
            /*if (Configurations.isProduction() && AppSession.getBoolean(Constants.IS_LOGGED_IN)) {
                Logger.log("OkHttp", message);
//                Log.e("OKHTTP::"," "+message);
            } else {
                Logger.log("okHttp", message);
//                Log.e("OKHTTP::"," 2 ="+message);
            }*/
        });
        return logging.setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    public class BasicAuthInterceptor implements Interceptor {
        private final String credentials;

        public BasicAuthInterceptor(String user, String password) {
            this.credentials = Credentials.basic(user, password);
        }

        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Request authenticatedRequest = request.newBuilder()
                    .header("Authorization", credentials).build();
            return chain.proceed(authenticatedRequest);
        }
    }
}
