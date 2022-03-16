package com.example.erpnext.models;

import androidx.room.Embedded;
import androidx.room.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class Message {

    @SerializedName("charts")
    @Expose
    @Embedded
    private Charts charts;
    @SerializedName("shortcuts")
    @Expose
    @Embedded
    private Shortcuts shortcuts;
    @SerializedName("cards")
    @Expose
    @Embedded
    private Cards cards;
    @SerializedName("onboarding")
    @Expose
    @Embedded
    private Object onboarding;
    @SerializedName("allow_customization")
    @Expose
    private Boolean allowCustomization;

    public Charts getCharts() {
        return charts;
    }

    public void setCharts(Charts charts) {
        this.charts = charts;
    }

    public Shortcuts getShortcuts() {
        return shortcuts;
    }

    public void setShortcuts(Shortcuts shortcuts) {
        this.shortcuts = shortcuts;
    }

    public Cards getCards() {
        return cards;
    }

    public void setCards(Cards cards) {
        this.cards = cards;
    }

    public Object getOnboarding() {
        return onboarding;
    }

    public void setOnboarding(Object onboarding) {
        this.onboarding = onboarding;
    }

    public Boolean getAllowCustomization() {
        return allowCustomization;
    }

    public void setAllowCustomization(Boolean allowCustomization) {
        this.allowCustomization = allowCustomization;
    }
}
