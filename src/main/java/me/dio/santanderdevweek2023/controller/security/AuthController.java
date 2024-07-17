package me.dio.santanderdevweek2023.controller.security;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import me.dio.santanderdevweek2023.dto.security.AuthenticationDTO;
import me.dio.santanderdevweek2023.dto.security.LoginResponseDTO;
import me.dio.santanderdevweek2023.model.Client;
import me.dio.santanderdevweek2023.repository.ClientRepository;
import me.dio.santanderdevweek2023.service.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    @SecurityRequirement(name = "")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.getLogin(), data.getPassword());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((Client) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

}
