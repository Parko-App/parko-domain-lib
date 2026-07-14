package com.parko.domain.lib.model;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserTest {

    private final Email email = new Email("12345@sistemas.frc.utn.edu.ar");

    @Test
    void createsUserWithMatchingStudentId() {
        UUID id = UUID.randomUUID();

        User user = new User(id, "12345", "Juan Perez", email);

        assertThat(user.getId()).isEqualTo(id);
        assertThat(user.getStudentId()).isEqualTo("12345");
        assertThat(user.getFullName()).isEqualTo("Juan Perez");
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.isActive()).isTrue();
        assertThat(user.getFirebaseUid()).isNull();
    }

    @Test
    void studentIdNullThrows() {
        assertThatThrownBy(() -> new User(UUID.randomUUID(), null, "Juan Perez", email))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void emailNullThrows() {
        assertThatThrownBy(() -> new User(UUID.randomUUID(), "12345", "Juan Perez", null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void studentIdMismatchWithEmailThrows() {
        assertThatThrownBy(() -> new User(UUID.randomUUID(), "99999", "Juan Perez", email))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void fullNameNullThrows() {
        assertThatThrownBy(() -> new User(UUID.randomUUID(), "12345", null, email))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
