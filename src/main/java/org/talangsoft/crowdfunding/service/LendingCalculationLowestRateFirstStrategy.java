package org.talangsoft.crowdfunding.service;

import org.talangsoft.crowdfunding.model.CurrentOfferResult;
import org.talangsoft.crowdfunding.model.LoanOffer;
import org.talangsoft.crowdfunding.model.RequestAndOffers;

import java.math.BigDecimal;
import java.util.Iterator;

public class LendingCalculationLowestRateFirstStrategy implements LendingCalculationStrategy {
    private CurrentOfferResult NO_OFFER = new CurrentOfferResult(BigDecimal.ZERO, BigDecimal.ZERO);

    @Override
    public CurrentOfferResult calculateCurrentOffer(RequestAndOffers requestAndOffers) {
        if (requestAndOffers.getOffers().isEmpty()) {
            return NO_OFFER;
        }
        BigDecimal remainderFromRequest = requestAndOffers.getRequest().getAmount();
        BigDecimal weightedInterest = BigDecimal.ZERO;

        Iterator<LoanOffer> offerIt = requestAndOffers.getOffers().iterator();
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
