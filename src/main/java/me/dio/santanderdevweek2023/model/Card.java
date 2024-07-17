package me.dio.santanderdevweek2023.model;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "tb_card")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name="cd_id_number", unique = true)
    private Long number;
    @Column(name = "cd_enum_type")
    private CardType type;
    @Column(name = "cd_num_limit")
    private double limit;

    @ManyToOne
    @JoinColumn(name = "fk_account_id", referencedColumnName = "acc_cd_id")
    private Account account;

    public Card(Long number, CardType type, double limit, Account account) {
        this.number = number;
        this.type = type;
        this.limit = limit;
        this.account = account;
    }
}
