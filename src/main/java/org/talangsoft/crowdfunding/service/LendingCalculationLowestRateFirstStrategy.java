package org.talangsoft.crowdfunding.service;

import org.talangsoft.crowdfunding.model.CurrentOfferResult;
import org.talangsoft.crowdfunding.model.LoanOffer;
import org.talangsoft.crowdfunding.model.RequestAndOffers;

import java.math.BigDecimal;

public class LendingCalculationLowestRateFirstStrategy implements LendingCalculationStrategy {
    private CurrentOfferResult NO_OFFER = new CurrentOfferResult(BigDecimal.ZERO, BigDecimal.ZERO);
    @Override
    public CurrentOfferResult calculateCurrentOffer(RequestAndOffers requestAndOffers) {
        if (requestAndOffers.getOffers().isEmpty()) {
            return NO_OFFER;
        }
        BigDecimal remainderFromRequest = requestAndOffers.getRequest().getAmount();
        BigDecimal weightedInterest = BigDecimal.ZERO;

        for (LoanOffer offer : requestAndOffers.getOffers()) {
            if (remainderFromRequest.compareTo(BigDecimal.ZERO) > 0) {
                BigDecimal availableToTake = remainderFromRequest.min(offer.getAmount());
                remainderFromRequest = remainderFromRequest.subtract(availableToTake);
                weightedInterest = weightedInterest.add(availableToTake.multiply(offer.getInterestRate()));
            } else break;
        }
        BigDecimal amountSatisfied = requestAndOffers.getRequest().getAmount().subtract(remainderFromRequest);
        return new CurrentOfferResult(amountSatisfied, weightedInterest.divide(amountSatisfied));
    }


}
