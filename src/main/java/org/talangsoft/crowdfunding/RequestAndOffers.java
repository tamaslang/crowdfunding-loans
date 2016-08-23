package org.talangsoft.crowdfunding;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RequestAndOffers {
    private LoanRequest request;

    private List<LoanOffer> offers = new ArrayList<>();

    public RequestAndOffers(LoanRequest request) {
        this.request = request;
    }

    public void createOffer(LoanOffer offer) {
        this.offers.add(offer);
    }

    public CurrentOffer calculateCurrentOffer() {
        if (offers.isEmpty()) {
            return new CurrentOffer(BigDecimal.ZERO, BigDecimal.ZERO);
        }
        BigDecimal remainderFromRequest = request.getAmount();
        BigDecimal weightedInterest = BigDecimal.ZERO;

        offers.sort((o1,o2) -> o1.getInterestRate().compareTo(o2.getInterestRate()));

        for (LoanOffer offer: offers) {
            if(remainderFromRequest.doubleValue() > 0) {
                // take as much from the offer as needed
                BigDecimal take = offer.getAmount().doubleValue() < remainderFromRequest.doubleValue()?
                        offer.getAmount() : remainderFromRequest;
                remainderFromRequest = remainderFromRequest.subtract(take);
                weightedInterest = weightedInterest.add(take.multiply(offer.getInterestRate()));
            } else {
                // don't take any, but break
            }
        }
        BigDecimal amountCompleted = request.getAmount().subtract(remainderFromRequest);
        return new CurrentOffer(amountCompleted, weightedInterest.divide(amountCompleted));
    }

}
