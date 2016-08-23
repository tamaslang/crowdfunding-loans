import org.talangsoft.crowdfunding.controller.CrowdFundingPlatformImpl
import org.talangsoft.crowdfunding.model.CurrentOfferResult
import org.talangsoft.crowdfunding.repository.InMemoryRequestAndOffersRepository
import org.talangsoft.crowdfunding.service.LendingCalculationLowestRateFirstStrategy
import spock.lang.Unroll

@Unroll
class CrowdFundingPlatformAcceptanceSpec extends spock.lang.Specification {

    def "Amount '#amount' for '#days' with offers #offers should result in amount '#expectedAmount' with rate '#expectedInterestRate'"() {

        def crowdFundingPlatform = new CrowdFundingPlatformImpl(
                new InMemoryRequestAndOffersRepository(),
                new LendingCalculationLowestRateFirstStrategy())

        given: "The loan for '#amount' for '#days' is requested"
        def loanRequestId = crowdFundingPlatform.createLoanRequest(BigDecimal.valueOf(amount), days)
        and: "The lenders for #loanRequestId are offered"
        offers.each {
            crowdFundingPlatform.createLoanOffer(loanRequestId, BigDecimal.valueOf(it.amount), BigDecimal.valueOf(it.interestRate))
        }

        expect: "Current offer with amount '#expectedAmount' with rate '#expectedInterest'"
        crowdFundingPlatform.getCurrentOffer(loanRequestId) == new CurrentOfferResult(BigDecimal.valueOf(expectedAmount), BigDecimal.valueOf(expectedInterestRate))

        where:
        amount | days | offers                                                                                       | expectedAmount | expectedInterestRate
        1000.0 | 100  | [new Offer(100.0, 5.0), new Offer(500.0, 8.6)]                                               | 600.0          | 8.0
        1000.0 | 100  | [new Offer(100.0, 5.0), new Offer(600.0, 6.0), new Offer(600.0, 7.0), new Offer(500.0, 8.2)] | 1000.0         | 6.2
        1000.0 | 100  | [new Offer(500.0, 5.0), new Offer(500.0, 7.0)]                                               | 1000.0         | 6.0
        1000.0 | 100  | [new Offer(2000.0, 5.0), new Offer(4000.0, 4.0)]                                             | 1000.0         | 4.0
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