package com.virspit.virspitorder.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.virspit.virspitorder.dto.request.OrderMemoRequestDto;
import com.virspit.virspitorder.dto.response.MemberResponseDto;
import com.virspit.virspitorder.dto.response.OrdersResponseDto;
import com.virspit.virspitorder.dto.response.ProductResponseDto;
import com.virspit.virspitorder.entity.Orders;
import com.virspit.virspitorder.response.result.SuccessResponse;
import com.virspit.virspitorder.service.OrderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    protected OrderService ordersService;

    private ProductResponseDto productDto = ProductResponseDto.builder()
            .id(1l)
            .build();
    private MemberResponseDto memberDto = MemberResponseDto.builder()
            .memberName("1")
            .build();

    @DisplayName("전체 주문 목록 테스트")
    @Test
    void allListByDate() throws Exception {
        Orders order1 = Orders.builder()
                .id(1l)
                .memberId(1l)
                .productId(1l)
                .orderDate(LocalDateTime.now())
                .build();
        List<Orders> orders = List.of(order1);

        String startDate = "2020-10-12 12:00:00";
        String endDate = "2021-10-12 12:00:00";

        Pageable page = PageRequest.of(0, 2, Sort.by("orderDate").descending());
        given(ordersService.getAll(startDate, endDate, page))
                .willReturn(orders.stream()
                        .map(doc -> OrdersResponseDto.entityToDto(doc, productDto, memberDto))
                        .collect(Collectors.toList()));

        mvc.perform(get("/orders")
                .param("startDate", startDate)
                .param("endDate", endDate)
                .param("page", String.valueOf(1))
                .param("size", String.valueOf(2))
        ).andExpect(status().isOk());

        verify(ordersService).getAll(startDate, endDate, page);
    }

    @DisplayName("유저 전체 주문 목록 테스트")
    @Test
    void memberOrderList() throws Exception {
        Orders order1 = Orders.builder()
                .id(1l)
                .memberId(1l)
                .productId(1l)
                .orderDate(LocalDateTime.now())
                .build();
        List<Orders> orders = List.of(order1);

        String startDate = "2020-10-12 12:00:00";
        String endDate = "2021-10-12 12:00:00";

        Pageable page = PageRequest.of(0, 2, Sort.by("orderDate").descending());
        given(ordersService.getAllByMember(1l, startDate, endDate, page))
                .willReturn(orders.stream()
                        .map(doc -> OrdersResponseDto.entityToDto(doc, productDto, memberDto))
                        .collect(Collectors.toList()));

        mvc.perform(get("/orders/members/{memberId}", 1)
                .param("startDate", startDate)
                .param("endDate", endDate)
                .param("page", String.valueOf(1))
                .param("size", String.valueOf(2))
        ).andExpect(status().isOk());

        verify(ordersService).getAllByMember(1l,startDate, endDate, page);
    }

    @DisplayName("메모 업데이트")
    @Test
    void updateMemo() throws Exception {
        Orders order1 = Orders.builder()
                .id(1l)
                .memberId(1l)
                .productId(1l)
                .memo("memo update")
                .orderDate(LocalDateTime.now())
                .build();
        OrderMemoRequestDto requestDto = OrderMemoRequestDto.builder()
                .memo("memo update")
                .orderId(1l)
                .build();
        given(ordersService.updateMemo(requestDto))
                .willReturn(OrdersResponseDto.entityToDto(order1, productDto, memberDto));
        ObjectMapper objectMapper = new ObjectMapper();
        mvc.perform(put("/orders/memo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto))
        ).andExpect(status().isOk());

        verify(ordersService).updateMemo(requestDto);
    }
}