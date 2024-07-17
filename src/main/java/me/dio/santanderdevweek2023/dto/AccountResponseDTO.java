package me.dio.santanderdevweek2023.dto;

import lombok.*;
import me.dio.santanderdevweek2023.model.Account;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AccountResponseDTO {
    private Account account;
    private CardDTO cardDTO;
}
