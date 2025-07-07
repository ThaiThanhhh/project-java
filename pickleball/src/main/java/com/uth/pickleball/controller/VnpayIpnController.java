package com.example.vnpay.controller;

import com.example.vnpay.config.VnpayConfig;
import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class VnpayIpnController {
    @Autowired
    private VnpayConfig vnpayConfig;

    @GetMapping("/vnpay/ipn")
    public Map<String, String> handleIpn(@RequestParam Map<String, String> allParams) {
        Map<String, String> inputData = new HashMap<>();
        Map<String, String> returnData = new HashMap<>();
        for (Map.Entry<String, String> entry : allParams.entrySet()) {
            if (entry.getKey().startsWith("vnp_")) {
                inputData.put(entry.getKey(), entry.getValue());
            }
        }
        String vnp_SecureHash = inputData.get("vnp_SecureHash");
        inputData.remove("vnp_SecureHash");
        List<String> fieldNames = new ArrayList<>(inputData.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        for (int i = 0; i < fieldNames.size(); i++) {
            String key = fieldNames.get(i);
            String value = inputData.get(key);
            if (i > 0) hashData.append('&');
            hashData.append(URLEncoder.encode(key, StandardCharsets.US_ASCII)).append('=')
                    .append(URLEncoder.encode(value, StandardCharsets.US_ASCII));
        }
        String secureHash = HmacUtils.hmacSha512Hex(vnpayConfig.getHashSecret(), hashData.toString());
        String orderId = inputData.get("vnp_TxnRef");
        String vnp_Amount = inputData.get("vnp_Amount");
        // TODO: Lấy thông tin đơn hàng từ DB theo orderId, kiểm tra số tiền, trạng thái, ...
        // Ở đây sẽ mô phỏng như PHP: order = null
        Map<String, Object> order = null;
        try {
            if (secureHash.equals(vnp_SecureHash)) {
                if (order != null) {
                    // TODO: kiểm tra số tiền, trạng thái đơn hàng, cập nhật DB
                    returnData.put("RspCode", "00");
                    returnData.put("Message", "Confirm Success");
                } else {
                    returnData.put("RspCode", "01");
                    returnData.put("Message", "Order not found");
                }
            } else {
                returnData.put("RspCode", "97");
                returnData.put("Message", "Invalid signature");
            }
        } catch (Exception e) {
            returnData.put("RspCode", "99");
            returnData.put("Message", "Unknow error");
        }
        return returnData;
    }
} 