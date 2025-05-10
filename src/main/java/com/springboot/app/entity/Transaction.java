package com.springboot.app.entity;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
@Getter
@Setter
@Document(collection = "transaction")
public class Transaction {
    @Id
    private ObjectId id;
    private String orderCode;
    private Long money;
    private String unit;
    private String accountNumber;
    private String depscription;
    private LocalDateTime createdAt;
    private String codePaymentLink;
    private String referenceCode;
    private String codeBank;
    private String bankName;
    private String nameAccoutSend;
    private String accoutNumberSend;
    private String nameAccoutReal;
    private String accoutNumberReal;
    public String toString(){
        return "Transaction{" +
                "id=" + id +
                ", orderCode='" + orderCode + '\'' +
                ", money=" + money +
                ", unit='" + unit + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", depscription='" + depscription + '\'' +
                ", createdAt=" + createdAt +
                ", codePaymentLink='" + codePaymentLink + '\'' +
                ", referenceCode='" + referenceCode + '\'' +
                ", codeBank='" + codeBank + '\'' +
                ", bankName='" + bankName + '\'' +
                ", nameAccoutSend='" + nameAccoutSend + '\'' +
                ", accoutNumberSend='" + accoutNumberSend + '\'' +
                ", nameAccoutReal='" + nameAccoutReal + '\'' +
                ", accoutNumberReal='" + accoutNumberReal + '\'' +
                '}';
    }


}
