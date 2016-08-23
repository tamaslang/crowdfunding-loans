import org.talangsoft.crowdfunding.CrowdFundingApi
import spock.lang.*

@Unroll
class CrowdFundingApiAcceptanceSpec extends spock.lang.Specification {

    def "Amount '#amount' for '#days' with offers #offers should result in amount '#epectedAmount' with rate '#expectedInterest'"() {

        def crowdFundingApi =new CrowdFundingApi()

        expect:
        crowdFundingApi.deteletMe() == 0


        where:
        amount     | days     | offers                                                     | epectedAmount | expectedInterest
        1000       | 100      | new Offers([new Offer(100.0,5.0), new Offer(500.0, 8.6)])  | 600           | 8.6
    }


    static class Offers {
        List<Offer> offerList;

        Offers(List<Offer> offerList) {
            this.offerList = offerList
        }

        @Override
        public String toString() {
            return "Offers{" + offerList + '}';
        }
    }
    static class Offer {
        double amount
        double interestRate

        Offer(double amount, double interestRate) {
            this.amount = amount
            this.interestRate = interestRate
        }

        @Override
        public String toString() {
            return "Offer{" +
                    "amount=" + amount +
                    ", interestRate=" + interestRate +
                    '}';
        }
    }
}