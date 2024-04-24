package com.example.projetomusicafinal.services;

import com.example.projetomusicafinal.models.Album;
import com.example.projetomusicafinal.models.AvaliacaoAlbum;
import com.example.projetomusicafinal.models.Banda;
import com.example.projetomusicafinal.repositories.AlbumRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter
@Service
public class AlbumService {

    private double media;

    @Autowired
    private AlbumRepository albumRepository;

    public Album createAlbum(Album album) {
        return albumRepository.save(album);
    }

    public Album findById(Long albumId) {
        return albumRepository.findById(albumId).orElse(null);
    }

    public Album save(Album album) {
        return albumRepository.save(album);
    } //é responsável por salvar ou atualizar um objeto Album no banco de dados. Ele recebe um objeto Album como parâmetro
    // e passa esse objeto para o método save do AlbumRepository.

    public Page<Album> findAllByBanda(Banda banda, Pageable pageable) {
        return albumRepository.findAllByBanda(banda, pageable);
    }

    public void updateMedia(List<AvaliacaoAlbum> avaliacoes) {
        double soma = 0.0;
        for (AvaliacaoAlbum avaliacao : avaliacoes) {
            soma += avaliacao.getNota();
        }
        this.media = soma / avaliacoes.size();
    }

    public Album updateDuracaoTotal(Album album, int duracaoMusica) {
        int duracaoTotal = album.getDuracaoTotal();
        duracaoTotal += duracaoMusica;
        album.setDuracaoTotal(duracaoTotal);
        return albumRepository.save(album);
    }

}
