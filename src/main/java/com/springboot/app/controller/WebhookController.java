package com.springboot.app.controller;

import com.springboot.app.entity.Transaction;
import com.springboot.app.entity.WebhookData;
import com.springboot.app.service.serviceIml.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.ConvertOperators;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/webhook")

public class WebhookController {
    @Autowired
    private TransactionService transactionService;
    @RequestMapping(value = "/receive", method = RequestMethod.POST)
    public ResponseEntity<String> receiveWebhook(@RequestBody WebhookData data) {
        System.out.println(data);
        List<String> values = data.getValues();
        Transaction transaction = new Transaction();
        transaction.setOrderCode(values.get(0) != null ? values.get(0) : "");
        transaction.setMoney(Long.valueOf(values.get(1) != null ? values.get(1) : "0"));
        transaction.setUnit(values.get(2) != null ? values.get(2) : "");
        transaction.setAccountNumber(values.get(3) != null ? values.get(3) : "");
        transaction.setDepscription(values.get(4) != null ? values.get(4) : "");
        transaction.setCreatedAt(LocalDateTime.parse(values.get(5) != null ? values.get(5) : ""));
        transaction.setCodePaymentLink(values.get(6) != null ? values.get(6) : "");
        transaction.setReferenceCode(values.get(7) != null ? values.get(7) : "");
        transaction.setCodeBank(values.get(8) != null ? values.get(8) : "");
        transaction.setBankName(values.get(9) != null ? values.get(9) : "");
        transaction.setNameAccoutSend(values.get(10) != null ? values.get(10) : "");
        transaction.setAccoutNumberSend(values.get(11) != null ? values.get(11) : "");
        transaction.setNameAccoutReal(values.get(12) != null ? values.get(12) : "");
        transaction.setAccoutNumberReal(values.get(13) != null ? values.get(13) : "");
        System.out.println("transaction" + transaction);
        this.transactionService.createTransaction(transaction);
        // Lưu vào database
        return ResponseEntity.ok("Received");
    }
    private String getValue(List<String> values, int index) {
        return (values != null && values.size() > index && values.get(index) != null)
                ? values.get(index)
                : "";
    }


}
