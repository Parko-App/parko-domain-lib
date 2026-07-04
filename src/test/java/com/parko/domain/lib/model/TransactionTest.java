package com.parko.domain.lib.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TransactionTest {

    private final UUID id = UUID.randomUUID();
    private final UUID balanceAccountId = UUID.randomUUID();
    private final UUID parkingSessionId = UUID.randomUUID();
    private final BigDecimal amount = BigDecimal.TEN;

    @Test
    void topUpHasNoParkingSession() {
        Transaction transaction = Transaction.topUp(
                id, balanceAccountId, amount, TransactionStatus.PENDING, "MERCADO_PAGO", "ext-ref");

        assertThat(transaction.getType()).isEqualTo(TransactionType.TOPUP);
        assertThat(transaction.getParkingSessionId()).isNull();
        assertThat(transaction.getBalanceAccountId()).isEqualTo(balanceAccountId);
    }

    @Test
    void topUpWithoutBalanceAccountThrows() {
        assertThatThrownBy(() -> Transaction.topUp(
                id, null, amount, TransactionStatus.PENDING, "MERCADO_PAGO", "ext-ref"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void chargeRequiresParkingSession() {
        Transaction transaction = Transaction.charge(
                id, balanceAccountId, parkingSessionId, amount, TransactionStatus.COMPLETED, "INTERNAL_BALANCE", null);

        assertThat(transaction.getType()).isEqualTo(TransactionType.CHARGE);
        assertThat(transaction.getParkingSessionId()).isEqualTo(parkingSessionId);
    }

    @Test
    void chargeWithoutParkingSessionThrows() {
        assertThatThrownBy(() -> Transaction.charge(
                id, balanceAccountId, null, amount, TransactionStatus.COMPLETED, "INTERNAL_BALANCE", null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void refundRequiresParkingSession() {
        Transaction transaction = Transaction.refund(
                id, balanceAccountId, parkingSessionId, amount, TransactionStatus.COMPLETED, "CASH", null);

        assertThat(transaction.getType()).isEqualTo(TransactionType.REFUND);
    }

    @Test
    void refundWithoutParkingSessionThrows() {
        assertThatThrownBy(() -> Transaction.refund(
                id, balanceAccountId, null, amount, TransactionStatus.COMPLETED, "CASH", null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void amountNullThrows() {
        assertThatThrownBy(() -> Transaction.charge(
                id, balanceAccountId, parkingSessionId, null, TransactionStatus.COMPLETED, "INTERNAL_BALANCE", null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void statusNullThrows() {
        assertThatThrownBy(() -> Transaction.charge(
                id, balanceAccountId, parkingSessionId, amount, null, "INTERNAL_BALANCE", null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void paymentProviderNullThrows() {
        assertThatThrownBy(() -> Transaction.charge(
                id, balanceAccountId, parkingSessionId, amount, TransactionStatus.COMPLETED, null, null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void statusCanBeUpdated() {
        Transaction transaction = Transaction.topUp(
                id, balanceAccountId, amount, TransactionStatus.PENDING, "MERCADO_PAGO", "ext-ref");

        transaction.setStatus(TransactionStatus.COMPLETED);

        assertThat(transaction.getStatus()).isEqualTo(TransactionStatus.COMPLETED);
    }
}
