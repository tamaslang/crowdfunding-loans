package org.talangsoft.crowdfunding.controller;

import org.talangsoft.crowdfunding.model.CurrentOfferResult;

import java.math.BigDecimal;
import java.util.UUID;

public interface CrowdFundingPlatform {
    UUID createLoanRequest(BigDecimal amount, Integer days);

    UUID createLoanOffer(UUID loanRequestIdentifier, BigDecimal amount, BigDecimal interestRate);

    CurrentOfferResult getCurrentOffer(UUID loanRequestId);
}
