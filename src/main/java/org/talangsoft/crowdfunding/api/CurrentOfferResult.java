package org.talangsoft.crowdfunding.api;

import java.math.BigDecimal;

public class CurrentOfferResult {
    private BigDecimal amount;
    private BigDecimal interestRate;

    public CurrentOfferResult(BigDecimal amount, BigDecimal interestRate) {
        this.amount = amount;
        this.interestRate = interestRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CurrentOfferResult that = (CurrentOfferResult) o;

        if (!amount.equals(that.amount)) return false;
        return interestRate.equals(that.interestRate);

    }

    @Override
    public int hashCode() {
        int result = amount.hashCode();
        result = 31 * result + interestRate.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "CurrentOffer{" +
                "amount=" + amount +
                ", interestRate=" + interestRate +
                '}';
    }
}
