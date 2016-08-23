package org.talangsoft.crowdfunding.controller;

import com.google.common.base.Preconditions;
import org.talangsoft.crowdfunding.model.CurrentOfferResult;
import org.talangsoft.crowdfunding.model.LoanOffer;
import org.talangsoft.crowdfunding.model.LoanRequest;
import org.talangsoft.crowdfunding.repository.RequestAndOffersRepository;
import org.talangsoft.crowdfunding.service.LendingCalculationStrategy;

import java.math.BigDecimal;
import java.util.UUID;

public class CrowdFundingPlatformImpl implements CrowdFundingPlatform {

    private RequestAndOffersRepository repository;

    private LendingCalculationStrategy calculationStrategy;

    public CrowdFundingPlatformImpl(RequestAndOffersRepository repository, LendingCalculationStrategy calculationStrategy) {
        this.repository = repository;
        this.calculationStrategy = calculationStrategy;
    }

    @Override
    public UUID createLoanRequest(BigDecimal amount, Integer days) {
        Preconditions.checkArgument(days > 0, "Days should be greater than 0");
        Preconditions.checkArgument(amount.doubleValue() > 0, "The amount should be greater than 0");

        return repository.createLoanRequest(new LoanRequest(amount, days));
    }

    @Override
    public UUID createLoanOffer(UUID loanRequestId, BigDecimal amount, BigDecimal interestRate) {
        Preconditions.checkArgument(interestRate.doubleValue() > 0, "Interest Rate should be greater than 0%");
        Preconditions.checkArgument(amount.doubleValue() > 0, "The amount should be greater than 0");

        return repository.createOfferForRequest(loanRequestId, new LoanOffer(amount, interestRate));
    }

    @Override
    public CurrentOfferResult getCurrentOffer(UUID loanRequestId) {
        return calculationStrategy.calculateCurrentOffer(repository.findByLoanRequestId(loanRequestId));
    }

}
