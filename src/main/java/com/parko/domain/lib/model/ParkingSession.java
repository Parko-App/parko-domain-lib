package com.parko.domain.lib.model;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class ParkingSession {

    protected UUID id;
    protected UUID vehicleId;
    protected String plateSnapshot;
    protected SessionType sessionType;
    protected SessionStatus status;
    protected LocalDateTime entryAt;
    protected LocalDateTime exitAt;

    private ParkingSession(UUID id, UUID vehicleId, String plateSnapshot, SessionType sessionType, LocalDateTime entryAt) {
        this.id = id;
        this.vehicleId = vehicleId;
        this.plateSnapshot = plateSnapshot;
        this.sessionType = sessionType;

        if (entryAt == null) {
            throw new IllegalArgumentException("El entryAt no puede estar vacío");
        }
        this.entryAt = entryAt;

        this.status = SessionStatus.ACTIVE;
    }

    public static ParkingSession forRegisteredUser(UUID id, UUID vehicleId, String plateSnapshot, LocalDateTime entryAt) {
        if (vehicleId == null) {
            throw new IllegalArgumentException("Una sesión de usuario registrado requiere vehicleId");
        }
        if (plateSnapshot == null) {
            throw new IllegalArgumentException("Una sesión de usuario registrado requiere plateSnapshot");
        }
        return new ParkingSession(id, vehicleId, plateSnapshot, SessionType.REGISTERED, entryAt);
    }

    public static ParkingSession forVisitor(UUID id, LocalDateTime entryAt) {
        return new ParkingSession(id, null, null, SessionType.VISITOR, entryAt);
    }

    public void complete(LocalDateTime exitAt) {
        if (status != SessionStatus.ACTIVE) {
            throw new IllegalStateException("Solo se puede completar una sesión ACTIVE");
        }
        if (exitAt == null) {
            throw new IllegalArgumentException("El exitAt no puede estar vacío");
        }
        this.status = SessionStatus.COMPLETED;
        this.exitAt = exitAt;
    }

    public void cancel() {
        if (status != SessionStatus.ACTIVE) {
            throw new IllegalStateException("Solo se puede cancelar una sesión ACTIVE");
        }
        this.status = SessionStatus.CANCELLED;
    }
}
