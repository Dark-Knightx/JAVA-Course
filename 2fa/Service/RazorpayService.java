package com.example.BOWO.Service;

import com.razorpay.RazorpayClient;
import com.razorpay.Order;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RazorpayService {

    @Value("${razorpay.api.key}")
    private String apiKey;

    @Value("${razorpay.api.secret}")
    private String apiSecret;

    public String createOrder(double amount) {
        try {
            RazorpayClient client = new RazorpayClient(apiKey, apiSecret);

            // Prepare order data
            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", amount * 100); // Convert amount to paise
            orderRequest.put("currency", "INR");
            orderRequest.put("receipt", "order_receipt_123");

            // Create the order
            Order order = client.orders.create(orderRequest);
            return order.toString();  // Return the created order information
        } catch (RazorpayException e) {
            e.printStackTrace();
            return "Error creating order: " + e.getMessage();
        }
    }
}