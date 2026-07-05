package com.parko.domain.lib.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TicketTest {

    private final UUID parkingSessionId = UUID.randomUUID();
    private final LocalDateTime issuedAt = LocalDateTime.now();

    private Ticket newTicket() {
        return new Ticket(parkingSessionId, "T-0001", "qr-payload", issuedAt);
    }

    @Test
    void createsTicketAsPendingPayment() {
        Ticket ticket = newTicket();

        assertThat(ticket.getStatus()).isEqualTo(TicketStatus.PENDING_PAYMENT);
        assertThat(ticket.getPaidAt()).isNull();
    }

    @Test
    void parkingSessionIdNullThrows() {
        assertThatThrownBy(() -> new Ticket(null, "T-0001", "qr-payload", issuedAt))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void ticketNumberNullThrows() {
        assertThatThrownBy(() -> new Ticket(parkingSessionId, null, "qr-payload", issuedAt))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void qrDataNullThrows() {
        assertThatThrownBy(() -> new Ticket(parkingSessionId, "T-0001", null, issuedAt))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void issuedAtNullThrows() {
        assertThatThrownBy(() -> new Ticket(parkingSessionId, "T-0001", "qr-payload", null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void markAsPaidTransitionsFromPendingPayment() {
        Ticket ticket = newTicket();
        LocalDateTime paidAt = issuedAt.plusMinutes(5);

        ticket.markAsPaid(paidAt);

        assertThat(ticket.getStatus()).isEqualTo(TicketStatus.PAID);
        assertThat(ticket.getPaidAt()).isEqualTo(paidAt);
    }

    @Test
    void markAsPaidWithNullThrows() {
        Ticket ticket = newTicket();

        assertThatThrownBy(() -> ticket.markAsPaid(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void markAsPaidWhenNotPendingThrows() {
        Ticket ticket = newTicket();
        ticket.markAsPaid(issuedAt.plusMinutes(5));

        assertThatThrownBy(() -> ticket.markAsPaid(issuedAt.plusMinutes(10)))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void markAsUsedTransitionsFromPaid() {
        Ticket ticket = newTicket();
        ticket.markAsPaid(issuedAt.plusMinutes(5));

        ticket.markAsUsed();

        assertThat(ticket.getStatus()).isEqualTo(TicketStatus.USED);
    }

    @Test
    void markAsUsedWhenNotPaidThrows() {
        Ticket ticket = newTicket();

        assertThatThrownBy(ticket::markAsUsed)
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void cancelFromPendingPaymentSucceeds() {
        Ticket ticket = newTicket();

        ticket.cancel();

        assertThat(ticket.getStatus()).isEqualTo(TicketStatus.CANCELLED);
    }

    @Test
    void cancelFromPaidSucceeds() {
        Ticket ticket = newTicket();
        ticket.markAsPaid(issuedAt.plusMinutes(5));

        ticket.cancel();

        assertThat(ticket.getStatus()).isEqualTo(TicketStatus.CANCELLED);
    }

    @Test
    void cancelFromUsedThrows() {
        Ticket ticket = newTicket();
        ticket.markAsPaid(issuedAt.plusMinutes(5));
        ticket.markAsUsed();

        assertThatThrownBy(ticket::cancel)
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void cancelTwiceThrows() {
        Ticket ticket = newTicket();
        ticket.cancel();

        assertThatThrownBy(ticket::cancel)
                .isInstanceOf(IllegalStateException.class);
    }
}
