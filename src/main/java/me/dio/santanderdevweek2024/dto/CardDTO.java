package me.dio.santanderdevweek2024.dto;

import lombok.*;
import me.dio.santanderdevweek2024.model.CardType;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CardDTO {
    private UUID id;
    private Long number;
    private CardType type;
    private double limit;
}
