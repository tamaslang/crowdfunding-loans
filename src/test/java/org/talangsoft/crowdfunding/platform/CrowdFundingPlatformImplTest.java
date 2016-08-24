package org.talangsoft.crowdfunding.platform;

import org.junit.Test;
import org.talangsoft.crowdfunding.controller.CrowdFundingPlatform;
import org.talangsoft.crowdfunding.controller.CrowdFundingPlatformImpl;
import org.talangsoft.crowdfunding.model.CurrentOfferResult;
import org.talangsoft.crowdfunding.model.LoanOffer;
import org.talangsoft.crowdfunding.model.LoanRequest;
import org.talangsoft.crowdfunding.model.RequestAndOffers;
import org.talangsoft.crowdfunding.repository.InMemoryRequestAndOffersRepository;
import org.talangsoft.crowdfunding.repository.UnknownLoanRequestIdEx;

import java.math.BigDecimal;
import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;


public class CrowdFundingPlatformImplTest {

    private CrowdFundingPlatform underTest = new CrowdFundingPlatformImpl(
            new InMemoryRequestAndOffersRepository());

    @Test
    public void createLoanRequestFor100DaysShouldReturnUniqueIdentifier() {
        UUID request1Id = underTest.createLoanRequest(new BigDecimal("100"), 100);
        UUID request2Id = underTest.createLoanRequest(new BigDecimal("100"), 100);

        assertThat(request1Id, not(equalTo(request2Id)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void createLoanRequestForNegativeDaysShouldReturnFailure() {
        underTest.createLoanRequest(new BigDecimal("100"), -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createLoanRequestFor0DaysShouldReturnFailure() {
        underTest.createLoanRequest(new BigDecimal("100"), 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createLoanRequestFor0AmountShouldReturnFailure() {
        underTest.createLoanRequest(new BigDecimal("0"), 100);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createLoanOfferForNegativeRateShouldReturnFailure() {
        underTest.createLoanOffer(UUID.randomUUID(), new BigDecimal("100"), new BigDecimal("-1"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void createLoanOfferFor0RateShouldReturnFailure() {
        underTest.createLoanOffer(UUID.randomUUID(), new BigDecimal("100"), new BigDecimal("0"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void createLoanOfferFor0AmountShouldReturnFailure() {
        underTest.createLoanOffer(UUID.randomUUID(), new BigDecimal("0"), new BigDecimal("1"));
    }

    @Test(expected = UnknownLoanRequestIdEx.class)
    public void createLoanOfferForUnknownRequestShouldReturnFailure() {
        UUID unkownRequestId = UUID.randomUUID();
        underTest.createLoanOffer(unkownRequestId, new BigDecimal("100"), new BigDecimal("1"));
    }

    @Test
    public void createLoanOfferForExistingLoanRequestShouldBeSuccessful() {
        UUID loanRequestId = underTest.createLoanRequest(new BigDecimal("100"), 100);
        underTest.createLoanOffer(loanRequestId, new BigDecimal("100"), new BigDecimal("1"));
    }

    @Test(expected = UnknownLoanRequestIdEx.class)
    public void getCurrentOfferForUnknownRequestShouldReturnFailure() {
        UUID unkownRequestId = UUID.randomUUID();
        underTest.getCurrentOffer(unkownRequestId);
    }

    @Test
    public void getCurrentOfferForExistingRequestWithoutOffersShouldReturnOfferWithZeros() {
        UUID loanRequestId = underTest.createLoanRequest(new BigDecimal("100"), 100);
        assertThat(underTest.getCurrentOffer(loanRequestId), equalTo(new CurrentOfferResult(BigDecimal.ZERO, BigDecimal.ZERO)));
    }

    @Test
    public void requestWithNoOffersShouldReturnCurrentOfferWithZeros() {
        UUID loanRequestId = underTest.createLoanRequest(new BigDecimal("100"), 100);
        assertThat(underTest.getCurrentOffer(loanRequestId), equalTo(new CurrentOfferResult(new BigDecimal("0"), new BigDecimal("0"))));
    }

//    @Test
//    public void requestWithOneOfferBelowTheAmountShouldReturnTheOffer() {
//        RequestAndOffers rno = new RequestAndOffers(new LoanRequest(new BigDecimal("100"), 100));
//        rno.createOffer(new LoanOffer(new BigDecimal("50"), new BigDecimal("10")));
//        assertEquals(new CurrentOfferResult(new BigDecimal("50"), new BigDecimal("10")), underTest.calculateCurrentOffer(rno));
//    }
//
//    @Test
//    public void request100WithTwoOfferBelowTheAmountShouldReturnBothOffersAndAverageInterestRate() {
//        RequestAndOffers rno = new RequestAndOffers(new LoanRequest(new BigDecimal("100"), 100));
//        rno.createOffer(new LoanOffer(new BigDecimal("50"), new BigDecimal("10")));
//        rno.createOffer(new LoanOffer(new BigDecimal("50"), new BigDecimal("20")));
//        assertEquals(new CurrentOfferResult(new BigDecimal("100"), new BigDecimal("15.0")), underTest.calculateCurrentOffer(rno));
//    }
//
//    @Test
//    public void request1000WithTwoOfferBelowTheAmountShouldReturnBothOffesrAndAverageInterestRate() {
//        RequestAndOffers rno = new RequestAndOffers(new LoanRequest(new BigDecimal("1000"), 100));
//        rno.createOffer(new LoanOffer(new BigDecimal("100"), new BigDecimal("5.0")));
//        rno.createOffer(new LoanOffer(new BigDecimal("500"), new BigDecimal("8.6")));
//        assertEquals(new CurrentOfferResult(new BigDecimal("600"), new BigDecimal("8.0")), underTest.c(rno));
//    }
//
//    @Test
//    public void request1000With3OffersShouldReturnLowerOffer() {
//        RequestAndOffers rno = new RequestAndOffers(new LoanRequest(new BigDecimal("1000"), 100));
//        rno.createOffer(new LoanOffer(new BigDecimal("1000"), new BigDecimal("20")));
//        rno.createOffer(new LoanOffer(new BigDecimal("1000"), new BigDecimal("10")));
//        rno.createOffer(new LoanOffer(new BigDecimal("1000"), new BigDecimal("15")));
//        assertEquals(new CurrentOfferResult(new BigDecimal("1000"), new BigDecimal("10")), underTest.calculateCurrentOffer(rno));
//    }
//
//    @Test
//    public void request1000With2BiggerOffersShouldReturnFractionOfLowerOffer() {
//        RequestAndOffers rno = new RequestAndOffers(new LoanRequest(new BigDecimal("1000"), 100));
//        rno.createOffer(new LoanOffer(new BigDecimal("4000"), new BigDecimal("20")));
//        rno.createOffer(new LoanOffer(new BigDecimal("5000"), new BigDecimal("10")));
//        assertEquals(new CurrentOfferResult(new BigDecimal("1000"), new BigDecimal("10")), underTest.calculateCurrentOffer(rno));
//    }

}
