package org.talangsoft.crowdfunding;

import org.joda.money.Money;

import java.math.BigDecimal;
import java.util.UUID;

public interface CrowdFundingPlatform {
    UUID createLoanRequest(Money amount, Integer days);

    UUID createLoanOffer(UUID loanRequestIdentifier, Money amount, BigDecimal interestRate);

    CombinedOffer getCurrentOffer(UUID loanRequestId);
}
