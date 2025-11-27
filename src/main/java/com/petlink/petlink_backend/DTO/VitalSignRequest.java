package com.petlink.petlink_backend.DTO;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Payload enviado desde la pechera IoT (ESP32)")
public class VitalSignRequest {

    @Schema(description = "Id del dispositivo IoT")
    public String deviceId;

    @Schema(description = "timestamp generado en el ESP32")
    public String timestamp;

    @Schema(description = "Ritmo cardíaco en BPM")
    public int heartBpm;

    @Schema(description = "Estado del indicador (ESTABLE, ALERTA, CRITICO)")
    public String state;

    public Long collarId;
}