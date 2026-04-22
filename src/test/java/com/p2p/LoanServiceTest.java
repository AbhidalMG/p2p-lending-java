package com.p2p;
import org.junit.jupiter.api.Test;

import com.p2p.domain.Borrower;
import com.p2p.service.LoanService;

import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
public class LoanServiceTest {
     @Test
void shouldRejectLoanWhenBorrowerNotVerified() {

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

// Kita mengetes bahwa pemanggilan method ini HARUS menghasilkan IllegalArgumentException
assertThrows(IllegalArgumentException.class, () -> {
    loanService.createLoan(borrower, amount);
}, "Harusnya melempar IllegalArgumentException karena borrower belum verified");
}}