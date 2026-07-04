package com.parko.domain.lib.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BalanceAccountTest {

    @Test
    void createsAccountWithZeroBalance() {
        UUID userId = UUID.randomUUID();

        BalanceAccount account = new BalanceAccount(userId);

        assertThat(account.getUserId()).isEqualTo(userId);
        assertThat(account.getBalance()).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    void userIdNullThrows() {
        assertThatThrownBy(() -> new BalanceAccount(null))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
