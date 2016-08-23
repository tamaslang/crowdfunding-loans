package org.talangsoft.crowdfunding;

import org.joda.money.Money;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;


public class CrowdFundingPlatformImplTest {

    private CrowdFundingPlatform underTest = new CrowdFundingPlatformImpl();

    @Test
    public void createLoanRequestFor100DaysShouldReturnUniqueIdentifier(){
        UUID request1Id = underTest.createLoanRequest(Money.parse("GBP 100.0"), 100);
        UUID request2Id = underTest.createLoanRequest(Money.parse("GBP 100.0"), 100);

        assertThat(request1Id, not(equalTo(request2Id)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void createLoanRequestForNegativeDaysShouldReturnFailure(){
        underTest.createLoanRequest(Money.parse("GBP 100.0"),-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createLoanRequestFor0DaysShouldReturnFailure(){
        underTest.createLoanRequest(Money.parse("GBP 100.0"),0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createLoanOfferForNegativeRateShouldReturnFailure(){
        underTest.createLoanOffer(UUID.randomUUID(), Money.parse("GBP 100.0"), BigDecimal.valueOf(-1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void createLoanOfferFor0RateShouldReturnFailure(){
        underTest.createLoanOffer(UUID.randomUUID(), Money.parse("GBP 100.0"), BigDecimal.valueOf(0));
    }
//
//    @Test(expected = UnknownLoanRequestIdEx.class)
//    public void createLoanOfferForUnknownReuqestShouldReturnFailure(){
//        UUID unkownRequestId = UUID.randomUUID();
//        underTest.createLoanOffer(unkownRequestId, Money.parse("GBP 100.0"), BigDecimal.valueOf(1));
//    }

}
