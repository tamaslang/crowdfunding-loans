import org.talangsoft.crowdfunding.CrowdFundingApi
import spock.lang.*

@Unroll
class CrowdFundingApiAcceptanceSpec extends spock.lang.Specification {
    def "Crowdfunding API for #description should result in #expected"() {

        def crowdFundingApi =new CrowdFundingApi()
        expect:
        crowdFundingApi.deteletMe() == expected

        where:
        description     | expected
        "Just calling the deleteMe method"  | 0
    }
}