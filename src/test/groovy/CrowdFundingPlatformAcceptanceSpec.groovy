import org.talangsoft.crowdfunding.controller.CrowdFundingPlatformImpl
import org.talangsoft.crowdfunding.model.CurrentOfferResult
import org.talangsoft.crowdfunding.repository.InMemoryRequestAndOffersRepository
import spock.lang.Unroll

@Unroll
class CrowdFundingPlatformAcceptanceSpec extends spock.lang.Specification {

    def "Amount '#amount' for '#days' with offers #offers should result in amount '#expectedAmount' with rate '#expectedInterestRate'"() {

        def crowdFundingPlatform = new CrowdFundingPlatformImpl(
                new InMemoryRequestAndOffersRepository())

        given: "The loan for '#amount' for '#days' is requested"
        def loanRequestId = crowdFundingPlatform.createLoanRequest(new BigDecimal(amount), days)
        and: "The lenders for #loanRequestId are offered #offers"
        offers.each {
            crowdFundingPlatform.createLoanOffer(loanRequestId, it.amount, it.interestRate)
        }

        expect: "Current offer with amount '#expectedAmount' with rate '#expectedInterest'"
        crowdFundingPlatform.getCurrentOffer(loanRequestId) == new CurrentOfferResult(new BigDecimal(expectedAmount), new BigDecimal(expectedInterestRate))

        where:
        amount | days | offers                                                                                         | expectedAmount | expectedInterestRate
        1000.0 | 100  | [new Offer("100", "5"), new Offer("500", "8")]                                                 | "600"          | "7.5"
        1000.0 | 100  | [new Offer("100", "5"), new Offer("600", "6"), new Offer("600", "7"), new Offer("500", "8.2")] | "1000"         | "6.2"
        1000.0 | 100  | [new Offer("500", "5"), new Offer("500", "7")]                                                 | "1000"         | "6"
        1000.0 | 100  | [new Offer("2000", "5"), new Offer("4000", "4")]                                               | "1000"         | "4"
    }

    static class Offer {
        BigDecimal amount
        BigDecimal interestRate

        Offer(String amount, String interestRate) {
            this.amount = new BigDecimal(amount)
            this.interestRate = new BigDecimal(interestRate)
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