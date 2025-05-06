package com.springboot.app.controller;
import com.springboot.app.entity.Transaction;
import com.springboot.app.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionRepository transactionRepository;

    @GetMapping
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

//    @GetMapping("/bank/{bankTransactionId}")
//    public Transaction getTransactionByBankId(@PathVariable String bankTransactionId) {
//        return transactionRepository(bankTransactionId);
//    }
}
