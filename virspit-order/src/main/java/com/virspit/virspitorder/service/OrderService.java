package com.virspit.virspitorder.service;

import com.virspit.virspitorder.dto.request.OrdersRequestDto;
import com.virspit.virspitorder.dto.response.OrdersResponseDto;
import com.virspit.virspitorder.entity.Orders;
import com.virspit.virspitorder.feign.MemberServiceFeignClient;
import com.virspit.virspitorder.response.error.ErrorCode;
import com.virspit.virspitorder.response.error.exception.BusinessException;
import com.virspit.virspitorder.repository.OrderRepository;
import com.virspit.virspitorder.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.protocol.types.Field;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberServiceFeignClient memberServiceFeignClient;
    private final KafkaTemplate<String, Long> kafkaTemplate;

    private static String TOPIC_NAME = "order";

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

    public List<OrdersResponseDto> getAllByMember(Long memberId, String startDate, String endDate, Pageable pageable) {
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

    public Object getOrder(Long memberId, Long productId) {
        String memberWalletAddress = memberServiceFeignClient.findByMemberId(memberId);
        kafkaTemplate.send(TOPIC_NAME, productId);
        Orders orders = new Orders(memberId, productId, memberWalletAddress);

        return orderRepository.save(orders);
    }
}
