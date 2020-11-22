package com.equator.learn.dynamic.base;

import lombok.SneakyThrows;

public class LogData {
    /**
     * 日志来源
     */
    private String source;
    /**
     * 日志生产时间
     */
    private long logTime;
    /**
     * 日志内容
     */
    private String data;
    /**
     * 其它一些内容
     */
    private int value;

    public LogData() {
    }

    public LogData(String source, long logTime, String data, int value) {
        this.source = source;
        this.logTime = logTime;
        this.data = data;
        this.value = value;
    }

    public long getLogTime() {
        return logTime;
    }

    public void setLogTime(long logTime) {
        this.logTime = logTime;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
