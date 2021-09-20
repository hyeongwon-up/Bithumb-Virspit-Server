package com.virspit.virspitorder.repository;

import com.virspit.virspitorder.entity.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
    Page<Orders> findByOrderDateBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
}
