package com.parko.domain.lib.model;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AccessKeyTest {

    @Test
    void createsActiveAccessKeyByDefault() {
        UUID vehicleId = UUID.randomUUID();

        AccessKey accessKey = new AccessKey(vehicleId, "AK-0001");

        assertThat(accessKey.getVehicleId()).isEqualTo(vehicleId);
        assertThat(accessKey.getCode()).isEqualTo("AK-0001");
        assertThat(accessKey.isActive()).isTrue();
    }

    @Test
    void vehicleIdNullThrows() {
        assertThatThrownBy(() -> new AccessKey(null, "AK-0001"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void codeNullThrows() {
        assertThatThrownBy(() -> new AccessKey(UUID.randomUUID(), null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void codeBlankThrows() {
        assertThatThrownBy(() -> new AccessKey(UUID.randomUUID(), "   "))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void activeCanBeToggled() {
        AccessKey accessKey = new AccessKey(UUID.randomUUID(), "AK-0001");

        accessKey.setActive(false);

        assertThat(accessKey.isActive()).isFalse();
    }
}
