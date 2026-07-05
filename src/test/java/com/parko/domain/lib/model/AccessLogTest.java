package com.parko.domain.lib.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AccessLogTest {

    private final UUID id = UUID.randomUUID();
    private final UUID parkingSessionId = UUID.randomUUID();
    private final LocalDateTime occurredAt = LocalDateTime.now();

    @Test
    void createsAccessLogWithParkingSession() {
        AccessLog log = new AccessLog(
                id, parkingSessionId, AccessEventType.ENTRY, AccessMethod.PLATE,
                AccessResult.AUTHORIZED, "{}", occurredAt);

        assertThat(log.getParkingSessionId()).isEqualTo(parkingSessionId);
        assertThat(log.getEventType()).isEqualTo(AccessEventType.ENTRY);
        assertThat(log.getAccessMethod()).isEqualTo(AccessMethod.PLATE);
        assertThat(log.getResult()).isEqualTo(AccessResult.AUTHORIZED);
    }

    @Test
    void allowsNullParkingSessionForPreSessionDenial() {
        AccessLog log = new AccessLog(
                id, null, AccessEventType.ENTRY, AccessMethod.PLATE,
                AccessResult.DENIED, "{}", occurredAt);

        assertThat(log.getParkingSessionId()).isNull();
        assertThat(log.getResult()).isEqualTo(AccessResult.DENIED);
    }

    @Test
    void eventTypeNullThrows() {
        assertThatThrownBy(() -> new AccessLog(
                id, parkingSessionId, null, AccessMethod.PLATE, AccessResult.AUTHORIZED, "{}", occurredAt))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void accessMethodNullThrows() {
        assertThatThrownBy(() -> new AccessLog(
                id, parkingSessionId, AccessEventType.ENTRY, null, AccessResult.AUTHORIZED, "{}", occurredAt))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void resultNullThrows() {
        assertThatThrownBy(() -> new AccessLog(
                id, parkingSessionId, AccessEventType.ENTRY, AccessMethod.PLATE, null, "{}", occurredAt))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void rawPayloadNullThrows() {
        assertThatThrownBy(() -> new AccessLog(
                id, parkingSessionId, AccessEventType.ENTRY, AccessMethod.PLATE, AccessResult.AUTHORIZED, null, occurredAt))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void occurredAtNullThrows() {
        assertThatThrownBy(() -> new AccessLog(
                id, parkingSessionId, AccessEventType.ENTRY, AccessMethod.PLATE, AccessResult.AUTHORIZED, "{}", null))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
