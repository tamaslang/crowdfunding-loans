package org.talangsoft.crowdfunding.service;

import org.talangsoft.crowdfunding.model.CurrentOfferResult;
import org.talangsoft.crowdfunding.model.LoanOffer;
import org.talangsoft.crowdfunding.model.RequestAndOffers;

import java.math.BigDecimal;

public class LendingCalculationLowestRateFirstStrategy implements LendingCalculationStrategy {
    @Override
    public CurrentOfferResult calculateCurrentOffer(RequestAndOffers requestAndOffers) {
        if (requestAndOffers.getOffers().isEmpty()) {
            return new CurrentOfferResult(BigDecimal.ZERO, BigDecimal.ZERO);
        }
        BigDecimal remainderFromRequest = requestAndOffers.getRequest().getAmount();
        BigDecimal weightedInterest = BigDecimal.ZERO;

        for (LoanOffer offer : requestAndOffers.getOffers()) {
            if (remainderFromRequest.doubleValue() > 0) {
                // take as much from the offer as needed
                BigDecimal take = offer.getAmount().doubleValue() < remainderFromRequest.doubleValue() ?
                        offer.getAmount() : remainderFromRequest;
                remainderFromRequest = remainderFromRequest.subtract(take);
                weightedInterest = weightedInterest.add(take.multiply(offer.getInterestRate()));
            } else break;
        }
        BigDecimal amountSatisfied = requestAndOffers.getRequest().getAmount().subtract(remainderFromRequest);
        return new CurrentOfferResult(amountSatisfied, weightedInterest.divide(amountSatisfied));
    }
}
