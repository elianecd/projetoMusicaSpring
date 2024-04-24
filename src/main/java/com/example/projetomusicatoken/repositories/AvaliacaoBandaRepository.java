package com.example.projetomusicatoken.repositories;

import com.example.projetomusicatoken.models.AvaliacaoBanda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvaliacaoBandaRepository extends JpaRepository<AvaliacaoBanda, Long> {
    //Este é o repositório responsável por interagir com a tabela AvaliacaoBanda no banco de dados. Ele fornece métodos para realizar operações CRUD (Create, Read, Update, Delete) nesta tabela.
    List<AvaliacaoBanda> findAllByIdBanda(Long idBanda);
}
