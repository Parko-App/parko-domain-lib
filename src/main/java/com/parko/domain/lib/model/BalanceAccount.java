package com.parko.domain.lib.model;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
public class BalanceAccount {

    private UUID userId;
    private BigDecimal balance;

    public BalanceAccount(UUID userId) {
        if (userId == null) {
            throw new IllegalArgumentException("El userId no puede estar vacío");
        }
        this.userId = userId;
        this.balance = BigDecimal.ZERO;
    }
}
