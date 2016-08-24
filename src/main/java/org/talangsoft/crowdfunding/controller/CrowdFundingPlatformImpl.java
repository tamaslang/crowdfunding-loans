package org.talangsoft.crowdfunding.controller;

import com.google.common.base.Preconditions;
import org.talangsoft.crowdfunding.model.CurrentOfferResult;
import org.talangsoft.crowdfunding.model.LoanOffer;
import org.talangsoft.crowdfunding.model.LoanRequest;
import org.talangsoft.crowdfunding.model.RequestAndOffers;
import org.talangsoft.crowdfunding.repository.RequestAndOffersRepository;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.UUID;

public class CrowdFundingPlatformImpl implements CrowdFundingPlatform {

    private RequestAndOffersRepository repository;

    private CurrentOfferResult NO_OFFER = new CurrentOfferResult(BigDecimal.ZERO, BigDecimal.ZERO);

    public CrowdFundingPlatformImpl(RequestAndOffersRepository repository) {
        this.repository = repository;
    }

    @Override
    public UUID createLoanRequest(BigDecimal amount, Integer days) {
        Preconditions.checkArgument(days > 0, "Days should be greater than 0");
        Preconditions.checkArgument(amount.compareTo(BigDecimal.ZERO) > 0, "The amount should be greater than 0");

        return repository.createLoanRequest(new LoanRequest(amount, days));
    }

    @Override
    public UUID createLoanOffer(UUID loanRequestId, BigDecimal amount, BigDecimal interestRate) {
        Preconditions.checkArgument(amount.compareTo(BigDecimal.ZERO) > 0, "Interest Rate should be greater than 0%");
        Preconditions.checkArgument(interestRate.compareTo(BigDecimal.ZERO) > 0, "The amount should be greater than 0");

        return repository.createOfferForRequest(loanRequestId, new LoanOffer(amount, interestRate));
    }

    @Override
    public CurrentOfferResult getCurrentOffer(UUID loanRequestId) {
        return calculateCurrentOffer(loanRequestId);
    }

    protected CurrentOfferResult calculateCurrentOffer(UUID loanRequestId) {
        RequestAndOffers requestAndOffers = repository.findByLoanRequestId(loanRequestId);
        if (requestAndOffers.getOffers().isEmpty()) {
            return NO_OFFER;
        }

        TreeSet<LoanOffer> sortedOffers = new TreeSet<>();
        sortedOffers.addAll(requestAndOffers.getOffers());

        BigDecimal remainderFromRequest = requestAndOffers.getRequest().getAmount();
        BigDecimal weightedInterest = BigDecimal.ZERO;

        Iterator<LoanOffer> offerIt = sortedOffers.iterator();
        while (offerIt.hasNext() && remainderFromRequest.compareTo(BigDecimal.ZERO) > 0) {
            LoanOffer offer = offerIt.next();
            BigDecimal availableToTake = remainderFromRequest.min(offer.getAmount());
            remainderFromRequest = remainderFromRequest.subtract(availableToTake);
            weightedInterest = weightedInterest.add(availableToTake.multiply(offer.getInterestRate()));
        }
        BigDecimal amountSatisfied = requestAndOffers.getRequest().getAmount().subtract(remainderFromRequest);
        return new CurrentOfferResult(amountSatisfied, weightedInterest.divide(amountSatisfied));
    }


}
