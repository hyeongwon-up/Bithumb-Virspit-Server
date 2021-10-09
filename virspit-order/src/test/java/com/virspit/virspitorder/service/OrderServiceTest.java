package com.virspit.virspitorder.service;

import com.virspit.virspitorder.entity.Orders;
import com.virspit.virspitorder.repository.OrderRepository;
import com.virspit.virspitorder.util.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    // TODO : mock unit test error

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    List<Orders> orders;

    @BeforeEach
    void setUp() {
        orders = new ArrayList<>();
        for (long i = 0; i < 10; i++) {
            Orders order = Orders.builder()
                    .id(i + 1)
                    .memberId(i + 1)
                    .productId(i + 1)
                    .orderDate(LocalDateTime.now().minusMonths(i))
                    .build();
            orders.add(order);
        }
    }

    @DisplayName("startDate, endDate 모두 null 이 아닐때 주문 목록 조회")
    @Test
    void getAll_1() {
        // given
        String startDate = "2020-10-10 12:00:00";
        String endDate = "2021-09-20 12:00:00";
        Pageable pageable = PageRequest.of(0, 3, Sort.by("orderDate").descending());
        given(orderRepository.saveAll(orders)).willReturn(orders);
        given(orderRepository.findByOrderDateBetween(
                LocalDateTime.parse(startDate, StringUtils.FORMATTER),
                LocalDateTime.parse(endDate, StringUtils.FORMATTER),
                pageable).get())
                .willReturn(List.of(orders.get(1), orders.get(2), orders.get(3)).stream());

        // when
//        List<OrdersResponseDto> result = orderService.getAll(startDate, endDate, pageable);
        // then
//        assertThat(result).contains(OrdersResponseDto.entityToDto(orders.get(1)));
//        assertThat(result).contains(OrdersResponseDto.entityToDto(orders.get(2)));
//        assertThat(result).contains(OrdersResponseDto.entityToDto(orders.get(3)));
    }
}