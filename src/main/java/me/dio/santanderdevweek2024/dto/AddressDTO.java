package me.dio.santanderdevweek2024.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AddressDTO {
    private String cep;
    private String complement;
    private String number;
}
