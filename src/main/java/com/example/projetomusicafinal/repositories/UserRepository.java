package com.example.projetomusicafinal.repositories;

import com.example.projetomusicafinal.user.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<Usuario, Long> {
//recebe o tipo da entidade da tabela e o tipo da chave primaria
//Usuario é a entidade que está sendo gerenciada pelo repositório
    UserDetails findByUsername(String username); //para consultar o usuario pelo username
}
