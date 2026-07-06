package com.parko.domain.lib.model;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class VehicleTest {

    @Test
    void createsActiveVehicleByDefault() {
        UUID userId = UUID.randomUUID();

        Vehicle vehicle = new Vehicle(userId, "AB123CD", "Toyota", "Corolla");

        assertThat(vehicle.getUserId()).isEqualTo(userId);
        assertThat(vehicle.getPlate()).isEqualTo("AB123CD");
        assertThat(vehicle.getBrand()).isEqualTo("Toyota");
        assertThat(vehicle.getModel()).isEqualTo("Corolla");
        assertThat(vehicle.isActive()).isTrue();
    }

    @Test
    void userIdNullThrows() {
        assertThatThrownBy(() -> new Vehicle(null, "AB123CD", "Toyota", "Corolla"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void plateNullThrows() {
        assertThatThrownBy(() -> new Vehicle(UUID.randomUUID(), null, "Toyota", "Corolla"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void brandNullThrows() {
        assertThatThrownBy(() -> new Vehicle(UUID.randomUUID(), "AB123CD", null, "Corolla"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void modelNullThrows() {
        assertThatThrownBy(() -> new Vehicle(UUID.randomUUID(), "AB123CD", "Toyota", null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void activeCanBeToggled() {
        Vehicle vehicle = new Vehicle(UUID.randomUUID(), "AB123CD", "Toyota", "Corolla");

        vehicle.setActive(false);

        assertThat(vehicle.isActive()).isFalse();
    }
}
