package com.p2p;
import org.junit.jupiter.api.Test;

import com.p2p.domain.Borrower;
import com.p2p.domain.Loan;
import com.p2p.service.LoanService;

import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
public class LoanServiceTest {
     @Test
void shouldRejectLoanWhenBorrowerNotVerified() {
//TC-01: shouldRejectLoanWhenBorrowerNotVerified
// =====================================================
// SCENARIO:
// Borrower tidak terverifikasi (KYC = false)
// Ketika borrower mengajukan pinjaman
// Maka sistem harus menolak dengan melempar exception
// =====================================================

// =========================
// Arrange (Initial Condition)
// =========================
// Borrower belum lolos proses KYC
Borrower borrower = new Borrower(false, 700);

// Service untuk pengajuan loan
LoanService loanService = new LoanService();

// Jumlah pinjaman valid
BigDecimal amount = BigDecimal.valueOf(1000);

// =========================
// Act & Assert (Action & Expected Result)
// =========================

assertThrows(IllegalArgumentException.class, () -> {
    loanService.createLoan(borrower, amount);
}, "Harusnya melempar IllegalArgumentException karena borrower belum verified");
}

//TC-02: shouldRejectLoanWhenAmountIsZeroOrNegative
@Test
void shouldRejectLoanWhenAmountIsZeroOrNegative() {
//=====================================================
// SCENARIO:
// Borrower terverifikasi (KYC = true) dengan credit score tinggi
// Ketika borrower mengajukan pinjaman dengan jumlah 0 atau negatif
// Maka sistem harus menolak dengan melempar exception
    Borrower borrower = new Borrower(true, 750); 
    LoanService loanService = new LoanService();

// 1. Arrange untuk angka NOL dan NEGATIF    
    BigDecimal zeroAmount = BigDecimal.ZERO;
    BigDecimal negativeAmount = new BigDecimal("-1000");

 // 2. Act & Assert untuk angka NOL
    assertThrows(IllegalArgumentException.class, () -> {
        loanService.createLoan(borrower, zeroAmount);
    }, "Harusnya error saat jumlah pinjaman 0");

// 3. Act & Assert untuk angka NEGATIF
    assertThrows(IllegalArgumentException.class, () -> {
        loanService.createLoan(borrower, negativeAmount);
    }, "Harusnya error saat jumlah pinjaman negatif");
}

@Test
//TC-03: shouldApproveLoanWhenCreditScoreHigh
void shouldApproveLoanWhenCreditScoreHigh(){
    // =====================================================
    // SCENARIO:
    // Borrower terverifikasi (KYC = true) dengan credit score tinggi (misal 750)
    // Ketika borrower mengajukan pinjaman dengan jumlah valid
    // Maka sistem harus menyetujui pinjaman tersebut
    // =====================================================

    // 1. Arrange
    Borrower borrower = new Borrower(true, 750); // Verified dan credit score tinggi
    LoanService loanService = new LoanService();
    BigDecimal amount = BigDecimal.valueOf(1000);

    // 2. Act
    var loan = loanService.createLoan(borrower, amount);

    // 3. Assert
    assertEquals("APPROVED", loan.getStatus().name(), "Loan harus disetujui untuk credit score tinggi");
}

@Test
//TC-04: shouldRejectLoanWhenCreditScoreLow
void shouldRejectLoanWhenCreditScoreLow() {
    // =====================================================
    // SCENARIO:
    // Borrower terverifikasi (KYC = true) dengan credit score rendah (misal 500)
    // Ketika borrower mengajukan pinjaman dengan jumlah valid
    // Maka sistem harus menolak pinjaman tersebut
    // =====================================================

    //1. Arrange
    Borrower borrower = new Borrower(true, 500); 
    LoanService loanService = new LoanService();
    BigDecimal amount = new BigDecimal("2000");

    // 2. Act (Jalankan aksi)
    Loan result = loanService.createLoan(borrower, amount);

    // 3. Assert (Pastikan hasil sesuai ekspektasi)
    assertNotNull(result, "Objek loan harusnya berhasil dibuat");
    assertEquals(Loan.Status.REJECTED, result.getStatus(), 
                 "Seharusnya status loan REJECTED karena credit score rendah");
}

}