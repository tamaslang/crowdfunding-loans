import org.joda.money.CurrencyUnit
import org.joda.money.Money
import org.talangsoft.crowdfunding.CombinedOffer
import org.talangsoft.crowdfunding.CrowdFundingPlatformImpl
import spock.lang.Unroll

@Unroll
class CrowdFundingPlatformAcceptanceSpec extends spock.lang.Specification {

    def "Amount '#amount' for '#days' with offers #offers should result in amount '#expectedAmount' with rate '#expectedInterestRate'"() {

        def crowdFundingPlatform = new CrowdFundingPlatformImpl()

        given: "The loan for '#amount' for '#days' is requested"
        def loanRequestId = crowdFundingPlatform.createLoanRequest(Money.of(CurrencyUnit.GBP, amount), days)
        and: "The lenders for #loanRequestId are offered"
        offers.each {
            crowdFundingPlatform.createLoanOffer(loanRequestId, Money.of(CurrencyUnit.GBP, it.amount), BigDecimal.valueOf(it.interestRate))
        }

        expect: "Current offer with amount '#expectedAmount' with rate '#expectedInterest'"
        crowdFundingPlatform.getCurrentOffer(loanRequestId) == new CombinedOffer(Money.of(CurrencyUnit.GBP, expectedAmount), BigDecimal.valueOf(expectedInterestRate))

        where:
        amount | days | offers                                                                                       | expectedAmount | expectedInterestRate
        1000   | 100  | [new Offer(100.0, 5.0), new Offer(500.0, 8.6)]                                               | 600            | 8.0
        1000   | 100  | [new Offer(100.0, 5.0), new Offer(600.0, 6.0), new Offer(600.0, 7.0), new Offer(500.0, 8.2)] | 1000           | 6.2
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