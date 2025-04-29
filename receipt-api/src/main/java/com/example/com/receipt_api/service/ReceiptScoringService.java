package com.example.com.receipt_api.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.example.com.receipt_api.model.Receipt;
import com.example.com.receipt_api.model.Receipt.Item;

@Service
public class ReceiptScoringService {

    private static final Pattern ALPHANUMERIC = Pattern.compile("[a-zA-Z0-9]");

    public int calculatePoints(Receipt receipt) {
        int points = 0;

        // Rule 1: One point per alphanumeric character in retailer name
        points += (int) receipt.getRetailer().chars()
                .filter(ch -> ALPHANUMERIC.matcher(Character.toString((char) ch)).matches())
                .count();

        // Rule 2: 50 points if total is a round dollar (e.g., 15.00)
        BigDecimal total = new BigDecimal(receipt.getTotal());
        if (total.scale() <= 0 || total.remainder(BigDecimal.ONE).compareTo(BigDecimal.ZERO) == 0) {
            points += 50;
        }

        // Rule 3: 25 points if total is multiple of 0.25
        if (total.remainder(new BigDecimal("0.25")).compareTo(BigDecimal.ZERO) == 0) {
            points += 25;
        }

        // Rule 4: 5 points for every two items
        points += (receipt.getItems().size() / 2) * 5;

        // Rule 5: If description length % 3 == 0 → price * 0.2 and round up
        for (Item item : receipt.getItems()) {
            String desc = item.getShortDescription().trim();
            if (desc.length() % 3 == 0) {
                BigDecimal price = new BigDecimal(item.getPrice());
                points += (int) Math.ceil(price.multiply(new BigDecimal("0.2")).doubleValue());
            }
        }

        // Rule 6: 6 points if purchase day is odd
        LocalDate date = LocalDate.parse(receipt.getPurchaseDate());
        if (date.getDayOfMonth() % 2 == 1) {
            points += 6;
        }

        // Rule 7: 10 points if purchase time is after 2:00PM and before 4:00PM
        LocalTime time = LocalTime.parse(receipt.getPurchaseTime());
        if (time.isAfter(LocalTime.of(14, 0)) && time.isBefore(LocalTime.of(16, 0))) {
            points += 10;
        }

        return points;
    }
}
