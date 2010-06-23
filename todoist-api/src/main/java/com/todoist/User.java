package com.todoist;

import java.util.TimeZone;

import org.codehaus.jackson.annotate.JsonProperty;

public class User {
    public enum SortOrder {
        OLDEST_DATES_FIRST, OLDEST_DATES_LAST;
    }

    public enum TimeFormat {
        _24_HOUR, _12_HOUR;
    }

    public enum DateFormat {
        DDMMYYYY, MMDDYYYY
    }

    private String email;
    @JsonProperty(value = "full_name")
    private String fullName;
    private Integer id;
    @JsonProperty(value = "api_token")
    private String apiToken;
    @JsonProperty(value = "start_page")
    private String startPage;
    @JsonProperty("timezone")
    private TimeZone timeZone;
    // TODO timezoneOffset
    @JsonProperty(value = "tz_offset")
    private Object timeZoneOffset;
    @JsonProperty(value = "time_format")
    private TimeFormat timeFormat;
    @JsonProperty(value = "date_format")
    private DateFormat dateFormat;
    @JsonProperty(value = "sort_order")
    private SortOrder sortOrder;
    private String twitter;
    private String jabber;
    private String msn;
    @JsonProperty(value = "mobile_number")
    private String mobileNumber;
    @JsonProperty(value = "mobile_host")
    private String mobileHost;

    // TODO make premium_until a date
    @JsonProperty(value = "premium_until")
    private String premiumUntil;

    // TODO make default_reminder an enum
    @JsonProperty(value = "default_reminder")
    private String defaultReminder;

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    public Integer getId() {
        return id;
    }

    public String getApiToken() {
        return apiToken;
    }

    public String getStartPage() {
        return startPage;
    }

    public TimeZone getTimeZone() {
        return timeZone;
    }

    public Object getTimeZoneOffset() {
        return timeZoneOffset;
    }

    public TimeFormat getTimeFormat() {
        return timeFormat;
    }

    public DateFormat getDateFormat() {
        return dateFormat;
    }

    public SortOrder getSortOrder() {
        return sortOrder;
    }

    public String getTwitter() {
        return twitter;
    }

    public String getJabber() {
        return jabber;
    }

    public String getMsn() {
        return msn;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getMobileHost() {
        return mobileHost;
    }

    public String getPremiumUntil() {
        return premiumUntil;
    }

    public String getDefaultReminder() {
        return defaultReminder;
    }
}
