package com.example.vnpay.controller;

import com.example.vnpay.config.VnpayConfig;
import com.example.vnpay.model.CreatePaymentRequest;
import com.example.vnpay.model.CreatePaymentResponse;
import com.example.vnpay.model.QueryRequest;
import com.example.vnpay.model.RefundRequest;
import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import jakarta.servlet.http.HttpServletRequest;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/payment")
public class ApiPaymentController {
    @Autowired
    private VnpayConfig vnpayConfig;

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public CreatePaymentResponse createPayment(@Valid @RequestBody CreatePaymentRequest req, HttpServletRequest servletRequest) throws Exception {
        Map<String, String> inputData = new HashMap<>();
        inputData.put("vnp_Version", "2.1.0");
        inputData.put("vnp_TmnCode", vnpayConfig.getTmnCode());
        inputData.put("vnp_Amount", String.valueOf(req.getAmount() * 100));
        inputData.put("vnp_Command", "pay");
        inputData.put("vnp_CreateDate", vnpayConfig.getStartTime());
        inputData.put("vnp_CurrCode", "VND");
        inputData.put("vnp_IpAddr", servletRequest.getRemoteAddr());
        inputData.put("vnp_Locale", req.getLanguage());
        inputData.put("vnp_OrderInfo", req.getOrder_desc());
        inputData.put("vnp_OrderType", req.getOrder_type());
        inputData.put("vnp_ReturnUrl", vnpayConfig.getReturnUrl());
        inputData.put("vnp_TxnRef", req.getOrder_id());
        inputData.put("vnp_ExpireDate", req.getTxtexpire());
        if (req.getBank_code() != null && !req.getBank_code().isEmpty()) {
            inputData.put("vnp_BankCode", req.getBank_code());
        }
        List<String> fieldNames = new ArrayList<>(inputData.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        for (int i = 0; i < fieldNames.size(); i++) {
            String key = fieldNames.get(i);
            String value = inputData.get(key);
            if (i > 0) {
                hashData.append('&');
                query.append('&');
            }
            hashData.append(URLEncoder.encode(key, StandardCharsets.US_ASCII)).append('=')
                    .append(URLEncoder.encode(value, StandardCharsets.US_ASCII));
            query.append(URLEncoder.encode(key, StandardCharsets.US_ASCII)).append('=')
                    .append(URLEncoder.encode(value, StandardCharsets.US_ASCII));
        }
        String vnp_Url = vnpayConfig.getUrl() + "?" + query;
        String vnpSecureHash = HmacUtils.hmacSha512Hex(
                vnpayConfig.getHashSecret(), hashData.toString()
        );
        vnp_Url += "&vnp_SecureHash=" + vnpSecureHash;
        return new CreatePaymentResponse("00", "success", vnp_Url);
    }

    @PostMapping("/ipn")
    public Map<String, String> handleIpn(@RequestBody Map<String, String> allParams) {
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

    @PostMapping("/query")
    public String doQuery(@Valid @RequestBody QueryRequest req, HttpServletRequest servletRequest) throws Exception {
        String hashSecret = vnpayConfig.getHashSecret();
        String ipaddr = servletRequest.getRemoteAddr();
        Map<String, String> inputData = new LinkedHashMap<>();
        inputData.put("vnp_Version", "2.1.0");
        inputData.put("vnp_Command", "querydr");
        inputData.put("vnp_TmnCode", vnpayConfig.getTmnCode());
        inputData.put("vnp_TxnRef", req.getOrderid());
        inputData.put("vnp_OrderInfo", "Noi dung thanh toan");
        inputData.put("vnp_TransDate", req.getPaymentdate());
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
        return response.toString();
    }

    @PostMapping("/refund")
    public String doRefund(@Valid @RequestBody RefundRequest req, HttpServletRequest servletRequest) throws Exception {
        String hashSecret = vnpayConfig.getHashSecret();
        String ipaddr = servletRequest.getRemoteAddr();
        Map<String, String> inputData = new LinkedHashMap<>();
        inputData.put("vnp_Version", "2.1.0");
        inputData.put("vnp_TransactionType", req.getTrantype());
        inputData.put("vnp_Command", "refund");
        inputData.put("vnp_CreateBy", req.getMail());
        inputData.put("vnp_TmnCode", vnpayConfig.getTmnCode());
        inputData.put("vnp_TxnRef", req.getOrderid());
        inputData.put("vnp_Amount", String.valueOf(req.getAmount() * 100));
        inputData.put("vnp_OrderInfo", "Noi dung thanh toan");
        inputData.put("vnp_TransDate", req.getPaymentdate());
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
        return response.toString();
    }
} 