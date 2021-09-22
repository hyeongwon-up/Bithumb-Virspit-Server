package com.virspit.virspitproduct.domain.product.repository;

import com.virspit.virspitproduct.domain.product.entity.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByTeamPlayerIdAndNameContains(Long teamPlayerId, String name, Sort sort);

    List<Product> findAllByNameContains(String name, Sort sort);

    List<Product> findAllByStartDateTimeIsAfter(LocalDateTime localDateTime);
}
