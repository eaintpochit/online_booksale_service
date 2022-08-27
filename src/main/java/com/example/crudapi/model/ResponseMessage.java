package com.example.crudapi.model;

public class ResponseMessage {
    private String statusMessage;
    private String creatd_date_time;
    private Object data;

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public String getCreatd_date_time() {
        return creatd_date_time;
    }

    public void setCreatd_date_time(String creatd_date_time) {
        this.creatd_date_time = creatd_date_time;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
