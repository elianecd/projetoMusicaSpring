package com.example.projetomusicafinal.services;

import com.example.projetomusicafinal.models.AvaliacaoBanda;
import com.example.projetomusicafinal.models.Banda;
import com.example.projetomusicafinal.repositories.AvaliacaoBandaRepository;
import com.example.projetomusicafinal.repositories.BandaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BandaService { //é a classe de serviço que contém a lógica de negócios para criar uma banda.

    @Autowired //cria uma instância do BandaRepository e a fornece ao BandaService quando o BandaService é criado.
    private BandaRepository bandaRepository;

    @Autowired
    private AvaliacaoBandaRepository avaliacaoBandaRepository;

    public Banda createBanda(Banda banda) { //salva a banda no banco de dados.
        return bandaRepository.save(banda);
    } //Quando chamamos o método save do JpaRepository, ele insere a entidade no banco de dados se ela ainda não existir,
    // ou atualiza a entidade existente se ela já existir.

    public Banda findById(Long idBanda) {
        return bandaRepository.findById(idBanda).orElse(null);
    }

    public void updateMedia(Banda banda) {
        List<AvaliacaoBanda> avaliacoes = avaliacaoBandaRepository.findAllByIdBanda(banda.getId());
        double soma = 0.0;
        for (AvaliacaoBanda avaliacao : avaliacoes) {
            soma += avaliacao.getNota();
        }
        double media = soma / avaliacoes.size();
        banda.setMedia(media);
        bandaRepository.save(banda);
    }

    public Page<Banda> findAll(Pageable pageable) {
        return (Page<Banda>) bandaRepository.findAll(pageable);
    }

}
