package com.parko.domain.lib.model;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class AccessLog {

    private UUID id;
    private UUID parkingSessionId;
    private AccessEventType eventType;
    private AccessMethod accessMethod;
    private AccessResult result;
    private String rawPayload;
    private LocalDateTime occurredAt;

    public AccessLog(UUID id, UUID parkingSessionId, AccessEventType eventType, AccessMethod accessMethod,
                      AccessResult result, String rawPayload, LocalDateTime occurredAt) {
        this.id = id;
        this.parkingSessionId = parkingSessionId;

        if (eventType == null) {
            throw new IllegalArgumentException("El eventType no puede estar vacío");
        }
        this.eventType = eventType;

        if (accessMethod == null) {
            throw new IllegalArgumentException("El accessMethod no puede estar vacío");
        }
        this.accessMethod = accessMethod;

        if (result == null) {
            throw new IllegalArgumentException("El result no puede estar vacío");
        }
        this.result = result;

        if (rawPayload == null) {
            throw new IllegalArgumentException("El rawPayload no puede estar vacío");
        }
        this.rawPayload = rawPayload;

        if (occurredAt == null) {
            throw new IllegalArgumentException("El occurredAt no puede estar vacío");
        }
        this.occurredAt = occurredAt;
    }
}
