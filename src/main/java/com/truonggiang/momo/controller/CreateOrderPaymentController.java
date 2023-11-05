package com.truonggiang.momo.controller;

import com.truonggiang.momo.model.OrderRequestDTO;
import com.truonggiang.momo.service.CreateOrderPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class CreateOrderPaymentController {

    @Autowired
    private CreateOrderPaymentService paymentService;

    @PostMapping("/momo-payment")
    public ResponseEntity<Map<String, Object>> momoPayment(@RequestBody OrderRequestDTO orderRequest) throws NoSuchAlgorithmException, InvalidKeyException, IOException {

        Map<String, Object> result = this.paymentService.createOrder(orderRequest);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }

}
