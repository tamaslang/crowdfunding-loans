package org.talangsoft.crowdfunding.service;

import org.talangsoft.crowdfunding.model.CurrentOfferResult;
import org.talangsoft.crowdfunding.model.RequestAndOffers;

public interface LendingCalculationStrategy {
    CurrentOfferResult calculateCurrentOffer(RequestAndOffers requestAndOffers);
}
