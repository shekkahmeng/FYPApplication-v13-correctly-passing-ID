package com.shekkahmeng.fypapplication.db;

import android.graphics.Bitmap;

/**
 * Created by shekkahmeng on 8/10/15.
*/
public class DownloadedEvent {
    private long eventRowID;
    private String eventName;
    private String eventWebsite;
    private String eventDate;
    private String eventContactName;
    private String eventContact;
    private String shortName;
    private String chairmanName;
    private String chairmanEmail;
    private String conferencePhone;
    private String systemEmail;
    private String secretaristAddress;
    private String eventLocation;
    private String eventImage;
    private boolean eventJoin;
    private long eventReminder;

    public DownloadedEvent() {
    }

    public DownloadedEvent(long eventRowID, String eventName, String eventWebsite, String eventDate, String eventContact, String eventContactName, String shortName, String chairmanName, String conferencePhone, String chairmanEmail, String systemEmail, String secretaristAddress, String eventLocation, String eventImage, boolean eventJoin, long eventReminder) {
        this.eventRowID = eventRowID;
        this.eventName = eventName;
        this.eventWebsite = eventWebsite;
        this.eventDate = eventDate;
        this.eventContact = eventContact;
        this.eventContactName = eventContactName;
        this.shortName = shortName;
        this.chairmanName = chairmanName;
        this.conferencePhone = conferencePhone;
        this.chairmanEmail = chairmanEmail;
        this.systemEmail = systemEmail;
        this.secretaristAddress = secretaristAddress;
        this.eventLocation = eventLocation;
        this.eventImage = eventImage;
        this.eventJoin = eventJoin;
        this.eventReminder = eventReminder;
    }

    public long getEventRowID() {
        return eventRowID;
    }

    public void setEventRowID(long eventRowID) {
        this.eventRowID = eventRowID;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventWebsite() {
        return eventWebsite;
    }

    public void setEventWebsite(String eventWebsite) {
        this.eventWebsite = eventWebsite;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventContactName() {
        return eventContactName;
    }

    public void setEventContactName(String eventContactName) {
        this.eventContactName = eventContactName;
    }

    public String getEventContact() {
        return eventContact;
    }

    public void setEventContact(String eventContact) {
        this.eventContact = eventContact;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getChairmanName() {
        return chairmanName;
    }

    public void setChairmanName(String chairmanName) {
        this.chairmanName = chairmanName;
    }

    public String getChairmanEmail() {
        return chairmanEmail;
    }

    public void setChairmanEmail(String chairmanEmail) {
        this.chairmanEmail = chairmanEmail;
    }

    public String getSystemEmail() {
        return systemEmail;
    }

    public void setSystemEmail(String systemEmail) {
        this.systemEmail = systemEmail;
    }

    public String getConferencePhone() {
        return conferencePhone;
    }

    public void setConferencePhone(String conferencePhone) {
        this.conferencePhone = conferencePhone;
    }

    public String getSecretaristAddress() {
        return secretaristAddress;
    }

    public void setSecretaristAddress(String secretaristAddress) {
        this.secretaristAddress = secretaristAddress;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public String getEventImage() {
        return eventImage;
    }

    public void setEventImage(String eventImage) {
        this.eventImage = eventImage;
    }

    public boolean isEventJoin() {
        return eventJoin;
    }

    public void setEventJoin(boolean eventJoin) {
        this.eventJoin = eventJoin;
    }

    public long getEventReminder() {
        return eventReminder;
    }

    public void setEventReminder(long eventReminder) {
        this.eventReminder = eventReminder;
    }
}
