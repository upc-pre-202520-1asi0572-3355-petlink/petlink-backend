package com.petlink.petlink_backend.DTO;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Payload enviado desde la pechera IoT (ESP32)")
public class VitalSignRequest {

    private long collarId;
    private String timestamp;
    private int heartBpm;
    private String state;

    public long getCollarId() {
        return collarId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public int getHeartBpm() {
        return heartBpm;
    }

    public String getState() {
        return state;
    }
}