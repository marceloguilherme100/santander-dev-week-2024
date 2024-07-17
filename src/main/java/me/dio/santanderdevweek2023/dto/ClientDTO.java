package me.dio.santanderdevweek2023.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.*;
import me.dio.santanderdevweek2023.model.security.UserRole;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ClientDTO {
    private String cpf;
    private String name;
    @Column(name = "cli_tx_password")
    private String password;
    @JsonFormat(pattern="dd/MM/yyyy")
    private Date birthdate;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private AddressDTO addressDTO;
    private UserRole role;
}
