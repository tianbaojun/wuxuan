package com.vtmer.yann.powernotes.bean;

/**
 * Created by Administrator on 2016/2/27.
 */
public class MyCalenderEvent {
    private String calendar_id;
    private String dtstart;
    private String dtend;
    private String duration;
    private String eventTimezone;
    private String rrule;
    private String title;

    @Override
    public String toString() {
        return "MyCalenderEvent{" +
                "calendar_id='" + calendar_id /*+ '\'' +
                ", dtstart='" + dtstart + '\'' +
                ", dtend='" + dtend + '\'' +
                ", duration='" + duration + '\'' +
                ", eventTimezone='" + eventTimezone + '\'' +
                ", rrule='" + rrule + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description*/ + '\'' +
                '}';
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String description;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCalendar_id() {
        return calendar_id;
    }

    public void setCalendar_id(String calendar_id) {
        this.calendar_id = calendar_id;
    }

    public String getDtstart() {
        return dtstart;
    }

    public void setDtstart(String dtstart) {
        this.dtstart = dtstart;
    }

    public String getDtend() {
        return dtend;
    }

    public void setDtend(String dtend) {
        this.dtend = dtend;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getEventTimezone() {
        return eventTimezone;
    }

    public void setEventTimezone(String eventTimezone) {
        this.eventTimezone = eventTimezone;
    }

    public String getRrule() {
        return rrule;
    }

    public void setRrule(String rrule) {
        this.rrule = rrule;
    }

}
