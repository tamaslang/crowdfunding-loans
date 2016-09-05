# crowdfunding-loans
Coding Test, See desccription: https://github.com/tamaslang/crowdfunding-loans/blob/master/docs/Crowdfunding%20Loans.pdf

This solution assumes:
- Lenders can not make offer with 0% interest rate
- requests can not be made for 0 days
- Currency is not relevant (Otherwise a type like Joda Money would be useful)
- The duration of the loan request will be no more granular than days (Otherwise I might consider using a type like java.time.Duration to represent the duration)
- For a simple solution,  all id's are randomly generated UUID
- If there is no offer the api returns with 0 amount with 0 interest rate for the get current offers call

(But the correct business behaviour would have been clarified with an on-site BA ideally :-)

Acceptance tests are written in Groovy with Spock:
https://github.com/tamaslang/crowdfunding-loans/blob/master/src/test/groovy/CrowdFundingPlatformAcceptanceSpec.groovy


Build with mvn:
```
mvn compile test
```
