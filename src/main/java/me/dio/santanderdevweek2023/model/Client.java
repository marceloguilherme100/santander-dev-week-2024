package me.dio.santanderdevweek2023.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import me.dio.santanderdevweek2023.model.security.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tb_client")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Client implements UserDetails {
    @Id
    @Column(name = "cli_tx_cpf")
    private String cpf;
    @Column(name = "cli_tx_name")
    private String name;
    @Column(name = "cli_tx_password")
    private String password;

    private UserRole role;

    @Column(name = "cli_dt_birthdate")
    @JsonFormat(pattern="dd/MM/yyyy")
    private Date birthdate;

    @Column(name = "cli_tx_complement")
    private String complement;

    @Column(name = "cli_tx_number")
    private String number;

    @ManyToOne(cascade=CascadeType.ALL)
    private Address address;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == UserRole.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return cpf;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}