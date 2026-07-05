package com.parko.domain.lib.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
public class Vehicle {

    private UUID userId;
    private String plate;
    private String marca;
    private String modelo;
    @Setter
    private boolean active;

    public Vehicle(UUID userId, String plate, String marca, String modelo) {
        if (userId == null) {
            throw new IllegalArgumentException("El userId no puede estar vacío");
        }
        this.userId = userId;

        if (plate == null) {
            throw new IllegalArgumentException("La patente no puede estar vacía");
        }
        this.plate = plate;

        if (marca == null) {
            throw new IllegalArgumentException("La marca no puede estar vacía");
        }
        this.marca = marca;

        if (modelo == null) {
            throw new IllegalArgumentException("El modelo no puede estar vacío");
        }
        this.modelo = modelo;

        this.active = true;
    }
}
