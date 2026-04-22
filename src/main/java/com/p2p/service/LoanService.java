package com.p2p.service;

import java.math.BigDecimal;
import com.p2p.domain.*;

public class LoanService {
    public Loan createLoan(Borrower borrower, BigDecimal amount) {
        // TC-01: Validasi KYC
        if (!borrower.isVerified()) {
            throw new IllegalArgumentException("Borrower not verified");
        }

        // TC-02: Validasi Jumlah (INI YANG DICARI TEST CASE 2)
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Loan amount must be greater than zero");
        }
    
      Loan loan = new Loan();
    
    // TC-03: Business Logic
    if (borrower.getCreditScore() >= 600) {
        loan.setStatus(Loan.Status.APPROVED);
        // atau gunakan loan.approve(); jika method itu ada di Loan.java
    } else {
        loan.setStatus(Loan.Status.REJECTED);
    }
    
    return loan;
    }
}