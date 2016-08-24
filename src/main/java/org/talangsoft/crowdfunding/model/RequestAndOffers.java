package org.talangsoft.crowdfunding.model;

import java.util.*;

public class RequestAndOffers {
    private LoanRequest request;

    private List<LoanOffer> offers = new ArrayList<>();

    public RequestAndOffers(LoanRequest request) {
        this.request = request;
    }

    public void createOffer(LoanOffer offer) {
        this.offers.add(offer);
    }

    public LoanRequest getRequest() {
        return request;
    }

    public List<LoanOffer> getOffers() {
        return offers;
    }
}
