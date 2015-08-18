package com.shekkahmeng.fypapplication.rest;

/**
 * Created by shekkahmeng on 7/31/2015.
 */
public class OnlineEvent {

    private long eventId;
    private String eventName;
    private String eventWebsite;
    private String eventDate;
    private String eventContactName;
    private String eventContact;
    private String eventImage;
    private String eventShortName;
    private String chairmanName;
    private String chairmanEmail;
    private String conferencePhone;
    private String systemEmail;
    private String secretaristAddress;
    private String eventLocation;
    private String eventHTML;


    public OnlineEvent() {
    }

    public OnlineEvent(long eventNo, String eventName, String eventWebsite, String eventDate, String eventContactName, String eventContact, String eventImage, String eventShortName, String chairmanName, String chairmanEmail, String conferencePhone, String systemEmail, String secretaristAddress, String eventLocation, String eventHTML) {
        this.eventId = eventNo;
        this.eventName = eventName;
        this.eventWebsite = eventWebsite;
        this.eventDate = eventDate;
        this.eventContactName = eventContactName;
        this.eventContact = eventContact;
        this.eventImage = eventImage;
        this.eventShortName = eventShortName;
        this.chairmanName = chairmanName;
        this.chairmanEmail = chairmanEmail;
        this.conferencePhone = conferencePhone;
        this.systemEmail = systemEmail;
        this.secretaristAddress = secretaristAddress;
        this.eventLocation = eventLocation;
        this.eventHTML = eventHTML;
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventWebsite() {
        return eventWebsite;
    }

    public void setEventWebsite(String eventWebsite) {
        this.eventWebsite = eventWebsite;
    }

    public String getEventContactName() {
        return eventContactName;
    }

    public void setEventContactName(String eventContactName) {
        this.eventContactName = eventContactName;
    }

    public String getEventImage() {
        return eventImage;
    }

    public void setEventImage(String eventImage) {
        this.eventImage = eventImage;
    }

    public String getEventContact() {
        return eventContact;
    }

    public void setEventContact(String eventContact) {
        this.eventContact = eventContact;
    }

    public String getEventShortName() {
        return eventShortName;
    }

    public void setEventShortName(String eventShortName) {
        this.eventShortName = eventShortName;
    }

    public String getChairmanName() {
        return chairmanName;
    }

    public void setChairmanName(String chairmanName) {
        this.chairmanName = chairmanName;
    }

    public String getConferencePhone() {
        return conferencePhone;
    }

    public void setConferencePhone(String conferencePhone) {
        this.conferencePhone = conferencePhone;
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

    public String getEventHTML() {
        return eventHTML;
    }

    public void setEventHTML(String eventHTML) {
        this.eventHTML = eventHTML;
    }
}
