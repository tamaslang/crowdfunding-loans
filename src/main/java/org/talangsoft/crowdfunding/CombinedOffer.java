package org.talangsoft.crowdfunding;

import org.joda.money.Money;

import java.math.BigDecimal;

public class CombinedOffer {
    private Money amount;
    private BigDecimal interestRate;

    public CombinedOffer(Money amount, BigDecimal interestRate) {
        this.amount = amount;
        this.interestRate = interestRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CombinedOffer that = (CombinedOffer) o;

        if (!amount.equals(that.amount)) return false;
        return interestRate.equals(that.interestRate);

    }

    @Override
    public int hashCode() {
        int result = amount.hashCode();
        result = 31 * result + interestRate.hashCode();
        return result;
    }
}
