package me.dio.santanderdevweek2023.dto.security;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class LoginResponseDTO {
    String token;
}
