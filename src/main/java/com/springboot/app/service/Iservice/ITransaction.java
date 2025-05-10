package com.springboot.app.service.Iservice;

import com.springboot.app.entity.Transaction;
import org.bson.types.ObjectId;

import java.util.List;

public interface ITransaction  {
    // Define the methods you want to implement in the service class
    // For example:
     List<Transaction> getAllTransactions();
     Transaction getTransactionById(ObjectId id);
     void createTransaction(Transaction transaction);
     void updateTransaction(ObjectId id, Transaction transaction);
     void deleteTransaction(ObjectId id);
}
