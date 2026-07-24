package com.parko.domain.lib.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
public class AccessKey {

    private UUID vehicleId;
    private String code;
    @Setter
    private boolean active;

    public AccessKey(UUID vehicleId, String code) {
        if (vehicleId == null) {
            throw new IllegalArgumentException("El vehicleId no puede estar vacío");
        }
        this.vehicleId = vehicleId;

        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("El code no puede estar vacío");
        }
        this.code = code;

        this.active = true;
    }
}
