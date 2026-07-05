package com.parko.domain.lib.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ParkingSessionTest {

    private final UUID id = UUID.randomUUID();
    private final UUID vehicleId = UUID.randomUUID();
    private final LocalDateTime entryAt = LocalDateTime.now();

    @Test
    void registeredUserSessionRequiresVehicleAndPlate() {
        ParkingSession session = ParkingSession.forRegisteredUser(id, vehicleId, "AB123CD", entryAt);

        assertThat(session.getSessionType()).isEqualTo(SessionType.REGISTERED);
        assertThat(session.getVehicleId()).isEqualTo(vehicleId);
        assertThat(session.getPlateSnapshot()).isEqualTo("AB123CD");
        assertThat(session.getStatus()).isEqualTo(SessionStatus.ACTIVE);
    }

    @Test
    void registeredUserSessionWithoutVehicleThrows() {
        assertThatThrownBy(() -> ParkingSession.forRegisteredUser(id, null, "AB123CD", entryAt))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void registeredUserSessionWithoutPlateThrows() {
        assertThatThrownBy(() -> ParkingSession.forRegisteredUser(id, vehicleId, null, entryAt))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void visitorSessionHasNoVehicleOrPlate() {
        ParkingSession session = ParkingSession.forVisitor(id, entryAt);

        assertThat(session.getSessionType()).isEqualTo(SessionType.VISITOR);
        assertThat(session.getVehicleId()).isNull();
        assertThat(session.getPlateSnapshot()).isNull();
        assertThat(session.getStatus()).isEqualTo(SessionStatus.ACTIVE);
    }

    @Test
    void entryAtNullThrows() {
        assertThatThrownBy(() -> ParkingSession.forVisitor(id, null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void completeTransitionsToCompletedAndSetsExitAt() {
        ParkingSession session = ParkingSession.forVisitor(id, entryAt);
        LocalDateTime exitAt = entryAt.plusHours(1);

        session.complete(exitAt);

        assertThat(session.getStatus()).isEqualTo(SessionStatus.COMPLETED);
        assertThat(session.getExitAt()).isEqualTo(exitAt);
    }

    @Test
    void completeWithoutExitAtThrows() {
        ParkingSession session = ParkingSession.forVisitor(id, entryAt);

        assertThatThrownBy(() -> session.complete(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void completeWhenNotActiveThrows() {
        ParkingSession session = ParkingSession.forVisitor(id, entryAt);
        session.complete(entryAt.plusHours(1));

        assertThatThrownBy(() -> session.complete(entryAt.plusHours(2)))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void cancelTransitionsToCancelled() {
        ParkingSession session = ParkingSession.forVisitor(id, entryAt);

        session.cancel();

        assertThat(session.getStatus()).isEqualTo(SessionStatus.CANCELLED);
    }

    @Test
    void cancelWhenNotActiveThrows() {
        ParkingSession session = ParkingSession.forVisitor(id, entryAt);
        session.cancel();

        assertThatThrownBy(session::cancel)
                .isInstanceOf(IllegalStateException.class);
    }
}
