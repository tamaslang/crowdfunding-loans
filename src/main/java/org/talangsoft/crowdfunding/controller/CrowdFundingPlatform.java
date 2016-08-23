package org.talangsoft.crowdfunding.controller;

import org.talangsoft.crowdfunding.model.CurrentOfferResult;

import java.math.BigDecimal;
import java.util.UUID;

public interface CrowdFundingPlatform {
    /**
     * Create a loan request with the given amount and days
     *
     * @param amount the amount of the loan
     * @param days   the number of days the loan is for
     * @return the identifier of the loan request
     * @throws IllegalArgumentException if the amount is smaller
     *                                  or equal to zero or the days are smaller or equal to zero
     */
    UUID createLoanRequest(BigDecimal amount, Integer days);

    /**
     * Create a loan offer for a specific loan request
     *
     * @param loanRequestIdentifier the identifier of the loan request
     * @param amount                the amount of the offer
     * @param interestRate          the interest rate
     * @return the identifier of the loan offer
     * @throws IllegalArgumentException                                      if the amount is smaller
     *                                                                       or equal to zero or the interestRate is smaller or equal to zero
     * @throws org.talangsoft.crowdfunding.repository.UnknownLoanRequestIdEx if the loan request with id does not exist
     */
    UUID createLoanOffer(UUID loanRequestIdentifier, BigDecimal amount, BigDecimal interestRate);

    /**
     * Get the current offer for the loan request
     *
     * @param loanRequestId the identifier of the loan request
     *
     * @return the current offer result
     */
    CurrentOfferResult getCurrentOffer(UUID loanRequestId);
}
