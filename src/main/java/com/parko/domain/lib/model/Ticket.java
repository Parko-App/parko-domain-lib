package com.parko.domain.lib.model;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Ticket {

    protected UUID parkingSessionId;
    protected String ticketNumber;
    protected String qrData;
    protected TicketStatus status;
    protected LocalDateTime issuedAt;
    protected LocalDateTime paidAt;

    public Ticket(UUID parkingSessionId, String ticketNumber, String qrData, LocalDateTime issuedAt) {
        if (parkingSessionId == null) {
            throw new IllegalArgumentException("El parkingSessionId no puede estar vacío");
        }
        this.parkingSessionId = parkingSessionId;

        if (ticketNumber == null) {
            throw new IllegalArgumentException("El ticketNumber no puede estar vacío");
        }
        this.ticketNumber = ticketNumber;

        if (qrData == null) {
            throw new IllegalArgumentException("El qrData no puede estar vacío");
        }
        this.qrData = qrData;

        if (issuedAt == null) {
            throw new IllegalArgumentException("El issuedAt no puede estar vacío");
        }
        this.issuedAt = issuedAt;

        this.status = TicketStatus.PENDING_PAYMENT;
    }

    public void markAsPaid(LocalDateTime paidAt) {
        if (status != TicketStatus.PENDING_PAYMENT) {
            throw new IllegalStateException("Solo se puede pagar un ticket en estado PENDING_PAYMENT");
        }
        if (paidAt == null) {
            throw new IllegalArgumentException("El paidAt no puede estar vacío");
        }
        this.status = TicketStatus.PAID;
        this.paidAt = paidAt;
    }

    public void markAsUsed() {
        if (status != TicketStatus.PAID) {
            throw new IllegalStateException("Solo se puede usar un ticket en estado PAID");
        }
        this.status = TicketStatus.USED;
    }

    public void cancel() {
        if (status == TicketStatus.USED) {
            throw new IllegalStateException("No se puede cancelar un ticket ya usado");
        }
        if (status == TicketStatus.CANCELLED) {
            throw new IllegalStateException("El ticket ya está cancelado");
        }
        this.status = TicketStatus.CANCELLED;
    }
}
