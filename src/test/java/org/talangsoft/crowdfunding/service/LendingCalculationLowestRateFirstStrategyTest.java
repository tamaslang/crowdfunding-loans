package org.talangsoft.crowdfunding.service;

import org.junit.Test;
import org.talangsoft.crowdfunding.controller.CrowdFundingPlatform;
import org.talangsoft.crowdfunding.model.CurrentOfferResult;
import org.talangsoft.crowdfunding.model.LoanOffer;
import org.talangsoft.crowdfunding.model.LoanRequest;
import org.talangsoft.crowdfunding.model.RequestAndOffers;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class LendingCalculationLowestRateFirstStrategyTest {

    private LendingCalculationStrategy underTest = new LendingCalculationLowestRateFirstStrategy();

    @Test
    public void requestWithNoOffersShouldReturnCurrentOfferWithZeros() {
        RequestAndOffers rno = new RequestAndOffers(new LoanRequest(BigDecimal.valueOf(100), 100));
        assertEquals(new CurrentOfferResult(BigDecimal.valueOf(0), BigDecimal.valueOf(0)), underTest.calculateCurrentOffer(rno));
    }

    @Test
    public void requestWithOneOfferBelowTheAmountShouldReturnTheOffer() {
        RequestAndOffers rno = new RequestAndOffers(new LoanRequest(BigDecimal.valueOf(100), 100));
        rno.createOffer(new LoanOffer(BigDecimal.valueOf(50), BigDecimal.valueOf(10.0)));
        assertEquals(new CurrentOfferResult(BigDecimal.valueOf(50), BigDecimal.valueOf(10.0)), underTest.calculateCurrentOffer(rno));
    }

    @Test
    public void request100WithTwoOfferBelowTheAmountShouldReturnBothOffersAndAverageInterestRate() {
        RequestAndOffers rno = new RequestAndOffers(new LoanRequest(BigDecimal.valueOf(100), 100));
        rno.createOffer(new LoanOffer(BigDecimal.valueOf(50), BigDecimal.valueOf(10.0)));
        rno.createOffer(new LoanOffer(BigDecimal.valueOf(50), BigDecimal.valueOf(20.0)));
        assertEquals(new CurrentOfferResult(BigDecimal.valueOf(100), BigDecimal.valueOf(15.0)), underTest.calculateCurrentOffer(rno));
    }

    @Test
    public void request1000WithTwoOfferBelowTheAmountShouldReturnBothOffesrAndAverageInterestRate() {
        RequestAndOffers rno = new RequestAndOffers(new LoanRequest(BigDecimal.valueOf(1000), 100));
        rno.createOffer(new LoanOffer(BigDecimal.valueOf(100), BigDecimal.valueOf(5.0)));
        rno.createOffer(new LoanOffer(BigDecimal.valueOf(500), BigDecimal.valueOf(8.6)));
        assertEquals(new CurrentOfferResult(BigDecimal.valueOf(600), BigDecimal.valueOf(8.0)), underTest.calculateCurrentOffer(rno));
    }

    @Test
    public void request1000With2OffersShouldReturnLowerOffer() {
        RequestAndOffers rno = new RequestAndOffers(new LoanRequest(BigDecimal.valueOf(1000), 100));
        rno.createOffer(new LoanOffer(BigDecimal.valueOf(1000), BigDecimal.valueOf(20.0)));
        rno.createOffer(new LoanOffer(BigDecimal.valueOf(1000), BigDecimal.valueOf(10.0)));
        assertEquals(new CurrentOfferResult(BigDecimal.valueOf(1000), BigDecimal.valueOf(10.0)), underTest.calculateCurrentOffer(rno));
    }

    @Test
    public void request1000With2BiggerOffersShouldReturnFractionOfLowerOffer() {
        RequestAndOffers rno = new RequestAndOffers(new LoanRequest(BigDecimal.valueOf(1000), 100));
        rno.createOffer(new LoanOffer(BigDecimal.valueOf(4000), BigDecimal.valueOf(20.0)));
        rno.createOffer(new LoanOffer(BigDecimal.valueOf(5000), BigDecimal.valueOf(10.0)));
        assertEquals(new CurrentOfferResult(BigDecimal.valueOf(1000), BigDecimal.valueOf(10.0)), underTest.calculateCurrentOffer(rno));
    }
}
