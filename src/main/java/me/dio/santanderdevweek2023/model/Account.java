package me.dio.santanderdevweek2023.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "tb_account")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "acc_cd_id")
    private UUID id;

    @Column(name = "acc_num_acc-number")
    private Long number;

    @Column(name = "acc_tx_agency")
    private String agency;

    @Column(name = "acc_num_balance")
    private double balance;

    @Column(name = "acc_num_limit")
    private double limit;


    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Set<Card> cards;

    @OneToOne
    private Client client;

}
