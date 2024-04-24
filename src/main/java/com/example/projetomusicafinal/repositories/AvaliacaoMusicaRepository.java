package com.example.projetomusicafinal.repositories;

import com.example.projetomusicafinal.models.AvaliacaoMusica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvaliacaoMusicaRepository extends JpaRepository<AvaliacaoMusica, Long> {
    List<AvaliacaoMusica> findByMusicaId(Long id);

}
