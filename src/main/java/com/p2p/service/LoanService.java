package com.p2p.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.math.BigDecimal;
import com.p2p.domain.*;

public class LoanService {
    // Inisialisasi Logger khusus untuk class ini
    private static final Logger logger = LogManager.getLogger(LoanService.class);

    public Loan createLoan(Borrower borrower, BigDecimal amount) {
        logger.info("Menerima permintaan pinjaman baru.");

        // TC-01: Validasi KYC
        if (!borrower.isVerified()) {
            logger.error("Borrower belum terverifikasi (KYC).");
            throw new IllegalArgumentException("Borrower not verified");
        }

        // TC-02: Validasi Amount
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            logger.warn("Jumlah pinjaman tidak valid ({}).", amount);
            throw new IllegalArgumentException("Loan amount must be greater than zero");
        }

        Loan loan = new Loan();
        
        // TC-03 & TC-04: Business Logic
        if (borrower.getCreditScore() >= 600) {
            logger.info("Credit score mencukupi ({}). Pinjaman DISETUJUI.", borrower.getCreditScore());
            loan.approve();
        } else {
            logger.info("Credit score rendah ({}). Pinjaman DITOLAK.", borrower.getCreditScore());
            loan.reject();
        }

        logger.info("Proses selesai. ID Loan dibuat dengan status: {}", loan.getStatus());
        return loan;
    }
}