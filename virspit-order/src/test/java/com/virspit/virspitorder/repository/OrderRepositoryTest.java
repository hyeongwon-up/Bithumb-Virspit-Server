package com.virspit.virspitorder.repository;

import com.virspit.virspitorder.entity.Orders;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@AutoConfigureTestDatabase(replace = NONE)
@DataJpaTest
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @DisplayName("주문 정보 저장")
    @Test
    void save(){
        // given
        final Orders order = Orders.builder()
                .id(1l)
                .memberId(1l)
                .productId(1l)
                .orderDate(LocalDateTime.now())
                .build();

        // when
        final Orders saved = orderRepository.save(order);

        // then
        assertEquals(order, saved);

    }
}