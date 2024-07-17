package me.dio.santanderdevweek2023.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AccountDTO {
    private String cpf;
    private String password;
}
