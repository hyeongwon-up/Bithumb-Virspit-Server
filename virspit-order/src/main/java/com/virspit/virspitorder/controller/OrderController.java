package com.virspit.virspitorder.controller;

import com.virspit.virspitorder.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api("주문 관련 API")
@RequestMapping("/orders")
@RequiredArgsConstructor
@RestController
public class OrderController {

    private final OrderService orderService;

    @ApiOperation(value = "전체 주문 목록")
    @GetMapping
    public ResponseEntity<?> allList(@RequestParam(value = "startDate", required = false) String startDate,
                                     @RequestParam(value = "endDate", required = false) String endDate) {

        return ResponseEntity.ok(orderService.getAll(startDate, endDate));
    }

    @ApiOperation(value = "유저의 주문 목록")
    @GetMapping("/users")
    public ResponseEntity<?> userOrderList(@RequestParam(value = "startDate", required = false) String startDate,
                                           @RequestParam(value = "endDate", required = false) String endDate) {

        return ResponseEntity.ok(orderService.getAll(startDate, endDate));
    }
}
