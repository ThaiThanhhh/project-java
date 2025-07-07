package com.example.vnpay.controller;

import com.example.vnpay.config.VnpayConfig;
import com.example.vnpay.model.QueryRequest;
import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

@Controller
@RequestMapping("/vnpay/query")
public class VnpayQueryController {
    @Autowired
    private VnpayConfig vnpayConfig;

    @GetMapping
    public String showForm(Model model) {
        model.addAttribute("orderid", "");
        model.addAttribute("paymentdate", "");
        return "query";
    }

    @PostMapping
    public String doQuery(@ModelAttribute QueryRequest req, Model model, HttpServletRequest servletRequest) throws Exception {
        String hashSecret = vnpayConfig.getHashSecret();
        String ipaddr = servletRequest.getRemoteAddr();
        Map<String, String> inputData = new LinkedHashMap<>();
        inputData.put("vnp_Version", "2.1.0");
        inputData.put("vnp_Command", "querydr");
        inputData.put("vnp_TmnCode", vnpayConfig.getTmnCode());
        inputData.put("vnp_TxnRef", req.orderid);
        inputData.put("vnp_OrderInfo", "Noi dung thanh toan");
        inputData.put("vnp_TransDate", req.paymentdate);
        inputData.put("vnp_CreateDate", vnpayConfig.getStartTime());
        inputData.put("vnp_IpAddr", ipaddr);
        List<String> fieldNames = new ArrayList<>(inputData.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashdata = new StringBuilder();
        StringBuilder query = new StringBuilder();
        for (int i = 0; i < fieldNames.size(); i++) {
            String key = fieldNames.get(i);
            String value = inputData.get(key);
            if (i > 0) {
                hashdata.append('&');
                query.append('&');
            }
            hashdata.append(URLEncoder.encode(key, StandardCharsets.US_ASCII)).append('=')
                    .append(URLEncoder.encode(value, StandardCharsets.US_ASCII));
            query.append(URLEncoder.encode(key, StandardCharsets.US_ASCII)).append('=')
                    .append(URLEncoder.encode(value, StandardCharsets.US_ASCII));
        }
        String vnp_apiUrl = vnpayConfig.getApiUrl() + "?" + query;
        String vnpSecureHash = HmacUtils.hmacSha512Hex(hashSecret, hashdata.toString());
        vnp_apiUrl += "&vnp_SecureHash=" + vnpSecureHash;
        // Gọi API VNPAY
        URL url = new URL(vnp_apiUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setDoOutput(true);
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        model.addAttribute("orderid", req.orderid);
        model.addAttribute("paymentdate", req.paymentdate);
        model.addAttribute("result", response.toString());
        return "query";
    }
} 