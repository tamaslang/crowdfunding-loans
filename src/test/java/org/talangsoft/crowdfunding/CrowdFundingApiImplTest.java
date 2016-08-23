package org.talangsoft.crowdfunding;

import org.joda.money.Money;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;


public class CrowdFundingApiImplTest {

    private CrowdFundingPlatform underTest = new CrowdFundingPlatformImpl();
    @Test
    public void createLoanRequestFor100DaysShouldReturnIdentifier(){
        assertThat(underTest.createLoanRequest(Money.parse("GBP 100.0"), 100), notNullValue());
    }
}
