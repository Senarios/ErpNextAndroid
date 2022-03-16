package com.example.erpnext.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WareMessage {

        @SerializedName("keys")
        @Expose
        private List<String> keys = null;
        @SerializedName("values")
        @Expose
        private List<List<String>> values = null;

        public List<String> getKeys() {
            return keys;
        }

        public void setKeys(List<String> keys) {
            this.keys = keys;
        }

        public List<List<String>> getValues() {
            return values;
        }

        public void setValues(List<List<String>> values) {
            this.values = values;
        }


}
