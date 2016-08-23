package org.talangsoft.crowdfunding;

import java.math.BigDecimal;

public class CurrentOffer {
    private BigDecimal amount;
    private BigDecimal interestRate;

    public CurrentOffer(BigDecimal amount, BigDecimal interestRate) {
        this.amount = amount;
        this.interestRate = interestRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CurrentOffer that = (CurrentOffer) o;

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
