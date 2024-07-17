package me.dio.santanderdevweek2023.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.util.UUID;

@Entity
@Table(name = "tb_payment_method")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "pm_cd_id")
    private UUID id;

    @Column(name = "pm_tx_description", nullable = false)
    private String description;

    @Lob
    @JdbcTypeCode(Types.BINARY)
    @Column(name = "pm_tx_icon", nullable = false)
    private byte[] icon;



    public PaymentMethod(byte[] icon, String description) {
        this.icon = icon;
        this.description = description;
    }
}