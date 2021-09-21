package com.virspit.virspitorder.repository;

import com.virspit.virspitorder.entity.Orders;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@AutoConfigureTestDatabase(replace = NONE)
@DataJpaTest
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    List<Orders> generate(){
        List<Orders> orders = new ArrayList<>();
        for (long i = 0; i < 10; i++) {
            Orders order = Orders.builder()
                    .id(i + 1)
                    .memberId(i + 1)
                    .productId(i + 1)
                    .orderDate(LocalDateTime.now().minusMonths(i))
                    .build();
            orders.add(order);
            orderRepository.save(order);
        }
        return orders;
    }

    @DisplayName("주문 정보 저장")
    @Test
    void save() {
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

    @DisplayName("전체 조회 + 페이징(최근 주문순) : 0page 1size 의 첫번째 Order 와 최신순으로 저장한 orders 의 첫번째 id 가 같다.")
    @Test
    void findAll() {
        // given
        List<Orders> orders = generate();

        //when
        final Page<Orders> result = orderRepository.findAll(PageRequest.of(0, 1, Sort.by("orderDate").descending()));

        // then
        assertEquals(orders.get(0).getId(), result.getContent().get(0).getId());
    }

    @DisplayName("날짜 조회(최근 주문순): 현재부터 2달전까지 기간의 order 항목만 가져온다.")
    @Test
    void findByOrderDateBetween() {
        // given
        List<Orders> orders = generate();

        //when
        final Page<Orders> result = orderRepository.findByOrderDateBetween(
                LocalDateTime.now().minusMonths(2),
                LocalDateTime.now(),
                PageRequest.of(0, 3, Sort.by("orderDate").descending()));

        // then
        assertThat(result).contains(orders.get(0), orders.get(1));
    }

    @DisplayName("유저 아이디별로 전체기간의 구매목록을 가져온다.")
    @Test
    void findByMemberId(){
        // given
        List<Orders> orders = generate();

        //when
        final Page<Orders> result = orderRepository.findByMemberId(
                1L,
                PageRequest.of(0, 3, Sort.by("orderDate").descending()));

        // then
        assertThat(result).contains(orders.get(0));
        assertThat(result.getContent().size()).isEqualTo(1);
    }

    @DisplayName("유저 아이디별로 구매목록을 가져온다.(날짜지정)")
    @Test
    void findByMemberIdAndOrderDateBetween(){
        // given
        List<Orders> orders = generate();

        //when
        final Page<Orders> result = orderRepository.findByMemberIdAndOrderDateBetween(
                1L,
                LocalDateTime.now().minusMonths(2),
                LocalDateTime.now(),
                PageRequest.of(0, 3, Sort.by("orderDate").descending()));

        // then
        assertThat(result).contains(orders.get(0));
        assertThat(result.getContent().get(0).getId()).isEqualTo(1L);
    }
}