package com.virspit.virspitorder.service;

import com.virspit.virspitorder.dto.response.OrdersResponseDto;
import com.virspit.virspitorder.repository.OrderRepository;
import com.virspit.virspitorder.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public List<OrdersResponseDto> getAll(String startDate, String endDate) {
        StringUtils.validateInputDate(startDate, endDate);

        if (startDate == null && endDate == null) {
            return orderRepository.findAll()
                    .stream()
                    .map(OrdersResponseDto::entityToDto)
                    .collect(Collectors.toList());
        }


        return null;
    }
}
