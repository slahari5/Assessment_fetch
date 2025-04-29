package com.example.com.receipt_api.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.com.receipt_api.model.Receipt;
import com.example.com.receipt_api.service.ReceiptScoringService;

@RestController
@RequestMapping("/receipts")
public class ReceiptController {

    private final Map<String, Receipt> receiptStore = new ConcurrentHashMap<>();
    private final ReceiptScoringService scoringService;

    @Autowired
    public ReceiptController(ReceiptScoringService scoringService) {
        this.scoringService = scoringService;
    }

    @PostMapping("/process")
    public ResponseEntity<Map<String, String>> processReceipt(@RequestBody Receipt receipt) {
        String id = UUID.randomUUID().toString();
        receiptStore.put(id, receipt);
        Map<String, String> response = new HashMap<>();
        response.put("id", id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/points")
    public ResponseEntity<Map<String, Integer>> getPoints(@PathVariable String id) {
        Receipt receipt = receiptStore.get(id);
        if (receipt == null) {
            return ResponseEntity.notFound().build();
        }

        int points = scoringService.calculatePoints(receipt);
        Map<String, Integer> response = new HashMap<>();
        response.put("points", points);
        return ResponseEntity.ok(response);
    }
}
