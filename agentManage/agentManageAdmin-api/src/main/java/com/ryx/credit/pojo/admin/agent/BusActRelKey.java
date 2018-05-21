package com.ryx.credit.pojo.admin.agent;

public class BusActRelKey {
    private String activId;

    private String busId;

    public String getActivId() {
        return activId;
    }

    public void setActivId(String activId) {
        this.activId = activId == null ? null : activId.trim();
    }

    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId == null ? null : busId.trim();
    }
}