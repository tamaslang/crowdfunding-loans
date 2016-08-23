package org.talangsoft.crowdfunding;

import com.google.common.base.Preconditions;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CrowdFundingPlatformImpl implements CrowdFundingPlatform {

    private Map<UUID, RequestAndOffers> requestsAndOffers = new HashMap<>();

    @Override
    public UUID createLoanRequest(BigDecimal amount, Integer days) {
        Preconditions.checkArgument(days > 0, "Days should be greater than 0");
        Preconditions.checkArgument(amount.doubleValue() > 0, "The amount should be greater than 0");
        LoanRequest loanRequest = new LoanRequest(amount, days);
        requestsAndOffers.put(loanRequest.getIdentifier(), new RequestAndOffers(loanRequest));
        return loanRequest.getIdentifier();
    }

    @Override
    public UUID createLoanOffer(UUID loanRequestId, BigDecimal amount, BigDecimal interestRate) {
        Preconditions.checkArgument(interestRate.doubleValue() > 0, "Interest Rate should be greater than 0%");
        Preconditions.checkArgument(amount.doubleValue() > 0, "The amount should be greater than 0");
        checkRequestExist(loanRequestId);
        LoanOffer offer = new LoanOffer(amount, interestRate);
        requestsAndOffers.get(loanRequestId).createOffer(offer);
        return offer.getIdentifier();
    }

    @Override
    public CurrentOffer getCurrentOffer(UUID loanRequestId) {
        checkRequestExist(loanRequestId);
        return requestsAndOffers.get(loanRequestId).calculateCurrentOffer();
    }

    private void checkRequestExist(UUID loanRequestId) {
        if (requestsAndOffers.get(loanRequestId) == null) {
            throw new UnknownLoanRequestIdEx(loanRequestId.toString());
        }
    }
}
