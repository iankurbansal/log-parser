package com.log.parser.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Pojo class which maps the Event table in HSQL database
 */
@Entity
public class Event {
    @Id
    @GeneratedValue
    private long id;
    @Column(name = "eventId", unique = true)
    private String eventId;
    private long duration;
    private String type;
    private String host;
    private boolean alert;

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(final String eventId) {
        this.eventId = eventId;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(final long duration) {
        this.duration = duration;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getHost() {
        return host;
    }

    public void setHost(final String host) {
        this.host = host;
    }

    public boolean isAlert() {
        return alert;
    }

    public void setAlert(final boolean alert) {
        this.alert = alert;
    }

    @Override public String toString() {
        return "Event{" +
            "id=" + id +
            ", eventId='" + eventId + '\'' +
            ", duration=" + duration +
            ", type='" + type + '\'' +
            ", host='" + host + '\'' +
            ", alert=" + alert +
            '}';
    }

    @Override public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Event)) {
            return false;
        }
        final Event event = (Event) o;
        return id == event.id &&
            duration == event.duration &&
            alert == event.alert &&
            Objects.equals(eventId, event.eventId) &&
            Objects.equals(type, event.type) &&
            Objects.equals(host, event.host);
    }

    @Override public int hashCode() {
        return Objects.hash(id, eventId, duration, type, host, alert);
    }
}
