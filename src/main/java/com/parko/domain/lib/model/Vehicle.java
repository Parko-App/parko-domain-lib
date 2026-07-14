package com.parko.domain.lib.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
import java.util.regex.Pattern;

@Getter
public class Vehicle {

    private static final Pattern PLATE_PATTERN = Pattern.compile(
            "^([A-Z]{3}[0-9]{3}|[A-Z]{2}[0-9]{3}[A-Z]{2})$"
    );
    private static final int MAX_BRAND_LENGTH = 15;
    private static final int MAX_MODEL_LENGTH = 15;

    private UUID userId;
    private String plate;
    private String brand;
    private String model;
    @Setter
    private boolean active;

    public Vehicle(UUID userId, String plate, String brand, String model) {
        if (userId == null) {
            throw new IllegalArgumentException("El userId no puede estar vacío");
        }
        this.userId = userId;

        if (plate == null) {
            throw new IllegalArgumentException("La patente no puede estar vacía");
        }
        String normalizedPlate = plate.toUpperCase();
        if (!PLATE_PATTERN.matcher(normalizedPlate).matches()) {
            throw new IllegalArgumentException(
                    "Patente inválida, formato esperado ABC123 (viejo) o AB123CD (Mercosur): " + plate
            );
        }
        this.plate = normalizedPlate;

        if (brand == null) {
            throw new IllegalArgumentException("La marca no puede estar vacía");
        }
        if (brand.length() > MAX_BRAND_LENGTH) {
            throw new IllegalArgumentException("La marca no puede superar los " + MAX_BRAND_LENGTH + " caracteres");
        }
        this.brand = brand;

        if (model == null) {
            throw new IllegalArgumentException("El modelo no puede estar vacío");
        }
        if (model.length() > MAX_MODEL_LENGTH) {
            throw new IllegalArgumentException("El modelo no puede superar los " + MAX_MODEL_LENGTH + " caracteres");
        }
        this.model = model;

        this.active = true;
    }
}
