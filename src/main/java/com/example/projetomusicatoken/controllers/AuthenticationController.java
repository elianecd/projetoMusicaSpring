package com.example.projetomusicatoken.controllers;

import com.example.projetomusicatoken.repositories.UserRepository;
import com.example.projetomusicatoken.security.TokenService;
import com.example.projetomusicatoken.user.AuthenticationDTO;
import com.example.projetomusicatoken.user.LoginResponseDTO;
import com.example.projetomusicatoken.user.RegisterDTO;
import com.example.projetomusicatoken.user.Usuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("usuarios")

public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((Usuario) auth.getPrincipal());

//        HttpHeaders headers = new HttpHeaders();
//        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
//
//        return ResponseEntity.ok().headers(headers).build();

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
   @PostMapping("/novo-registro")
   public ResponseEntity register(@RequestBody @Valid RegisterDTO data) {
        if (this.userRepository.findByUsername(data.username()) != null) return ResponseEntity.badRequest().body("Usuário já existe");

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        Usuario newUser = new Usuario(data.username(), encryptedPassword);

        this.userRepository.save(newUser);

        return ResponseEntity.ok().build();
   }
}
