package org.talangsoft.crowdfunding;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;


public class CrowdFundingPlatformImplTest {

    private CrowdFundingPlatform underTest = new CrowdFundingPlatformImpl();

    @Test
    public void createLoanRequestFor100DaysShouldReturnUniqueIdentifier() {
        UUID request1Id = underTest.createLoanRequest(BigDecimal.valueOf(100), 100);
        UUID request2Id = underTest.createLoanRequest(BigDecimal.valueOf(100), 100);

        assertThat(request1Id, not(equalTo(request2Id)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void createLoanRequestForNegativeDaysShouldReturnFailure() {
        underTest.createLoanRequest(BigDecimal.valueOf(100), -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createLoanRequestFor0DaysShouldReturnFailure() {
        underTest.createLoanRequest(BigDecimal.valueOf(100), 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createLoanRequestFor0AmountShouldReturnFailure() {
        underTest.createLoanRequest(BigDecimal.valueOf(0), 100);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createLoanOfferForNegativeRateShouldReturnFailure() {
        underTest.createLoanOffer(UUID.randomUUID(), BigDecimal.valueOf(100), BigDecimal.valueOf(-1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void createLoanOfferFor0RateShouldReturnFailure() {
        underTest.createLoanOffer(UUID.randomUUID(), BigDecimal.valueOf(100), BigDecimal.valueOf(0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void createLoanOfferFor0AmountShouldReturnFailure() {
        underTest.createLoanOffer(UUID.randomUUID(), BigDecimal.valueOf(0), BigDecimal.valueOf(1));
    }

    @Test(expected = UnknownLoanRequestIdEx.class)
    public void createLoanOfferForUnknownRequestShouldReturnFailure() {
        UUID unkownRequestId = UUID.randomUUID();
        underTest.createLoanOffer(unkownRequestId, BigDecimal.valueOf(100), BigDecimal.valueOf(1));
    }

    @Test
    public void createLoanOfferForExistingLoanRequestShouldBeSuccessful() {
        UUID loanRequestId = underTest.createLoanRequest(BigDecimal.valueOf(100), 100);
        underTest.createLoanOffer(loanRequestId, BigDecimal.valueOf(100), BigDecimal.valueOf(1));
    }

    @Test(expected = UnknownLoanRequestIdEx.class)
    public void getCurrentOfferForUnknownRequestShouldReturnFailure() {
        UUID unkownRequestId = UUID.randomUUID();
        underTest.getCurrentOffer(unkownRequestId);
    }

    @Test
    public void getCurrentOfferForExistingRequestWithoutOffersShouldReturnOfferWithZeros() {
        UUID loanRequestId = underTest.createLoanRequest(BigDecimal.valueOf(100), 100);
        assertThat(underTest.getCurrentOffer(loanRequestId), equalTo(new CurrentOffer(BigDecimal.ZERO, BigDecimal.ZERO)));
    }

}
