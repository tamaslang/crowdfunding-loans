package org.talangsoft.crowdfunding.repository;

public class UnknownLoanRequestIdEx extends RuntimeException {

    public UnknownLoanRequestIdEx(String requestId) {
        super("Loan Request with id "+requestId+" was not found!");
    }
}
