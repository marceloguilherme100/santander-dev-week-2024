package me.dio.santanderdevweek2023.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "tb_address")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "addr_cd_id")
    private UUID id;

    @Column(name = "addr_tx_cep", unique = true, nullable = false)
    private String cep;
    @Column(name = "addr_tx_street")
    private String street;
    @Column(name = "addr_tx_district")
    private String district;
    @Column(name = "addr_tx_city")
    private String city;
    @Column(name = "addr_tx_uf")
    private String uf;
}