package com.example.erpnext.utils;


import com.example.erpnext.R;
import com.example.erpnext.app.MainApp;

public class Configurations {

    public static final Environments environment = Environments.Development;
    public static final long DB_VERSION = 1;

    public static boolean isProduction() {
        boolean isProduction;
        try {
            isProduction = environment == Environments.Production;
        } catch (Exception e) {
            isProduction = false;
        }
        return isProduction;
    }

    public static boolean isDevelopment() {
        boolean isDevelopment;
        try {
            isDevelopment = environment == Environments.Development;
        } catch (Exception e) {
            isDevelopment = false;
        }
        return isDevelopment;
    }

    public static boolean isLocal() {
        boolean isLocal;
        try {
            isLocal = environment == Environments.Local;
        } catch (Exception e) {
            isLocal = false;
        }
        return isLocal;
    }

    public static String getBaseUrl() {
        if (environment == Environments.Development)
            return MainApp.getAppContext().getString(R.string.development);
        else return null;

    }

    public static String getUploadsUrl() {
        if (environment == Environments.Development)
            return MainApp.getAppContext().getString(R.string.uploads);
        else return null;

    }

    public static String getEnvName() {
        return environment.toString();
    }

    public enum Environments {
        Development,
        Production,
        Local,
    }
}


