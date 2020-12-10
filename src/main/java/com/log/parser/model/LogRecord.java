package com.log.parser.model;

/**
 * POJO class which maps a row in given log file
 */
public class LogRecord {
    private String id;
    private long timestamp;
    private String state;
    private String type;
    private String host;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(final long timestamp) {
        this.timestamp = timestamp;
    }

    public String getState() {
        return state;
    }

    public void setState(final String state) {
        this.state = state;
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
}
