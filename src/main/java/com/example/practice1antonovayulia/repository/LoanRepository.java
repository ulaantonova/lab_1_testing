package com.example.practice1antonovayulia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.practice1antonovayulia.model.Loan;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByBookId(Long bookId);
    List<Loan> findByReaderId(Long readerId);
    List<Loan> findByReturnDateIsNull();
}
