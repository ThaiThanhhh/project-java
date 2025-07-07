package com.example.vnpay.controller;

import com.example.vnpay.config.VnpayConfig;
import com.example.vnpay.model.CreatePaymentRequest;
import com.example.vnpay.model.CreatePaymentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;
import jakarta.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Controller
public class VnpayCreatePaymentController {
    @Autowired
    private VnpayConfig vnpayConfig;

    @PostMapping(value = "/vnpay/create-payment", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public Object createPayment(@ModelAttribute CreatePaymentRequest req,
                               @RequestParam(required = false) String redirect,
                               HttpServletRequest servletRequest) throws Exception {
        Map<String, String> inputData = new HashMap<>();
        inputData.put("vnp_Version", "2.1.0");
        inputData.put("vnp_TmnCode", vnpayConfig.getTmnCode());
        inputData.put("vnp_Amount", String.valueOf(req.amount * 100));
        inputData.put("vnp_Command", "pay");
        inputData.put("vnp_CreateDate", vnpayConfig.getStartTime());
        inputData.put("vnp_CurrCode", "VND");
        inputData.put("vnp_IpAddr", servletRequest.getRemoteAddr());
        inputData.put("vnp_Locale", req.language);
        inputData.put("vnp_OrderInfo", req.order_desc);
        inputData.put("vnp_OrderType", req.order_type);
        inputData.put("vnp_ReturnUrl", vnpayConfig.getReturnUrl());
        inputData.put("vnp_TxnRef", req.order_id);
        inputData.put("vnp_ExpireDate", req.txtexpire);
        // Billing
        inputData.put("vnp_Bill_Mobile", req.txt_billing_mobile);
        inputData.put("vnp_Bill_Email", req.txt_billing_email);
        String fullName = req.txt_billing_fullname != null ? req.txt_billing_fullname.trim() : "";
        String firstName = "";
        String lastName = "";
        if (!fullName.isEmpty()) {
            String[] nameParts = fullName.split(" ");
            firstName = nameParts[0];
            lastName = nameParts.length > 1 ? nameParts[nameParts.length - 1] : "";
        }
        inputData.put("vnp_Bill_FirstName", firstName);
        inputData.put("vnp_Bill_LastName", lastName);
        inputData.put("vnp_Bill_Address", req.txt_inv_addr1);
        inputData.put("vnp_Bill_City", req.txt_bill_city);
        inputData.put("vnp_Bill_Country", req.txt_bill_country);
        if (req.txt_bill_state != null && !req.txt_bill_state.isEmpty()) {
            inputData.put("vnp_Bill_State", req.txt_bill_state);
        }
        // Invoice
        inputData.put("vnp_Inv_Phone", req.txt_inv_mobile);
        inputData.put("vnp_Inv_Email", req.txt_inv_email);
        inputData.put("vnp_Inv_Customer", req.txt_inv_customer);
        inputData.put("vnp_Inv_Address", req.txt_inv_addr1);
        inputData.put("vnp_Inv_Company", req.txt_inv_company);
        inputData.put("vnp_Inv_Taxcode", req.txt_inv_taxcode);
        inputData.put("vnp_Inv_Type", req.cbo_inv_type);
        if (req.bank_code != null && !req.bank_code.isEmpty()) {
            inputData.put("vnp_BankCode", req.bank_code);
        }
        // Sắp xếp và build query/hash
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
        String vnpSecureHash = org.apache.commons.codec.digest.HmacUtils.hmacSha512Hex(
                vnpayConfig.getHashSecret(), hashData.toString()
        );
        vnp_Url += "&vnp_SecureHash=" + vnpSecureHash;
        CreatePaymentResponse returnData = new CreatePaymentResponse("00", "success", vnp_Url);
        if (redirect != null) {
            return new RedirectView(vnp_Url);
        } else {
            return returnData;
        }
    }
} 