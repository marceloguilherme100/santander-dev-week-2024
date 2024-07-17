package me.dio.santanderdevweek2024.dto.security;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AuthenticationDTO {
    private String login;
    private String password;
}
