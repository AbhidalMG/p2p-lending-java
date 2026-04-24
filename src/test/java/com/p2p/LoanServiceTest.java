package com.p2p;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import com.p2p.domain.*;
import com.p2p.service.LoanService;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;

public class LoanServiceTest {
    private static final Logger logger = LogManager.getLogger(LoanServiceTest.class);
    private final LoanService loanService = new LoanService();

    @Test
    void shouldRejectLoanWhenBorrowerNotVerified() {
        logger.info("Menjalankan TC-01: Verifikasi KYC.");
        Borrower borrower = new Borrower(false, 700);
        
        assertThrows(IllegalArgumentException.class, () -> {
            loanService.createLoan(borrower, BigDecimal.valueOf(1000));
        });
        logger.debug("TC-01 berhasil melewati pengecekan exception.");
    }

    // TC-02
    @Test
    void shouldRejectLoanWhenAmountIsZeroOrNegative() {
        logger.info("Menjalankan TC-02: Validasi Jumlah Pinjaman.");
        Borrower borrower = new Borrower(true, 700);

        assertThrows(IllegalArgumentException.class, () -> {
            loanService.createLoan(borrower, BigDecimal.ZERO);
        });
        logger.debug("TC-02 berhasil menolak angka nol.");
    }

    //TC-03
   @Test
    void shouldApproveLoanWhenCreditScoreHigh() {
        logger.info("Menjalankan TC-03: Skenario Skor Kredit Tinggi.");
        Borrower borrower = new Borrower(true, 750);
        
        Loan result = loanService.createLoan(borrower, BigDecimal.valueOf(5000));
        
        assertEquals(Loan.Status.APPROVED, result.getStatus());
        logger.info("TC-03 Selesai: Status adalah APPROVED.");
    }

   //TC-04
    @Test
    void shouldRejectLoanWhenCreditScoreLow() {
        logger.info("Menjalankan TC-04: Skenario Credit Score Rendah.");
        Borrower borrower = new Borrower(true, 500);
        
        Loan result = loanService.createLoan(borrower, BigDecimal.valueOf(2000));
        
        assertEquals(Loan.Status.REJECTED, result.getStatus());
        logger.info("TC-04 Selesai: Status adalah REJECTED.");
    }
}