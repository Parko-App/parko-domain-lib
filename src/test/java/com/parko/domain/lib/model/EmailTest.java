package com.parko.domain.lib.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EmailTest {

    @Test
    void createsEmailWithSubdomain() {
        Email email = new Email("12345@sistemas.frc.utn.edu.ar");

        assertThat(email.getValue()).isEqualTo("12345@sistemas.frc.utn.edu.ar");
        assertThat(email.getStudentId()).isEqualTo("12345");
        assertThat(email.getInstitutionalDomain()).isEqualTo(InstitutionalDomain.SISTEMAS);
    }

    @Test
    void createsEmailWithoutSubdomainResolvesToFrc() {
        Email email = new Email("12345@frc.utn.edu.ar");

        assertThat(email.getInstitutionalDomain()).isEqualTo(InstitutionalDomain.FRC);
    }

    @Test
    void nullValueThrows() {
        assertThatThrownBy(() -> new Email(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void invalidFormatThrows() {
        assertThatThrownBy(() -> new Email("not-an-email"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void studentIdOutOfRangeThrows() {
        assertThatThrownBy(() -> new Email("100000@sistemas.frc.utn.edu.ar"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void unknownInstitutionalDomainThrows() {
        assertThatThrownBy(() -> new Email("12345@bogus.frc.utn.edu.ar"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
