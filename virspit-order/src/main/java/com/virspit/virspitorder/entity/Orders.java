package com.virspit.virspitorder.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@EqualsAndHashCode
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Orders { // TODO order 불가; orders or purchase
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long memberId;

    @NotNull
    private Long productId;

    @NotNull
    private LocalDateTime orderDate;
}
