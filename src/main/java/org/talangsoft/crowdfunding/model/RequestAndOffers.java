package org.talangsoft.crowdfunding.model;

import java.util.*;

public class RequestAndOffers {
    private LoanRequest request;

    private Set<LoanOffer> offers = new TreeSet<>();

    public RequestAndOffers(LoanRequest request) {
        this.request = request;
    }

    public void createOffer(LoanOffer offer) {
        this.offers.add(offer);
    }

    public LoanRequest getRequest() {
        return request;
    }

    public Set<LoanOffer> getOffers() {
        return offers;
    }
}
