package com.hackathon.myapplication2.db;

/**
 * Created by SwatiSh on 4/13/2015.
 */
public class HackBotEvent
{

    private int eventId;
    private String eventName;
    private long timeToTrigger;
    private long firstOccurrence;
    private long lastOccurrence;
    private long timesOccurred;
    private long probability;
    private long duration;
    private String pattern;
    private boolean repeatedWeekly;
    private long repeatInDays;

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public long getTimeToTrigger() {
        return timeToTrigger;
    }

    public void setTimeToTrigger(long timeToTrigger) {
        this.timeToTrigger = timeToTrigger;
    }

    public long getFirstOccurrence() {
        return firstOccurrence;
    }

    public void setFirstOccurrence(long firstOccurrence) {
        this.firstOccurrence = firstOccurrence;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public long getLastOccurrence() {
        return lastOccurrence;
    }

    public void setLastOccurrence(long lastOccurrence) {
        this.lastOccurrence = lastOccurrence;
    }

    public long getTimesOccurred() {
        return timesOccurred;
    }

    public void setTimesOccurred(long timesOccurred) {
        this.timesOccurred = timesOccurred;
    }

    public long getProbability() {
        return probability;
    }

    public void setProbability(long probability) {
        this.probability = probability;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public boolean isRepeatedWeekly() {
        return repeatedWeekly;
    }

    public void setRepeatedWeekly(boolean repeatedWeekly) {
        this.repeatedWeekly = repeatedWeekly;
    }

    public long getRepeatInDays() {
        return repeatInDays;
    }

    public void setRepeatInDays(long repeatInDays) {
        this.repeatInDays = repeatInDays;
    }
}
