package org.talangsoft.crowdfunding;

import org.junit.Test;
import org.talangsoft.crowdfunding.CrowdFundingApi;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;


public class CrowdFundingApiTest {

    private CrowdFundingApi underTest = new CrowdFundingApi();
    @Test
    public void deleteMe(){
        assertThat(underTest.deteletMe(), equalTo(0));
    }
}
