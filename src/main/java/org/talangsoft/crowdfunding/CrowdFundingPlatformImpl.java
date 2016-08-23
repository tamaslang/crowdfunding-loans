package org.talangsoft.crowdfunding;

import org.joda.money.Money;

import java.math.BigDecimal;
import java.util.UUID;

public class CrowdFundingPlatformImpl implements CrowdFundingPlatform {

    @Override
    public UUID createLoanRequest(Money amount, Integer days) {
        return UUID.randomUUID();
    }

    @Override
    public UUID createLoanOffer(UUID loanRequestIdentifier, Money amount, BigDecimal interestRate) {
        return UUID.randomUUID();
    }

    @Override
    public CombinedOffer getCurrentOffer(UUID loanRequestId) {
        return new CombinedOffer(Money.parse("GBP 600"), BigDecimal.valueOf(8.0));
    }

}
