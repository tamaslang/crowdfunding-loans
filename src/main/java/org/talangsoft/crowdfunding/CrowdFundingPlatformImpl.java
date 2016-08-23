package org.talangsoft.crowdfunding;

import com.google.common.base.Preconditions;
import org.joda.money.Money;

import java.math.BigDecimal;
import java.util.UUID;

public class CrowdFundingPlatformImpl implements CrowdFundingPlatform {

    @Override
    public UUID createLoanRequest(Money amount, Integer days) {
        Preconditions.checkArgument(days > 0, "Days should be greater than 0");
        return UUID.randomUUID();
    }

    @Override
    public UUID createLoanOffer(UUID loanRequestIdentifier, Money amount, BigDecimal interestRate) {
        Preconditions.checkArgument(interestRate.doubleValue() > 0, "Interest Rate should be greater than 0%");
        return UUID.randomUUID();
    }

    @Override
    public CombinedOffer getCurrentOffer(UUID loanRequestId) {
        return new CombinedOffer(Money.parse("GBP 600"), BigDecimal.valueOf(8.0));
    }

}
