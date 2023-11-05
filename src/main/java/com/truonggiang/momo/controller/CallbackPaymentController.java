package com.truonggiang.momo.controller;


import com.truonggiang.momo.model.CallbackRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class CallbackPaymentController {

    @GetMapping("api/v1/callback")
    public ResponseEntity<Map<String, Object>> callBack(@RequestParam Map<String, Object> callbackRequestDTO) {

        Map<String, Object> result = new HashMap<>();
        if (callbackRequestDTO.containsKey("message")
                && callbackRequestDTO.get("message").equals("Success")) {

            result.put("orderId", callbackRequestDTO.get("orderId"));
            result.put("amount", callbackRequestDTO.get("amount"));
            result.put("orderInfo", callbackRequestDTO.get("orderInfo"));
            result.put("message", callbackRequestDTO.get("message"));
        } else {

            result.put("message", callbackRequestDTO.get("message"));
        }

        return new ResponseEntity<>(result, HttpStatus.OK);

    }

}
