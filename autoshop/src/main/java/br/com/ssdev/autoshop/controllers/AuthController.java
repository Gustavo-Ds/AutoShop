package br.com.ssdev.autoshop.controllers;

import br.com.ssdev.autoshop.config.security.TokenService;
import br.com.ssdev.autoshop.dto.LoginDTO;
import br.com.ssdev.autoshop.dto.TokenDTO;
import br.com.ssdev.autoshop.dto.UserRequestDTO;
import br.com.ssdev.autoshop.dto.UserResponseDTO;
import br.com.ssdev.autoshop.models.User;
import br.com.ssdev.autoshop.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody @Valid LoginDTO loginDTO) {
        UsernamePasswordAuthenticationToken userNamePwdToken =
                new UsernamePasswordAuthenticationToken(
                        loginDTO.email(),
                        loginDTO.password()
                );
        Authentication auth = authenticationManager.authenticate(userNamePwdToken);
        String token = tokenService.generateToken((User) auth.getPrincipal());
        return ResponseEntity.ok(new TokenDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody @Valid UserRequestDTO userRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(userRequestDTO));
    }
}
