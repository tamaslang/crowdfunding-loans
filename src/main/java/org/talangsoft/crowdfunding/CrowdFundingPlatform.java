package org.talangsoft.crowdfunding;

import java.math.BigDecimal;
import java.util.UUID;

public interface CrowdFundingPlatform {
    UUID createLoanRequest(BigDecimal amount, Integer days);

    UUID createLoanOffer(UUID loanRequestIdentifier, BigDecimal amount, BigDecimal interestRate);

    CurrentOffer getCurrentOffer(UUID loanRequestId);
}
