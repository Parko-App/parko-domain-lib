package com.parko.domain.lib.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
public class Transaction {

    protected UUID id;
    protected UUID balanceAccountId;
    protected UUID parkingSessionId;
    protected TransactionType type;
    protected BigDecimal amount;
    @Setter
    protected TransactionStatus status;
    protected String paymentProvider;
    protected String externalRef;

    private Transaction(UUID id, UUID balanceAccountId, UUID parkingSessionId, TransactionType type,
                         BigDecimal amount, TransactionStatus status, String paymentProvider, String externalRef) {
        this.id = id;
        this.balanceAccountId = balanceAccountId;
        this.parkingSessionId = parkingSessionId;

        if (type == null) {
            throw new IllegalArgumentException("El tipo de transacción no puede estar vacío");
        }
        this.type = type;

        if (amount == null) {
            throw new IllegalArgumentException("El monto no puede estar vacío");
        }
        this.amount = amount;

        if (status == null) {
            throw new IllegalArgumentException("El estado no puede estar vacío");
        }
        this.status = status;

        if (paymentProvider == null) {
            throw new IllegalArgumentException("El proveedor de pago no puede estar vacío");
        }
        this.paymentProvider = paymentProvider;

        this.externalRef = externalRef;
    }

    public static Transaction topUp(UUID id, UUID balanceAccountId, BigDecimal amount,
                                     TransactionStatus status, String paymentProvider, String externalRef) {
        if (balanceAccountId == null) {
            throw new IllegalArgumentException("Un TOPUP requiere balanceAccountId");
        }
        return new Transaction(id, balanceAccountId, null, TransactionType.TOPUP,
                amount, status, paymentProvider, externalRef);
    }

    public static Transaction charge(UUID id, UUID balanceAccountId, UUID parkingSessionId, BigDecimal amount,
                                      TransactionStatus status, String paymentProvider, String externalRef) {
        if (parkingSessionId == null) {
            throw new IllegalArgumentException("Un CHARGE requiere parkingSessionId");
        }
        return new Transaction(id, balanceAccountId, parkingSessionId, TransactionType.CHARGE,
                amount, status, paymentProvider, externalRef);
    }

    public static Transaction refund(UUID id, UUID balanceAccountId, UUID parkingSessionId, BigDecimal amount,
                                      TransactionStatus status, String paymentProvider, String externalRef) {
        if (parkingSessionId == null) {
            throw new IllegalArgumentException("Un REFUND requiere parkingSessionId");
        }
        return new Transaction(id, balanceAccountId, parkingSessionId, TransactionType.REFUND,
                amount, status, paymentProvider, externalRef);
    }
}
