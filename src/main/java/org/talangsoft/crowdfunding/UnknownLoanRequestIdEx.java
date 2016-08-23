package org.talangsoft.crowdfunding;

public class UnknownLoanRequestIdEx extends RuntimeException {

    public UnknownLoanRequestIdEx(String requestId) {
        super("Loan Request with id "+requestId+" was not found!");
    }
}
