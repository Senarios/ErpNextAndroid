package com.example.erpnext.models;

public class Profile {
    String name;
    String daysAgo;
    String noOfLikes;
    String noOfReply;
    boolean isEnabled;

    public Profile(String name, String daysAgo, String noOfLikes, String noOfReply, boolean isEnabled) {
        this.name = name;
        this.daysAgo = daysAgo;
        this.noOfLikes = noOfLikes;
        this.noOfReply = noOfReply;
        this.isEnabled = isEnabled;
    }

    public Profile() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDaysAgo() {
        return daysAgo;
    }

    public void setDaysAgo(String daysAgo) {
        this.daysAgo = daysAgo;
    }

    public String getNoOfLikes() {
        return noOfLikes;
    }

    public void setNoOfLikes(String noOfLikes) {
        this.noOfLikes = noOfLikes;
    }

    public String getNoOfReply() {
        return noOfReply;
    }

    public void setNoOfReply(String noOfReply) {
        this.noOfReply = noOfReply;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }
}
