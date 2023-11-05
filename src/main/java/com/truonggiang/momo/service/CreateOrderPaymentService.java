package com.truonggiang.momo.service;

import com.truonggiang.momo.common.constants.MoMoConstant;
import com.truonggiang.momo.common.utils.MoMoHelper;
import com.truonggiang.momo.model.OrderRequestDTO;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Service
public class CreateOrderPaymentService {

    public Map<String, Object> createOrder(OrderRequestDTO orderRequest) throws NoSuchAlgorithmException, InvalidKeyException, IOException {

        JSONObject json = new JSONObject();
        json.put("partnerCode", MoMoConstant.PARTNER_CODE);
        json.put("accessKey", MoMoConstant.ACCESS_KEY);
        json.put("requestId", String.valueOf(System.currentTimeMillis()));
        json.put("amount", orderRequest.getAmount().toString());
        json.put("orderId", orderRequest.getOrderId());
        json.put("orderInfo", "Thanh toan don hang " + orderRequest.getOrderId());
        json.put("returnUrl", MoMoConstant.REDIRECT_URL);
        json.put("notifyUrl", MoMoConstant.NOTIFY_URL);
        json.put("requestType", MoMoConstant.REQUEST_TYPE);

        String data = "partnerCode=" + MoMoConstant.PARTNER_CODE
                + "&accessKey=" + MoMoConstant.ACCESS_KEY
                + "&requestId=" + json.get("requestId")
                + "&amount=" + json.get("amount")
                + "&orderId=" + json.get("orderId")
                + "&orderInfo=" + json.get("orderInfo")
                + "&returnUrl=" + MoMoConstant.REDIRECT_URL
                + "&notifyUrl=" + MoMoConstant.NOTIFY_URL
                + "&extraData=";

        String signatureKey = MoMoHelper.computeHmacSha256(data, MoMoConstant.SECRET_KEY);
        json.put("signature", signatureKey);

        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(MoMoConstant.CREATE_ORDER_URL);

        StringEntity stringEntity = new StringEntity(json.toString());
        post.setHeader("content-type", "application/json");
        post.setEntity(stringEntity);

        CloseableHttpResponse res = client.execute(post);
        BufferedReader rd = new BufferedReader(new InputStreamReader(res.getEntity().getContent()));
        StringBuilder resultJsonStr = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {

            resultJsonStr.append(line);
        }

        JSONObject object = new JSONObject(resultJsonStr.toString());
        Map<String, Object> result = new HashMap<>();
        for (Iterator<String> it = object.keys(); it.hasNext(); ) {

            String key = it.next();
            result.put(key, object.get(key));
        }

        return result;

    }

}
