package com.virspit.virspitorder.controller;

import com.virspit.virspitorder.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api("주문 관련 API")
@RequestMapping("/orders")
@RequiredArgsConstructor
@RestController
public class OrderController {

    private final OrderService orderService;

    @ApiOperation(value = "전체 주문 목록")
    @GetMapping
    public ResponseEntity<?> allList(@PageableDefault(sort = "orderDate", direction = Sort.Direction.DESC) Pageable pageable,
                                     @RequestParam(value = "startDate", required = false) String startDate,
                                     @RequestParam(value = "endDate", required = false) String endDate) {

        return ResponseEntity.ok(orderService.getAll(startDate, endDate, pageable));
    }

    @ApiOperation(value = "유저의 주문 목록")
    @GetMapping("/members/{memberId}")
    public ResponseEntity<?> memberOrderList(@PageableDefault(sort = "orderDate", direction = Sort.Direction.DESC) Pageable pageable,
                                           @RequestParam(value = "startDate", required = false) String startDate,
                                           @RequestParam(value = "endDate", required = false) String endDate,
                                           @PathVariable("memberId") Long memberId) {

        return ResponseEntity.ok(orderService.getAllByMember(memberId, startDate, endDate, pageable));
    }
}
