package org.talangsoft.crowdfunding.repository;

import org.talangsoft.crowdfunding.model.LoanOffer;
import org.talangsoft.crowdfunding.model.LoanRequest;
import org.talangsoft.crowdfunding.model.RequestAndOffers;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InMemoryRequestAndOffersRepository implements RequestAndOffersRepository {
    private Map<UUID, RequestAndOffers> requestsAndOffers = new HashMap<>();

    @Override
    public RequestAndOffers findByLoanRequestId(UUID loanRequestId) {
        checkRequestExist(loanRequestId);
        return requestsAndOffers.get(loanRequestId);
    }

    @Override
    public UUID createLoanRequest(LoanRequest loanRequest) {
        requestsAndOffers.put(loanRequest.getIdentifier(), new RequestAndOffers(loanRequest));
        return loanRequest.getIdentifier();
    }

    @Override
    public UUID createOfferForRequest(UUID loanRequestId, LoanOffer loanOffer) {
        checkRequestExist(loanRequestId);
        requestsAndOffers.get(loanRequestId).createOffer(loanOffer);
        return loanOffer.getIdentifier();
    }

    private void checkRequestExist(UUID loanRequestId) {
        if (requestsAndOffers.get(loanRequestId) == null) {
            throw new UnknownLoanRequestIdEx(loanRequestId.toString());
        }
    }
}
