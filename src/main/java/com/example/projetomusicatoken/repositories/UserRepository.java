package com.example.projetomusicatoken.repositories;

import com.example.projetomusicatoken.user.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<Usuario, Long> {
    UserDetails findByUsername(String username); //para consultar o usuario pelo username
}
