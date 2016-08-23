package org.talangsoft.crowdfunding;

import java.math.BigDecimal;
import java.util.UUID;

public class LoanOffer {
    private UUID identifier;
    private BigDecimal amount;
    private BigDecimal interestRate;

    public LoanOffer(BigDecimal amount, BigDecimal interestRate) {
        this.identifier = UUID.randomUUID();
        this.amount = amount;
        this.interestRate = interestRate;
    }

    public UUID getIdentifier() {
        return identifier;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }
}
