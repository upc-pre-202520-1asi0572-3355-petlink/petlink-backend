package com.petlink.petlink_backend.DTO;

public class VitalSignPayload {
    public String deviceId;
    public long timestamp;
    public int heartBpm;
    public String state;

    @Override
    public String toString() {
        return "VitalSignPayload{" +
                "deviceId='" + deviceId + '\'' +
                ", timestamp=" + timestamp +
                ", heartBpm=" + heartBpm +
                ", state='" + state + '\'' +
                '}';
    }
}