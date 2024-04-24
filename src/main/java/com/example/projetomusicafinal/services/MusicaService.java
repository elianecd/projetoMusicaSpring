package com.example.projetomusicafinal.services;

import com.example.projetomusicafinal.models.AvaliacaoMusica;
import com.example.projetomusicafinal.models.Musica;
import com.example.projetomusicafinal.repositories.MusicaRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter
@Service
public class MusicaService {
    private double media;

    @Autowired
    private MusicaRepository musicaRepository;

    public Musica createMusica(Musica musica){
        return musicaRepository.save(musica);
    }//aqui estou criando uma variavel do tipo Musica e salvando ela no banco de dados, então eu retorno para o
    //musicaRepository que é o repositorio da musica, pois o repositorio é responsavel por salvar ou atualizar um objeto
    //Musica no banco de dados. Ele recebe um objeto Musica como parâmetro e passa esse objeto para o método save do MusicaRepository.

    public Musica findById(Long musicaId){
        return musicaRepository.findById(musicaId).orElse(null);
    }//aqui estou criando uma variavel do tipo Musica e passando o idMusica como parametro, então eu retorno para o
    //musicaRepository que é o repositorio da musica, pois o repositorio é responsavel por buscar um objeto Musica no banco de dados

    public void updateMedia(List<AvaliacaoMusica> avaliacoes) {
        double soma = 0.0;
        for (AvaliacaoMusica avaliacao : avaliacoes) {
            soma += avaliacao.getNota();
        }
        this.media = soma / avaliacoes.size();
    }

}
