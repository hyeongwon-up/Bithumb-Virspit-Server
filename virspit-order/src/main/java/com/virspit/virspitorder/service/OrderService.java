package com.virspit.virspitorder.service;

import com.virspit.virspitorder.dto.response.OrdersResponseDto;
import com.virspit.virspitorder.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public List<OrdersResponseDto> getAll(String startDate, String endDate) {
        return null;
    }
}
