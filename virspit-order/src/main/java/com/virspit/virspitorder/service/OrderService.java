package com.virspit.virspitorder.service;

import com.virspit.virspitorder.dto.response.OrdersResponseDto;
import com.virspit.virspitorder.error.ErrorCode;
import com.virspit.virspitorder.error.exception.BusinessException;
import com.virspit.virspitorder.repository.OrderRepository;
import com.virspit.virspitorder.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Transactional(readOnly = true)
    public List<OrdersResponseDto> getAll(String startDate, String endDate, Pageable pageable) {
        StringUtils.validateInputDate(startDate, endDate);

        if (startDate != null) {
            return findAllByDate(startDate, endDate, pageable);
        }

        return findAll(pageable);
    }

    private List<OrdersResponseDto> findAllByDate(String startDate, String endDate, Pageable pageable) {
        LocalDateTime endDateTime = LocalDateTime.now();

        if (endDate != null) {
            endDateTime = LocalDateTime.parse(endDate, StringUtils.FORMATTER);
        }

        return orderRepository.findByOrderDateBetween(
                LocalDateTime.parse(startDate, StringUtils.FORMATTER),
                endDateTime,
                pageable)
                .stream()
                .map(OrdersResponseDto::entityToDto)
                .collect(Collectors.toList());
    }

    private List<OrdersResponseDto> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable)
                .stream()
                .map(OrdersResponseDto::entityToDto)
                .collect(Collectors.toList());
    }

    public List<OrdersResponseDto> getAllByUser(Long memberId, String startDate, String endDate, Pageable pageable) {
        StringUtils.validateInputDate(startDate, endDate);

        if(startDate == null && endDate == null) {
            return orderRepository.findByMemberId(memberId, pageable)
                    .stream()
                    .map(OrdersResponseDto::entityToDto)
                    .collect(Collectors.toList());
        }
        if(startDate == null || endDate == null) {
            throw new BusinessException("startDate, endDate 를 정확히 입력해주세요.", ErrorCode.INVALID_INPUT_VALUE);
        }
        return orderRepository.findByMemberIdAndOrderDateBetween(
                memberId,
                LocalDateTime.parse(startDate, StringUtils.FORMATTER),
                LocalDateTime.parse(endDate, StringUtils.FORMATTER),
                pageable)
                .stream()
                .map(OrdersResponseDto::entityToDto)
                .collect(Collectors.toList());
    }
}
