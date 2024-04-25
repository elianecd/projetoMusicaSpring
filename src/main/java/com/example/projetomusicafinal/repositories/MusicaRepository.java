package com.example.projetomusicafinal.repositories;

import com.example.projetomusicafinal.models.Album;
import com.example.projetomusicafinal.models.Banda;
import com.example.projetomusicafinal.models.Musica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicaRepository extends JpaRepository<Musica, Long> {
    Musica findByNomeAndAlbumAndBanda(String nome, Album album, Banda banda);
}