package com.p2p.service;
import java.math.BigDecimal;

import com.p2p.domain.*;
public class LoanService {
    public Loan createLoan(Borrower borrower, BigDecimal amount) {
        // =========================
        // VALIDASI UTAMA (TC-01)
        // =========================
        // Jika borrower belum terverifikasi,
        // maka proses harus dihentikan
        if (!borrower.isVerified()) {
            throw new IllegalArgumentException("Borrower not verified");
        }
        // Membuat objek loan baru
        Loan loan = new Loan();
        // =========================
        // LOGIC SEDERHANA (sementara)
        // =========================
        // Jika credit score tinggi → APPROVED
        // Jika tidak → REJECTED
        if (borrower.getCreditScore() >= 600) {
            loan.setStatus(Loan.Status.APPROVED);
        } else {
            loan.setStatus(Loan.Status.REJECTED);
        }
        return loan;
    }
}
