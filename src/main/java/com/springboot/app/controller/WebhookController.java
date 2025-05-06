package com.springboot.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.app.entity.Transaction;
import com.springboot.app.entity.WebhookData;
import com.springboot.app.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/webhook")
public class WebhookController {

    private String checksumKey = "ec33bef9965458820fc1c1d773c0d13fbba0b9e332bec3778060b19450e26182";

    @Autowired
    private TransactionRepository transactionRepository; // Hoặc TransactionMongoRepository

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void handleWebhook(@RequestBody Map<String, Object> webhookData) throws Exception {
        // Log dữ liệu nhận được
        ObjectMapper mapper = new ObjectMapper();
        System.out.println("Received webhook: " + mapper.writeValueAsString(webhookData));

        // Xác minh chữ ký
        String receivedSignature = (String) webhookData.get("signature");
        String dataToSign = webhookData.get("orderCode") + "|" +
                webhookData.get("amount") + "|" +
                webhookData.get("status") + "|" +
                webhookData.get("transactionId") + "|" +
                webhookData.get("transactionDate");
        String calculatedSignature = generateSignature(dataToSign, checksumKey);

        if (!receivedSignature.equals(calculatedSignature)) {
            throw new Exception("Invalid signature");
        }

        // Trích xuất mã giao dịch ngân hàng (nếu có)
        String bankTransactionId = extractBankTransactionId(webhookData);

        // Lưu thông tin giao dịch
        Transaction transaction = new Transaction(); // Hoặc TransactionDocument
        transaction.setOrderCode(String.valueOf(webhookData.get("orderCode")));
        transaction.setAmount(Long.parseLong(String.valueOf(webhookData.get("amount"))));
        transaction.setDescription((String) webhookData.get("description"));
        transaction.setStatus((String) webhookData.get("status"));
        transaction.setTransactionId((String) webhookData.get("transactionId"));
        transaction.setTransactionDate(LocalDateTime.parse((String) webhookData.get("transactionDate")));
        transaction.setPaymentMethod((String) webhookData.get("paymentMethod"));
        transaction.setSignature(receivedSignature);
        transaction.setBankTransactionId(bankTransactionId);

        transactionRepository.save(transaction);

        // Xử lý logic dựa trên trạng thái
        String status = (String) webhookData.get("status");
        if ("PAID".equals(status)) {
            System.out.println("Payment successful for order: " + webhookData.get("orderCode"));
            // Cập nhật trạng thái đơn hàng hoặc thông báo người dùng
        } else if ("CANCELLED".equals(status)) {
            System.out.println("Payment cancelled for order: " + webhookData.get("orderCode"));
        } else {
            System.out.println("Payment status: " + status + " for order: " + webhookData.get("orderCode"));
        }
    }

    private String generateSignature(String data, String key) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        mac.init(secretKeySpec);
        byte[] hash = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    private String extractBankTransactionId(Map<String, Object> webhookData) {
        // Kiểm tra trường description hoặc các trường khác có thể chứa mã giao dịch ngân hàng
        String description = (String) webhookData.get("description");
        if (description != null && description.contains("FT")) { // Ví dụ: Mã FT của Vietcombank
            // Logic đơn giản để trích xuất mã FT (cần tùy chỉnh theo định dạng thực tế)
            int startIndex = description.indexOf("FT");
            if (startIndex != -1) {
                return description.substring(startIndex, startIndex + 12); // FT + 10 ký tự
            }
        }
        // Nếu không tìm thấy, trả về null hoặc kiểm tra các trường khác
        return null;
    }
    @RequestMapping(value = "/receive", method = RequestMethod.POST)
    public ResponseEntity<String> receiveWebhook(@RequestBody WebhookData data) {
        System.out.println(data);
        // Lưu vào database
        return ResponseEntity.ok("Received");
    }
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello, World!");
    }



}
