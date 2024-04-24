package com.example.projetomusicatoken.repositories;

import com.example.projetomusicatoken.models.AvaliacaoAlbum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvaliacaoAlbumRepository extends JpaRepository<AvaliacaoAlbum, Long> {
    List<AvaliacaoAlbum> findAllByAlbumId(Long albumId);
}
