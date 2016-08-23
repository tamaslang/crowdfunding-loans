package org.talangsoft.crowdfunding.repository;

import org.talangsoft.crowdfunding.model.LoanOffer;
import org.talangsoft.crowdfunding.model.LoanRequest;
import org.talangsoft.crowdfunding.model.RequestAndOffers;

import java.util.UUID;

public interface RequestAndOffersRepository {
    RequestAndOffers findByLoanRequestId(UUID loanRequestId);

    UUID createLoanRequest(LoanRequest loanRequest);

    UUID createOfferForRequest(UUID loanRequestId, LoanOffer loanOffer);
}
