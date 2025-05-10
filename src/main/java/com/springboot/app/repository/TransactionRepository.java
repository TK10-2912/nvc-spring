package com.springboot.app.repository;

import com.springboot.app.entity.Transaction;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TransactionRepository extends MongoRepository<Transaction, ObjectId> {
    // Custom query methods can be defined here if needed
    // For example, to find transactions by order code:
     @Query(value = "{'orderCode': ?0}")
     List<Transaction> findByOrderCode(String orderCode);
}
