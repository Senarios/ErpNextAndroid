package com.example.erpnext.network.serializers;

public enum ErrorType {
    Email("email", "Email"),
    ConfirmPassword("confirm_password", "Confirm Password"),
    Contact("contact_no", "Contact number"),
    EndRental("base", "End Rental"),
    PromoCode("promo_code", "");
    private final String key;
    private final String alphaNumeric;

    ErrorType(String key, String alphaNumericName) {
        this.key = key;
        this.alphaNumeric = alphaNumericName;
    }

    public String getAlphaNumeric() {
        return alphaNumeric;
    }

    @Override
    public String toString() {
        return key;
    }
}
