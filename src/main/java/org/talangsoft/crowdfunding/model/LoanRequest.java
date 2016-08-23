package org.talangsoft.crowdfunding.model;

import java.math.BigDecimal;
import java.util.UUID;

public class LoanRequest {
    private UUID identifier;
    private BigDecimal amount;
    private int durationInDays;

    public LoanRequest(BigDecimal amount, int durationInDays) {
        this.identifier = UUID.randomUUID();
        this.amount = amount;
        this.durationInDays = durationInDays;
    }

    public UUID getIdentifier() {
        return identifier;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public int getDurationInDays() {
        return durationInDays;
    }
}
