package com.virspit.virspitorder.controller;

import com.virspit.virspitorder.dto.request.OrderMemoRequestDto;
import com.virspit.virspitorder.response.result.SuccessResponse;
import com.virspit.virspitorder.dto.response.OrdersResponseDto;
import com.virspit.virspitorder.service.OrderService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.groundx.caver_ext_kas.rest_client.io.swagger.client.ApiException;

@Api("주문 관련 API")
@RequestMapping("/orders")
@RequiredArgsConstructor
@RestController
public class OrderController {

    private final OrderService orderService;

    @ApiOperation(value = "전체 주문 목록")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startDate", value = "시작 날짜 (yyyy-MM-dd HH:mm:ss) ", paramType = "query"),
            @ApiImplicitParam(name = "endDate", value = "마지막 날짜 (yyyy-MM-dd HH:mm:ss)", paramType = "query")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "success", response = OrdersResponseDto.class, responseContainer = "List")
    })
    @GetMapping
    public ResponseEntity allList(@PageableDefault(sort = "orderDate", direction = Sort.Direction.DESC) Pageable pageable,
                                  @RequestParam(value = "startDate", required = false) String startDate,
                                  @RequestParam(value = "endDate", required = false) String endDate) {
        return new ResponseEntity<>(SuccessResponse.of(orderService.getAll(startDate, endDate, pageable)), HttpStatus.OK);
    }

    @ApiOperation(value = "유저의 주문 목록")
    @ApiResponses({
            @ApiResponse(code = 200, message = "success", response = OrdersResponseDto.class, responseContainer = "List")
    })
    @GetMapping("/members/{memberId}")
    public ResponseEntity memberOrderList(@PageableDefault(sort = "orderDate", direction = Sort.Direction.DESC) Pageable pageable,
                                             @RequestParam(value = "startDate", required = false) String startDate,
                                             @RequestParam(value = "endDate", required = false) String endDate,
                                             @PathVariable("memberId") Long memberId) {

        return ResponseEntity.ok(SuccessResponse.of(orderService.getAllByMember(memberId, startDate, endDate, pageable)));
    }

    @ApiOperation("유저 상품 주문")
    @ApiResponses({
            @ApiResponse(code = 200, message = "success", response = OrdersResponseDto.class, responseContainer = "List")
    })
    @PostMapping
    public ResponseEntity order(@RequestParam Long memberId, @RequestParam Long productId) throws ApiException {
        return ResponseEntity.ok(SuccessResponse.of(orderService.order(memberId, productId)));
    }

    @ApiOperation("결제관리 - 메모 수정")
    @ApiResponses({
            @ApiResponse(code = 200, message = "success", response = OrdersResponseDto.class)
    })
    @PutMapping("/memo")
    public ResponseEntity updateMemo(@RequestBody OrderMemoRequestDto requestDto) {
        return ResponseEntity.ok(SuccessResponse.of(orderService.updateMemo(requestDto)));
    }
}
