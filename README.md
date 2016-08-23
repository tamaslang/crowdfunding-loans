# crowdfunding-loans
Coding Test

This solution assumes (but the correct business behaviour would have been clarified):
- Lenders can not make offer with 0% interest rate
- Currency is not relevant (Otherwise a type like Joda Money would be useful)
- The duration of the loan request will be no more granular than days (Otherwise I might consider using a type like java.time.Duration to represent the duration)
- For a simple solution,  all id's are randomly generated UUID
