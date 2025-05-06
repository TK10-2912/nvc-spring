package com.springboot.app.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Transaction {
    @Id
    private String orderCode;
    private Long amount;
    private String description;
    private String status;
    private String transactionId;
    private LocalDateTime transactionDate;
    private String paymentMethod;
    private String signature;
    private String bankTransactionId; // Trường mới để lưu mã giao dịch ngân hàng
}
